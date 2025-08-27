package com.demo.controller;

import com.demo.dto.TextToSpeechRequest;
import org.springframework.ai.audio.tts.TextToSpeechPrompt;
import org.springframework.ai.audio.tts.TextToSpeechResponse;
import org.springframework.ai.elevenlabs.ElevenLabsTextToSpeechModel;
import org.springframework.ai.elevenlabs.ElevenLabsTextToSpeechOptions;
import org.springframework.ai.elevenlabs.api.ElevenLabsApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * Text-to-Speech流式响应控制器
 * 提供基于ElevenLabs API的实时语音合成服务
 * 
 * 核心功能：
 * - 接收文本转语音请求
 * - 配置语音参数（声音ID、稳定性、相似度增强等）
 * - 实时流式返回MP3音频数据
 * - 支持多语言语音合成
 */
@RestController
@RequestMapping("/api/tts")
public class TextToSpeechController {

    /**
     * ElevenLabs Text-to-Speech模型实例
     * 用于执行实际的语音合成操作
     */
    private final ElevenLabsTextToSpeechModel textToSpeechModel;

    /**
     * 构造函数，通过依赖注入获取Text-to-Speech模型
     * 
     * @param textToSpeechModel ElevenLabs Text-to-Speech模型实例
     */
    @Autowired
    public TextToSpeechController(ElevenLabsTextToSpeechModel textToSpeechModel) {
        this.textToSpeechModel = textToSpeechModel;
    }

    /**
     * 流式Text-to-Speech API端点
     * 接收文本和语音参数，返回实时音频流
     * 
     * 处理流程：
     * 1. 从请求中提取语音设置参数
     * 2. 构建ElevenLabs Text-to-Speech选项
     * 3. 创建语音合成提示（prompt）
     * 4. 调用模型进行流式语音生成
     * 5. 将音频数据流式返回给客户端
     * 
     * 音频格式：MP3, 44.1kHz, 128kbps
     * 模型：eleven_multilingual_v2 (支持多语言)
     * 
     * @param request Text-to-Speech请求对象，包含文本和语音参数
     * @return 包含音频流的ResponseEntity，Content-Type为audio/mpeg
     */
    @PostMapping(value = "/stream", produces = "audio/mpeg")
    public ResponseEntity<StreamingResponseBody> ttsStream(@RequestBody TextToSpeechRequest request) {

        // 构建语音设置对象，包含声音的各项参数
        // stability: 语音稳定性 (0.0-1.0)
        // similarityBoost: 相似度增强 (0.0-1.0) 
        // style: 语音风格强度 (0.0-1.0)
        // useSpeakerBoost: 是否启用说话者增强
        // speed: 语音速度 (0.5-2.0)
        var voiceSettings = new ElevenLabsApi.SpeechRequest.VoiceSettings(
                request.stability(), request.similarityBoost(), request.style(), request.useSpeakerBoost(), request.speed()
        );

        // 构建Text-to-Speech选项配置
        var textToSpeechOptions = ElevenLabsTextToSpeechOptions.builder()
                .model("eleven_multilingual_v2")  // 使用多语言模型v2
                .voiceSettings(voiceSettings)     // 应用语音设置
                .outputFormat(ElevenLabsApi.OutputFormat.MP3_44100_128.getValue())  // 输出MP3格式
                .build();

        // 启用日志记录，便于调试和监控API调用
        textToSpeechOptions.setEnableLogging(Boolean.TRUE);

        // 创建Text-to-Speech提示对象，包含要转换的文本和选项
        var textToSpeechPrompt = new TextToSpeechPrompt(request.text(), textToSpeechOptions);

        // 调用模型执行流式语音合成，返回Flux响应流
        Flux<TextToSpeechResponse> responseStream = textToSpeechModel.stream(textToSpeechPrompt);

        // 创建StreamingResponseBody，用于将音频数据流式写入HTTP响应
        StreamingResponseBody body = outputStream -> {
            // 遍历响应流中的每个语音响应
            responseStream.toStream().forEach(speechResponse -> {
                try {
                    // 将音频数据写入输出流
                    outputStream.write(speechResponse.getResult().getOutput());
                    // 立即刷新输出流，确保客户端能够实时接收音频数据
                    outputStream.flush();
                } catch (IOException e) {
                    // 将IOException包装为UncheckedIOException，简化异常处理
                    throw new UncheckedIOException(e);
                }
            });
        };

        // 返回包含音频流的HTTP响应，设置正确的Content-Type
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("audio/mpeg"))
                .body(body);
    }

}
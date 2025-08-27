package com.demo.dto;

/**
 * Text-to-Speech请求数据传输对象
 * 封装了语音合成所需的所有参数
 * 
 * 使用Java Record类型，提供不可变的数据结构和自动生成的方法：
 * - 构造函数
 * - getter方法（text(), voiceId()等）
 * - equals(), hashCode(), toString()
 */
public record TextToSpeechRequest(
        /**
         * 要转换为语音的文本内容
         * 支持多语言文本，包括中文、英文等
         */
        String text,
        
        /**
         * ElevenLabs语音ID
         * 用于指定使用哪个预训练的声音模型
         * 不同的voiceId对应不同的说话者特征
         */
        String voiceId,
        
        /**
         * 语音稳定性参数 (范围: 0.0 - 1.0)
         * 值越高，生成的语音越稳定一致
         * 值越低，语音变化越丰富但可能不够稳定
         * 推荐值：0.75
         */
        Double stability,
        
        /**
         * 相似度增强参数 (范围: 0.0 - 1.0)  
         * 控制生成语音与原始声音的相似度
         * 值越高，越接近原始训练声音
         * 值越低，声音变化越大
         * 推荐值：0.75
         */
        Double similarityBoost,
        
        /**
         * 语音风格强度参数 (范围: 0.0 - 1.0)
         * 控制语音的表达风格和情感强度
         * 值越高，语音表达越夸张
         * 值越低，语音越平淡
         * 推荐值：0.0-0.5
         */
        Double style,
        
        /**
         * 是否启用说话者增强功能
         * true: 启用增强，提高语音清晰度和自然度
         * false: 不启用增强，使用标准处理
         * 推荐值：true
         */
        Boolean useSpeakerBoost,
        
        /**
         * 语音播放速度 (范围: 0.5 - 2.0)
         * 1.0为正常速度
         * 小于1.0为慢速播放
         * 大于1.0为快速播放
         * 推荐值：1.0
         */
        Double speed
) {
}

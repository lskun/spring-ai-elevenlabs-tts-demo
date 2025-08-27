package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.spring.LogbookClientHttpRequestInterceptor;

/**
 * Spring AI Text-to-Speech 流式响应应用程序主类
 * 使用ElevenLabs API提供实时语音合成服务
 * 
 * 主要功能：
 * 1. 集成Spring AI框架
 * 2. 配置ElevenLabs Text-to-Speech服务
 * 3. 支持实时音频流响应
 * 4. 集成HTTP请求日志记录
 */
@SpringBootApplication
public class SpringAITextToSpeechElevenlabsStreamingResponseApplication {

    /**
     * 应用程序入口点
     * 启动Spring Boot应用程序，初始化所有必要的组件和服务
     * 
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringAITextToSpeechElevenlabsStreamingResponseApplication.class, args);
    }

    /**
     * 配置RestClient自定义器Bean，集成Logbook进行HTTP日志记录
     * 该Bean为所有Spring发出的REST客户端调用添加拦截器
     * 允许我们记录发送到AI模型的请求和接收到的响应
     * 
     * 这对于调试和监控AI服务的交互非常有用，特别是：
     * - 跟踪API调用频率和响应时间
     * - 调试请求参数和响应格式
     * - 监控服务可用性和错误率
     * 
     * @param logbook Logbook实例，用于HTTP请求/响应日志记录
     * @return 配置了日志拦截器的RestClientCustomizer
     */
    @Bean
    public RestClientCustomizer restClientCustomizer(Logbook logbook) {
        return restClientBuilder -> restClientBuilder.requestInterceptor(new LogbookClientHttpRequestInterceptor(logbook));
    }

}

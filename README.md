# ⚡ Spring AI ElevenLabs 流式 Text-to-Speech 演示

基于 **Spring AI** 和 **ElevenLabs** 构建的**实时低延迟文本转语音流式传输**演示项目。展示如何构建先进的TTS系统，实现音频的即时播放，创造流畅响应的用户体验。

## ✨ 项目特性

本应用展示了一个**完整的实时文本转语音流式系统**，包含以下功能：

- 🎯 **REST API端点** - 支持实时流式文本转语音音频传输
- ⚡ **低延迟播放** - 在完整音频生成前即可开始播放
- 🌐 **交互式Web界面** - 中英双语界面，实时测试流式功能
- 🎵 **多语言支持** - 支持中文、英文等多语言语音合成
- 🔧 **参数可调** - 支持语音稳定性、相似度、风格、速度等参数调节

## 🏗️ 技术架构

### 后端技术栈
- **Spring Boot 3.5.4** - 现代化Java应用框架
- **Spring AI 1.1.0-SNAPSHOT** - AI集成框架
- **ElevenLabs Text-to-Speech API** - 高质量语音合成服务
- **Reactor** - 响应式编程支持流式数据处理
- **Logbook** - HTTP请求/响应日志记录

### 前端技术栈
- **纯HTML5/CSS3/JavaScript** - 无框架依赖
- **MediaSource Extensions (MSE)** - 支持实时音频流播放
- **Fetch API with Streaming** - 现代化流式数据接收
- **中英双语界面** - 提升用户体验

## 📦 环境配置

### 必需的环境变量

运行应用程序前，请确保设置以下环境变量：

```bash
export ELEVENLABS_API_KEY=your_elevenlabs_api_key_here
```

### 获取 ElevenLabs API Key

1. 访问 [ElevenLabs 官网](https://elevenlabs.io/)
2. 注册账户并登录
3. 在控制台中获取您的API密钥
4. 将密钥设置为环境变量

## 🚀 快速开始

### 1. 克隆项目

```bash
git clone https://github.com/lskun/spring-ai-elevenlabs-tts-demo.git
cd spring-ai-elevenlabs-tts-demo
```

### 2. 配置环境

```bash
# 设置 ElevenLabs API Key
export ELEVENLABS_API_KEY=your_api_key_here
```

### 3. 运行应用

```bash
# 使用 Maven 运行
./mvnw spring-boot:run

# 或者构建后运行
./mvnw clean package
java -jar target/spring-ai-text-to-speech-elevenlabs-streaming-response-0.0.1-SNAPSHOT.jar
```

### 4. 访问应用

打开浏览器访问：http://localhost:8080

## 🎮 使用指南

### Web界面操作

1. **输入文本** - 在文本框中输入要转换的文本（支持中英文）
2. **选择语音** - 从下拉菜单选择不同的Voice ID
3. **调整参数** - 根据需要调整以下参数：
   - **Stability 稳定性** (0.0-1.0) - 控制语音稳定性
   - **Similarity Boost 相似度增强** (0.0-1.0) - 控制与原始声音的相似度
   - **Style 语音风格** (0.0-1.0) - 控制表达风格强度
   - **Speaker Boost 说话者增强** - 提高语音清晰度
   - **Speed 语速** (0.25-4.0) - 调整播放速度

4. **开始转换** - 点击"🎵 生成流式语音"按钮
5. **实时播放** - 音频将在生成过程中实时开始播放

### API端点

#### POST /api/tts/stream

**请求体示例：**
```json
{
  "text": "你好！这是一个文本转语音演示。",
  "voiceId": "pNInz6obpgDQGcFmaJgB",
  "stability": 0.75,
  "similarityBoost": 0.75,
  "style": 0.0,
  "useSpeakerBoost": true,
  "speed": 1.0
}
```

**响应：**
- Content-Type: audio/mpeg
- 流式MP3音频数据

## 🔧 项目结构

```
src/
├── main/
│   ├── java/com/demo/
│   │   ├── SpringAITextToSpeechElevenlabsStreamingResponseApplication.java
│   │   ├── controller/
│   │   │   └── TextToSpeechController.java
│   │   └── dto/
│   │       └── TextToSpeechRequest.java
│   └── resources/
│       ├── application.yml
│       └── static/
│           └── index.html
└── test/ (测试代码)
```

## 🛠️ 开发说明

### 核心实现原理

1. **流式传输** - 使用Spring的StreamingResponseBody实现音频数据的流式传输
2. **响应式处理** - 基于Reactor的Flux处理异步音频流
3. **实时播放** - 前端使用MediaSource Extensions技术实现边接收边播放
4. **错误处理** - 完善的异常处理和用户提示机制

### 关键技术细节

- **音频格式**：MP3, 44.1kHz, 128kbps
- **模型选择**：eleven_multilingual_v2（支持多语言）
- **流式缓冲**：智能的音频块缓冲和播放策略
- **日志记录**：完整的HTTP请求/响应日志记录

## 📝 许可证

本项目基于 MIT 许可证开源。详情请查看 [LICENSE](LICENSE) 文件。

## 🤝 贡献

欢迎提交Issue和Pull Request来改进项目！

---

**注意事项：**
- 请妥善保管您的ElevenLabs API密钥，不要将其提交到版本控制系统中
- 使用前请确保已安装Java 17+和Maven 3.6+
- 建议在现代浏览器（Chrome、Firefox、Safari、Edge）中使用Web界面
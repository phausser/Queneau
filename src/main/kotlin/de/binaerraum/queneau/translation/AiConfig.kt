package de.binaerraum.queneau.translation

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.openai.OpenAiChatModel
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AiConfig {

    @Bean
    fun chatClient(chatModel: OpenAiChatModel): ChatClient {
        return ChatClient.builder(chatModel).build()
    }
}

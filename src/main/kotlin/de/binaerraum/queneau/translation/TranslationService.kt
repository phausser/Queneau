package de.binaerraum.queneau.translation

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.prompt.PromptTemplate
import org.springframework.stereotype.Service

@Service
class TranslationService(private val chatClient: ChatClient) {

    fun translateWithStyle(text: String, style: String, language: String): String? {
        val promptTemplate = PromptTemplate(
            "Übersetze den folgenden Text ins {language} im Stil von '{style}' aus Raymond Queneaus Stilübungen: {text}"
        )
        val prompt = promptTemplate.create(mapOf("language" to language, "style" to style, "text" to text))
        return chatClient.prompt(prompt).call().content()
    }
}

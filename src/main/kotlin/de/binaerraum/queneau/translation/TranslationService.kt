package de.binaerraum.queneau.translation

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.prompt.PromptTemplate
import org.springframework.stereotype.Service

@Service
class TranslationService(private val chatClient: ChatClient) {

    fun translateWithStyle(text: String, style: String): String? =
        PromptTemplate(TRANSLATION_PROMPT)
            .create(mapOf("style" to style, "text" to text))
            .let { prompt -> chatClient.prompt(prompt).call().content() }

    private companion object {
        val TRANSLATION_PROMPT = """
            Übersetze den folgenden Text ins deutsche der die charakteristischen Merkmale von Raymund Queneaus 
            '{style}' in Stilübungen aufgreift: 
            {text}. 
            
            Antworte nur mit der Übersetzung, ohne weitere Erklärungen, Kommentare oder Entschuldigung.
        """.trimIndent()
    }
}

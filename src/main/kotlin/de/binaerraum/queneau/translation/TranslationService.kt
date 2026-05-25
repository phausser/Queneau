package de.binaerraum.queneau.translation

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.prompt.PromptTemplate
import org.springframework.stereotype.Service

@Service
class TranslationService(private val chatClient: ChatClient) {

    // Jetzt empfangen wir sowohl den Stilnamen als auch die ausführliche Beschreibung
    fun translateWithStyle(text: String, styleName: String, styleDescription: String): String? =
        PromptTemplate(TRANSLATION_PROMPT)
            .create(mapOf("styleName" to styleName, "styleDescription" to styleDescription, "text" to text))
            .let { prompt -> chatClient.prompt(prompt).call().content() }

    private companion object {
        val TRANSLATION_PROMPT = """
            Übersetze den folgenden Text ins Deutsche und gestalte die Ausgabe so, dass sie die charakteristischen
            Merkmale der Stilübung '{styleName}' aufgreift.

            Beschreibung der Stilübung:
            {styleDescription}

            Ursprungstext:
            {text}

            Antworte nur mit der Übersetzung, ohne weitere Erklärungen, Kommentare oder Entschuldigungen.
        """.trimIndent()
    }
}

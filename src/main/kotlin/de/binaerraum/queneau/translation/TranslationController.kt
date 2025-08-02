package de.binaerraum.queneau.translation

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class TranslationController(
    private val translationService: TranslationService
) {

    companion object {
        // Liste der 99 Stile aus Queneaus "Stilübungen" (deutsche Übersetzung)
        val STYLES = listOf(
            "Notizen", "Doppelte Buchführung", "Litotes", "Metaphorisch", "Rückläufig", "Überraschungen", "Traum",
            "Voraussage", "Synchysis", "Regenbogen", "Wortspiel", "Zögern", "Präzision",
            "Subjektive Seite", "Andere Subjektivität", "Erzählung", "Wortzusammensetzung", "Negationen",
            "Animismus", "Anagramme", "Unterscheidung", "Homöoptoten", "Amtsbrief", "Klappentext", "Onomatopöie",
            "Logische Analyse", "Beharren", "Unwissenheit", "Vergangenheit", "Gegenwart", "Indirekte Rede", "Passiv",
            "Alexandriner", "Polyptoton", "Apherese", "Apokope", "Synkope", "Persönlich sprechen",
            "Ausrufungen", "Weißt du", "Vornehm", "Cockney", "Kreuzverhör", "Komödie", "Zwischenbemerkungen",
            "Parechese", "Spektral", "Philosophisch", "Apostrophe", "Linkisch", "Lässig", "Voreingenommen", "Sonett",
            "Geruchlich", "Geschmacklich", "Tastend", "Visuell", "Akustisch", "Telegrafisch", "Ode",
            "Permutationen nach Gruppen von 2, 3, 4 und 5 Buchstaben",
            "Permutationen nach Gruppen von 5, 6, 7 und 8 Buchstaben",
            "Permutationen nach Gruppen von 9, 10, 11 und 12 Buchstaben",
            "Permutationen nach Gruppen von 1, 2, 3 und 4 Wörtern",
            "Hellenismen", "Reaktionär", "Haiku", "Freier Vers", "Feminin", "Gallizismen", "Prothese",
            "Epenthese", "Paragoge", "Wortarten", "Metathese", "Konsequenzen", "Eigennamen",
            "Reimslang", "Rückwärtsslang", "Antiphrasis", "Kauderwelsch", "Mehr oder weniger", "Opernenglisch",
            "Für die Franzosen", "Spoonerismen", "Botanisch", "Medizinisch", "Beschimpfend", "Gastronomisch", "Zoologisch",
            "Nutslos", "Moderner Stil", "Probabilistisch", "Porträt", "Mathematisch", "Westindisch", "Zwischenrufe",
            "Gezierter Stil", "Unerwartet"
        )

        // Beispielhafte Sprachen (auf Deutsch benannt)
        val LANGUAGES = listOf("Englisch", "Deutsch", "Französisch", "Spanisch", "Italienisch")
    }

    @GetMapping("/")
    fun showForm(model: Model): String {
        model.addAttribute("styles", STYLES)
        model.addAttribute("languages", LANGUAGES)
        return "translation/index"
    }

    @PostMapping("/translate")
    fun translate(
        @RequestParam text: String,
        @RequestParam style: String,
        @RequestParam language: String,
        model: Model
    ): String {
        val result = translationService.translateWithStyle(text, style, language)
        model.addAttribute("originalText", text)
        model.addAttribute("style", style)
        model.addAttribute("language", language)
        model.addAttribute("translatedText", result)
        model.addAttribute("styles", STYLES)
        model.addAttribute("languages", LANGUAGES)
        return "translation/index"
    }
}
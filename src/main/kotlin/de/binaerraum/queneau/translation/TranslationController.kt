package de.binaerraum.queneau.translation

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class TranslationController(
    private val translationService: TranslationService
) {

    companion object {
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
    }

    @GetMapping("/")
    fun showForm(model: Model): String {
        model.addAttribute("styles", STYLES)
        return "translation/index"
    }

    @GetMapping("/exercice-de-style")
    @ResponseBody
    fun translate(
        @RequestParam text: String,
        @RequestParam style: String,
    ) = translationService.translateWithStyle(text, style)
}
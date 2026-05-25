package de.binaerraum.queneau.translation

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class TranslationController(
    private val translationService: TranslationService
) {

    companion object {
        val stilUebungen = listOf(
            "Angaben" to "Schreibe eine möglichst neutrale, sachliche und knappe Notiz der Begebenheit wie ein Polizeibericht oder eine Aktennotiz. Keine Emotionen, keine Wertungen, rein faktisch.",
            "Verdoppelung" to "Erzähle die Geschichte zweimal direkt nacheinander: einmal aus der Sicht des Erzählers (Soll) und einmal aus der Sicht eines anderen Beteiligten (Haben) – wie doppelte Buchführung.",
            "Litotes" to "Beschreibe alles durch Untertreibung und Verneinung des Gegenteils. Verwende viele Litotes (z. B. ‚nicht ungeschickt‘ statt ‚geschickt‘).",
            "Metaphorisch" to "Verwende eine stark bildhafte, poetische Sprache voller Metaphern, Vergleiche und bildhafter Ausdrücke. Alles wird metaphorisch überhöht.",
            "Rückläufig" to "Erzähle die gesamte Geschichte rückwärts, beginnend beim Ende und chronologisch zurück bis zum Anfang.",
            "Überraschungen" to "Jeder einzelne Satz endet mit einer unerwarteten, überraschenden oder absurden Wendung. Der Leser soll ständig überrascht werden.",
            "Traum" to "Erzähle die Begebenheit wie einen surrealen Traum: fließende Übergänge, verzerrte Logik, symbolhafte Elemente und traumhafte Atmosphäre.",
            "Voraussage" to "Erzähle die Geschichte als Vorhersage der Zukunft, als würde ein Prophet oder Wahrsager das Geschehen ankündigen.",
            "Synchysis" to "Verwende eine stark verworrene, durcheinandergewürfelte Syntax. Wörter und Satzteile sind chaotisch angeordnet, ohne klare Struktur.",
            "Regenbogen" to "Beschreibe die Szene extrem farbenreich. Jede Person, jedes Objekt und jede Handlung wird mit intensiven, leuchtenden Farbzuordnungen versehen.",
            "Wortspiel" to "Fülle den Text mit Wortspielen, Kalauern, Homonymen, Paronomasien und sprachlichen Spielereien.",
            "Zögern" to "Der Erzähler wirkt unsicher, zögerlich und ängstlich. Viele Pausen, Wiederholungen, Selbstkorrekturen und Füllwörter (‚äh‘, ‚also‘, ‚hmm‘).",
            "Genauigkeiten" to "Übertriebene, fast pedantische Genauigkeit. Uhrzeiten, Maße, Entfernungen, Details und Präzision stehen im Vordergrund.",
            "Subjektive Seite" to "Erzähle die Geschichte stark subjektiv aus der emotionalen, persönlichen Sicht des Ich-Erzählers.",
            "Andere Subjektivität" to "Erzähle die Begebenheit aus der subjektiven Sicht eines anderen Fahrgasts im Bus – mit dessen persönlicher Wahrnehmung und Meinung.",
            "Erzählung" to "Schreibe eine konventionelle, neutrale, gut lesbare Erzählung im klassischen literarischen Stil.",
            "Wortzusammensetzung" to "Bilde viele neue, lange zusammengesetzte Wörter (Neologismen) durch kreative Wortverschmelzungen.",
            "Negationen" to "Verwende extrem viele Verneinungen und doppelte Negationen. Beschreibe fast alles durch das, was es nicht ist.",
            "Animismus" to "Belebe alle leblosen Gegenstände. Stühle, Hüte, Busse, Türen etc. haben eigene Gefühle, Absichten und Handlungen.",
            "Anagramme" to "Verwende viele Anagramme: Wörter und Namen werden durch Buchstabenvertauschung verändert und neu gebildet.",
            "Distinguo" to "Mache ständig begriffliche Unterscheidungen (Distinguo). Trenne genau zwischen ähnlichen Begriffen und präzisiere ununterbrochen.",
            "Homöoteleuton" to "Verwende viele Wörter mit gleichen oder ähnlichen Endungen in einem Satz (Homöoteleuton), fast wie ein Reim innerhalb der Prosa.",
            "Amtlicher Brief" to "Schreibe den Vorfall als formellen behördlichen Brief oder Amtsschreiben mit typischer Amtssprache und Bürokratendeutsch.",
            "Klappentext" to "Verfasse einen reißerischen, werbenden Klappentext wie für ein Buch oder eine Filmankündigung.",
            "Lautmalereien" to "Verwende viele Onomatopöien und lautmalerische Wörter, um Geräusche möglichst direkt nachzuahmen.",
            "Logische Analyse" to "Zerlege die Begebenheit streng logisch in Prämissen, Schlussfolgerungen und logische Beziehungen.",
            "Beharrlichkeit" to "Der Erzähler wiederholt ständig dieselben Punkte und beharrt penetrant auf bestimmten Details.",
            "Ignoranz" to "Der Erzähler tut so, als wüsste er fast nichts über das Geschehen und bleibt bewusst unwissend und naiv.",
            "Vollendete Gegenwart" to "Erzähle alles im Perfekt (vollendete Gegenwart), als sei die Handlung gerade erst abgeschlossen.",
            "Gegenwart" to "Erzähle die gesamte Geschichte im Präsens (historisches Präsens).",
            "Vergangenheit" to "Erzähle im Imperfekt / Präteritum, klassische Vergangenheitsform.",
            "Indirekte Rede" to "Verwende fast ausschließlich indirekte Rede (reported speech) mit Konjunktiv.",
            "Passiv" to "Verwende fast nur Passivkonstruktionen. Die handelnden Personen treten stark in den Hintergrund.",
            "Alexandriner" to "Schreibe den gesamten Text in klassischen französischen Alexandrinern (12-silbige Verse mit Zäsur).",
            "Polyptoton" to "Wiederhole denselben Wortstamm in verschiedenen grammatikalischen Formen (Polyptoton).",
            "Aphärese" to "Schneide systematisch den Anfang von Wörtern ab (Apherese).",
            "Apokope" to "Schneide systematisch das Ende von Wörtern ab (Apokope).",
            "Synkope" to "Lasse systematisch Silben oder Buchstaben in der Wortmitte weg (Synkope).",
            "Persönlich sprechen" to "Sehr umgangssprachlich, persönlich und direkt, mit vielen umgangssprachlichen Floskeln (‚weißt du‘, ‚Mann‘, ‚ey‘).",
            "Ausrufungen" to "Verwende extrem viele Ausrufezeichen und Ausrufesätze. Der Text wirkt aufgeregt und enthusiastisch.",
            "Weißt du" to "Der Erzähler verwendet ständig Füllphrasen wie ‚weißt du‘, ‚nicht wahr‘, ‚oder?‘, ‚gell?‘.",
            "Vornehm" to "Gehobener, aristokratischer, sehr vornehmer und gestelzter Sprachstil.",
            "Cockney" to "Erzähle im Stil des Londoner Cockney-Dialekts (angepasst ins Deutsche).",
            "Kreuzverhör" to "Schreibe wie ein polizeiliches Kreuzverhör: Fragen und Antworten, scharfer, misstrauischer Ton.",
            "Komödie" to "Verfasse den Text als Theaterkomödie mit Szenenangaben, Regieanweisungen und Dialogen.",
            "Zwischenbemerkungen" to "Füge ständig lange, abschweifende Zwischenbemerkungen und Parenthesen ein.",
            "Parechese" to "Starke Verwendung von Alliterationen, Assonanzen und klanglichen Ähnlichkeiten.",
            "Spektral" to "Beschreibe alles mit physikalisch-optischen und spektralen Begriffen (Licht, Farbspektrum, Strahlen etc.).",
            "Philosophisch" to "Tief philosophischer, abstrakter und reflektierender Ton mit existentiellen Betrachtungen.",
            "Apostrophe" to "Richte die Erzählung direkt an eine abwesende Person oder Sache (Apostrophe).",
            "Linkisch" to "Ungeschickter, linkischer, holpriger und etwas unbeholfener Erzählstil.",
            "Lässig" to "Sehr lässiger, cooler, jugendlicher und entspannter Slang-Stil.",
            "Voreingenommen" to "Stark voreingenommener, parteiischer und vorurteilsbeladener Bericht.",
            "Sonett" to "Verfasse die Geschichte als klassisches Sonett (14 Zeilen, feste Reimstruktur).",
            "Geruchlich" to "Beschreibe die Szene hauptsächlich über Gerüche und olfaktorische Eindrücke.",
            "Geschmacklich" to "Beschreibe alles über Geschmacksempfindungen und gustatorische Metaphern.",
            "Tastend" to "Konzentriere dich auf taktile und haptische Eindrücke (Berührungen, Oberflächen, Temperaturen).",
            "Visuell" to "Rein visuelle Beschreibung: Formen, Farben, Licht, Bewegungen – wie eine Filmszene.",
            "Akustisch" to "Beschreibe die Szene vor allem über Geräusche, Klänge und akustische Eindrücke.",
            "Telegrafisch" to "Telegramm-Stil: extrem kurze Sätze, Stichpunkte, Abkürzungen, keine überflüssigen Wörter.",
            "Ode" to "Schreibe eine feierliche, erhabene Ode an die Begebenheit oder eine beteiligte Person.",
            "Permutationen nach Gruppen von 2, 3, 4 und 5 Buchstaben" to "Permutiere die Buchstaben innerhalb kleiner Gruppen von 2 bis 5 Buchstaben und erzeuge so neue Wörter.",
            "Permutationen nach Gruppen von 5, 6, 7 und 8 Buchstaben" to "Permutiere Buchstaben in mittelgroßen Gruppen von 5 bis 8 Buchstaben.",
            "Permutationen nach Gruppen von 9, 10, 11 und 12 Buchstaben" to "Permutiere Buchstaben in großen Gruppen von 9 bis 12 Buchstaben.",
            "Permutationen nach Gruppen von 1, 2, 3 und 4 Wörtern" to "Permutiere die Reihenfolge von Wortgruppen (1 bis 4 Wörter) innerhalb der Sätze.",
            "Hellenismen" to "Verwende viele griechische Fremdwörter, Hellenismen und antike Ausdrucksweisen.",
            "Reaktionär" to "Erzähle in einem reaktionären, extrem konservativen und altmodischen Ton.",
            "Haiku" to "Verfasse die Geschichte in der Form mehrerer Haikus (5-7-5 Silben).",
            "Freier Vers" to "Schreibe in freien Versen ohne festes Metrum oder Reimschema.",
            "Feminin" to "Übertrieben femininer, zarter, koketter und ‚süßer‘ Sprachstil.",
            "Gallizismen" to "Verwende viele französische Wörter und gallizistische Wendungen im deutschen Text.",
            "Prothese" to "Füge systematisch Buchstaben oder Silben am Anfang von Wörtern hinzu.",
            "Epenthese" to "Füge Buchstaben oder Silben innerhalb von Wörtern ein.",
            "Paragoge" to "Füge Buchstaben oder Silben am Ende von Wörtern hinzu.",
            "Wortarten" to "Spiele stark mit Wortarten: substantiviere Verben, verbalisiere Substantive usw.",
            "Metathese" to "Vertausche Buchstaben innerhalb von Wörtern (Metathese).",
            "Konsequenzen" to "Erzähle die Geschichte als Kette von kausalen Konsequenzen (‚darum‘, ‚folglich‘, ‚deshalb‘).",
            "Eigennamen" to "Erzähle die Geschichte fast nur mit Eigennamen von Personen und Orten.",
            "Reimslang" to "Verwende Verlan (französischer Reim-Slang) oder deutsche Entsprechungen.",
            "Rückwärtsslang" to "Sprich viele Wörter rückwärts (à l’envers).",
            "Antiphrasis" to "Verwende Antiphrasis: sage das Gegenteil von dem, was gemeint ist (ironisch).",
            "Kauderwelsch" to "Mische verschiedene Sprachen, Dialekte und Jargons zu einem Kauderwelsch.",
            "Mehr oder weniger" to "Verwende ständig abschwächende Formulierungen wie ‚mehr oder weniger‘, ‚einigermaßen‘, ‚ziemlich‘.",
            "Opernenglisch" to "Gespreiztes, opernhaftes, pseudo-englisch-deutsches Pathos.",
            "Für die Franzosen" to "Erkläre alles sehr ausführlich und belehrend, als müsste man es Franzosen erklären.",
            "Spoonerismen" to "Verwende viele Spoonerismen (Vertauschung von Anlauten benachbarter Wörter).",
            "Botanisch" to "Beschreibe alles mit botanischer Fachsprache und Pflanzenmetaphern.",
            "Medizinisch" to "Verwende medizinische und klinische Fachsprache.",
            "Beschimpfend" to "Der Erzähler beschimpft die Beteiligten ständig auf vulgäre und beleidigende Weise.",
            "Gastronomisch" to "Beschreibe die Szene mit kulinarischer und gastronomischer Fachsprache.",
            "Zoologisch" to "Verwende zoologische Fachbegriffe und vergleiche Menschen mit Tieren.",
            "Nutzlos" to "Füge viele überflüssige, redundante und unnötige Formulierungen ein.",
            "Moderner Stil" to "Zeitgenössischer, journalistischer, moderner und lässiger Schreibstil.",
            "Probabilistisch" to "Beschreibe alles in Wahrscheinlichkeiten, Statistiken und Zufallstermen.",
            "Porträt" to "Erstelle ein detailliertes literarisches Porträt der beteiligten Personen.",
            "Mathematisch" to "Formuliere die Geschichte mit mathematischen Begriffen, Formeln und logischen Symbolen.",
            "Westindisch" to "Erzähle im Stil westindischer Kreolsprache bzw. mit karibischem Einschlag.",
            "Zwischenrufe" to "Füge viele spontane Zwischenrufe und Kommentare ein (‚Ach!‘, ‚Nein!‘, ‚Tatsächlich?‘).",
            "Gezierter Stil" to "Sehr geziert, affektiert, gekünstelt und übertrieben stilisiert.",
            "Unerwartet" to "Der Text bricht ständig mit Erwartungen des Lesers durch abrupte Stilwechsel und unerwartete Wendungen."
        )
    }

    @GetMapping("/")
    fun showForm(model: Model): String {
        // Liste von Stilübungen an das Template übergeben
        model.addAttribute("styles", stilUebungen)
        return "translation/index"
    }

    @GetMapping("/exercice-de-style")
    @ResponseBody
    fun translate(
        @RequestParam text: String,
        @RequestParam style: String,
    ): String? {
        // Validieren, dass die übergebene Stilbezeichnung in der Liste vorhanden ist
        val pair = stilUebungen.find { it.first == style }
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown style: $style")

        // Name und Beschreibung an den Service weiterreichen
        return translationService.translateWithStyle(text, pair.first, pair.second)
    }
}
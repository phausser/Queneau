# GitHub Copilot Instructions

## Projektkontext

Dies ist **Queneau**, eine Spring-Boot-Webanwendung in Kotlin. Sie nimmt Texteingaben entgegen und formuliert diese im Stil von Raymond Queneaus *Stilübungen* mithilfe der OpenAI-API um.

## Technologie-Stack

- **Sprache:** Kotlin (JVM 21)
- **Framework:** Spring Boot 3.x
- **Template-Engine:** Thymeleaf
- **Sicherheit:** Spring Security (HTTP Basic)
- **AI-Integration:** Spring AI (OpenAI via `ChatClient`)
- **Build:** Gradle (Kotlin DSL)
- **Tests:** JUnit 5 + kotlin-test

## Copilot-Richtlinien

### Code-Stil
- Kotlin-Idiome bevorzugen: `data class`, Extension Functions, `let`/`run`/`apply`/`also`/`with`.
- Nullsicherheit konsequent nutzen: Fragezeichen-Typen und Elvis-Operator statt expliziter null-Checks.
- Funktionen kurz halten; Single-Responsibility-Prinzip beachten.
- Kommentare auf **Deutsch**; Commit-Messages und PR-Beschreibungen auf **Englisch**.

### Spring-spezifisch
- Konstruktor-Injection bevorzugen (kein `@Autowired` auf Feldern).
- Services mit `@Service`, Controller mit `@Controller` / `@RestController` annotieren.
- Konfiguration über `application.properties`; sensitive Werte **niemals** hardcoden.
- Spring AI `ChatClient` für alle OpenAI-Aufrufe verwenden.

### Sicherheit
- Niemals Secrets (API-Keys, Passwörter) in den Code oder in Konfigurationsdateien ohne Profil-Trennung schreiben.
- Sensible Werte gehören ausschließlich in `application-local.properties` (gitignored) oder Umgebungsvariablen.
- `WebSecurityConfig.kt` nicht ohne Rücksprache verändern.

### Tests
- Für jeden Service einen entsprechenden Unit-Test anlegen.
- `@SpringBootTest` nur für echte Integrationstests verwenden.
- MockK oder Mockito für Test-Doubles nutzen.

### Abhängigkeiten
- Neue Abhängigkeiten begründen und in `build.gradle.kts` eintragen.
- Dependabot verwaltet Updates automatisch – keine manuellen Downgrades.

## Wichtige Dateien

| Datei | Zweck |
|---|---|
| `src/.../translation/TranslationService.kt` | Kernlogik und Prompt-Erstellung |
| `src/.../translation/TranslationController.kt` | HTTP-Endpunkte |
| `src/.../translation/AiConfig.kt` | OpenAI-Client-Konfiguration |
| `src/.../app/WebSecurityConfig.kt` | Security-Konfiguration |
| `src/main/resources/application.properties` | App-Konfiguration |
| `build.gradle.kts` | Abhängigkeiten und Build-Logik |
| `.github/workflows/deploy.yaml` | CI/CD-Pipeline |
| `.github/dependabot.yml` | Automatische Abhängigkeits-Updates |


# AGENTS.md – Hinweise für KI-Agenten

Dieses Dokument beschreibt Konventionen und Richtlinien für KI-Agenten (z. B. GitHub Copilot, OpenAI Codex, Cursor), die an diesem Projekt arbeiten.

## Projektüberblick

**Queneau** ist eine Spring-Boot-Webanwendung (Kotlin), die Texte mithilfe der OpenAI-API im Stil von Raymond Queneaus *Stilübungen* umformuliert.

| Aspekt | Details |
|---|---|
| Sprache | Kotlin (JVM 21) |
| Framework | Spring Boot 3.x mit Thymeleaf, Spring Security, Spring AI |
| Build | Gradle (Kotlin DSL) |
| Deployment | Oracle Cloud VM via GitHub Actions (SCP + SSH) |

## Verzeichnisstruktur

```
src/main/kotlin/de/binaerraum/queneau/
├── QueneauApplication.kt          # Einstiegspunkt
├── app/
│   └── WebSecurityConfig.kt       # Spring-Security-Konfiguration
└── translation/
    ├── AiConfig.kt                # OpenAI-/Spring-AI-Konfiguration
    ├── TranslationController.kt   # Web-Controller (GET/POST)
    └── TranslationService.kt      # Kernlogik: Prompt-Erstellung & AI-Aufruf

src/main/resources/
├── application.properties         # Konfiguration (ohne Secrets)
├── application-local.properties   # Lokale Overrides (nicht einchecken)
├── static/                        # CSS & JavaScript
└── templates/translation/         # Thymeleaf-Templates
```

## Coding-Konventionen

- **Sprache:** Kotlin-Idiome bevorzugen (data classes, extension functions, scope functions).
- **Stil:** Offizielle [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html) einhalten.
- **Kommentare:** Deutsch im Quellcode; Commit-Messages und PR-Beschreibungen auf Englisch.
- **Tests:** Unit-Tests mit JUnit 5 + Kotlin-Test; Integrationstests mit `@SpringBootTest`.
- **Secrets:** Niemals API-Keys oder Passwörter einchecken. Lokale Werte gehören in `application-local.properties` (in `.gitignore`).

## Abhängigkeiten hinzufügen

Neue Abhängigkeiten immer in `build.gradle.kts` eintragen. Dependabot überwacht alle Gradle- und GitHub-Actions-Abhängigkeiten automatisch.

## CI/CD

- Der Workflow `.github/workflows/deploy.yaml` baut die App und deployt sie auf eine Oracle Cloud VM.
- Tests werden im CI-Lauf übersprungen (`-x test`); bitte lokal vor dem Push ausführen.
- Merges in `main` lösen ein automatisches Deployment aus.

## Wichtige Befehle

```bash
./gradlew build          # Projekt bauen
./gradlew bootRun        # Lokal starten
./gradlew test           # Tests ausführen
./gradlew clean build    # Sauber bauen
```

## Was KI-Agenten **nicht** tun sollen

- `application-local.properties` oder `.env`-Dateien erstellen/befüllen.
- Spring-Security komplett deaktivieren.
- Abhängigkeiten ohne Begründung downgraden.
- Direkt auf `main` pushen ohne Pull Request (außer über den Deploy-Workflow).


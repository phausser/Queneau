# Queneau Textübersetzer

Textübersetzer nach Queneaus "Stilübungen"

## Voraussetzungen

- Java 21+
- Gradle 8+

## Build & Start

```bash
./gradlew build
./gradlew bootRun
```

Für lokale Entwicklung kann das Passwort in `application-local.properties` gesetzt werden (diese Datei ist in `.gitignore` und wird nicht eingecheckt):

```properties
app.security.password=mein-lokales-passwort
```

Alternativ kann die Umgebungsvariable `APP_SECURITY_PASSWORD` gesetzt werden:

```bash
export APP_SECURITY_PASSWORD=mein-passwort
./gradlew bootRun
```

## Tests ausführen

```bash
./gradlew test
```

## GitHub Secrets einrichten

Für den automatischen Deployment-Workflow werden folgende GitHub Secrets benötigt:

| Secret | Beschreibung |
|---|---|
| `APP_SECURITY_PASSWORD` | Passwort für den HTTP-Zugang zur Anwendung |
| `SSH_HOST` | IP-Adresse oder Hostname des Deployment-Servers |
| `SSH_USER` | SSH-Benutzername für den Deployment-Server |
| `SSH_PRIVATE_KEY` | Privater SSH-Schlüssel für den Deployment-Server |
| `APP_DIR` | Verzeichnis auf dem Server, in das die App deployt wird |

Die Secrets können unter **Settings → Secrets and variables → Actions** im GitHub-Repository hinzugefügt werden.

## Lizenz

Dieses Projekt steht unter der [MIT-Lizenz](LICENSE).

## Autor

Patrick Haußer / binärraum

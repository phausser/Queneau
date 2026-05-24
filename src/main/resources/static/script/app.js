const input = document.getElementById('input');
const sourceSelect = document.getElementById('style-select');
const picker = document.querySelector('.style-picker');
const styleButton = document.getElementById('style-button');
const styleOptions = document.getElementById('style-options');
const statusEl = document.getElementById('status');
const historyEl = document.getElementById('history');

let activeRequest = null;

const fallbackStyles = [
    'Notations',
    'Metaphorisch',
    'Rückwärts',
    'Telegramm',
    'Brief',
    'Amtlich'
];

function availableStyles() {
    return Array.from(sourceSelect.options)
        .slice(1)
        .map((option) => ({
            value: option.value.trim(),
            label: option.textContent.trim()
        }))
        .filter((option) => option.value && option.label);
}

function ensureSourceOptions() {
    if (availableStyles().length > 0) {
        return;
    }

    sourceSelect.length = 1;
    fallbackStyles.forEach((style) => {
        const option = document.createElement('option');
        option.value = style;
        option.textContent = style;
        sourceSelect.appendChild(option);
    });
}

function closePicker() {
    picker.classList.remove('is-open');
    styleButton.setAttribute('aria-expanded', 'false');
}

function openPicker() {
    picker.classList.add('is-open');
    styleButton.setAttribute('aria-expanded', 'true');
}

function renderStyleOptions() {
    styleOptions.replaceChildren();

    availableStyles().forEach((style) => {
        const item = document.createElement('li');
        item.setAttribute('role', 'option');

        const button = document.createElement('button');
        button.type = 'button';
        button.textContent = style.label;
        button.addEventListener('click', () => {
            sourceSelect.value = style.value;
            styleButton.textContent = style.label;
            closePicker();
            translateSelectedStyle(style.value, style.label);
        });

        item.appendChild(button);
        styleOptions.appendChild(item);
    });
}

function setStatus(message) {
    statusEl.textContent = message;
}

function setLoading(isLoading) {
    input.disabled = isLoading;
    styleButton.disabled = isLoading;
}

// Größe des Textareas dynamisch anpassen
// Ziel: das Textfeld beim Tippen wachsen lassen, aber nur so weit,
// bis die Unterkante der Select-Box (style-picker) den unteren
// Rand des Browserfensters erreicht. Beim Verkleinern des Inhalts
// wird das Feld wieder verkleinert (aber nicht unter die in CSS
// definierte min-height).
function adjustTextareaHeight() {
    // Temporär auf auto setzen, um scrollHeight korrekt zu messen
    input.style.height = 'auto';

    const scrollHeight = input.scrollHeight;
    const computedMin = parseInt(getComputedStyle(input).minHeight, 10) || 172; // Fallback wie in CSS

    const currentHeight = input.offsetHeight;

    // Aktuelle Position und Größe des Pickers ermitteln
    const pickerRect = picker.getBoundingClientRect();
    const viewportHeight = window.innerHeight;

    // Wenn wir das Textarea um delta erhöhen, verschiebt sich der Picker
    // um genau diesen delta nach unten. Maximal erlaubter delta = Abstand
    // vom aktuellen Picker-Bottom bis zur Viewport-Bottom.
    const remainingSpace = viewportHeight - pickerRect.bottom;
    const maxDelta = Math.max(0, remainingSpace);

    // Maximal mögliche neue Höhe des Textareas (nicht kleiner als jetzt)
    const maxNewHeight = currentHeight + maxDelta;

    // Gewünschte Höhe basierend auf Inhalt, begrenzt durch min/max
    const desiredHeight = Math.min(scrollHeight, maxNewHeight);
    const finalHeight = Math.max(computedMin, desiredHeight);

    input.style.height = finalHeight + 'px';
}

// Reagiere auf Eingaben und Fensteränderungen
input.addEventListener('input', adjustTextareaHeight);
window.addEventListener('resize', adjustTextareaHeight);

// Initiale Anpassung (z. B. wenn das Textfeld vorbefüllt ist)
adjustTextareaHeight();

function addTranslation(style, text) {
    const item = document.createElement('article');
    item.className = 'translation';

    const body = document.createElement('p');
    body.className = 'translation-text';

    const title = document.createElement('strong');
    title.textContent = style;

    body.append(title, document.createTextNode(`\n\n${text}`));
    item.appendChild(body);
    historyEl.prepend(item);
}

async function translateSelectedStyle(style, label) {
    const rawText = input.value.trim();

    if (!rawText) {
        setStatus('Bitte gib einen Text ein.');
        sourceSelect.value = '';
        styleButton.textContent = 'Exercices de style...';
        return;
    }

    if (activeRequest) {
        activeRequest.abort();
    }

    activeRequest = new AbortController();
    setLoading(true);
    setStatus('Übersetze...');

    try {
        const params = new URLSearchParams({ text: rawText, style });
        const response = await fetch(`/exercice-de-style?${params}`, {
            method: 'GET',
            credentials: 'same-origin',
            signal: activeRequest.signal
        });

        if (!response.ok) {
            throw new Error(`API-Aufruf fehlgeschlagen (${response.status})`);
        }

        addTranslation(label, await response.text());
        setStatus('');
    } catch (error) {
        if (error.name !== 'AbortError') {
            setStatus(`Fehler: ${error.message}`);
        }
    } finally {
        activeRequest = null;
        setLoading(false);
        sourceSelect.value = '';
        styleButton.textContent = 'Exercices de style...';
    }
}

styleButton.addEventListener('click', () => {
    if (picker.classList.contains('is-open')) {
        closePicker();
    } else {
        openPicker();
    }
});

document.addEventListener('click', (event) => {
    if (!picker.contains(event.target)) {
        closePicker();
    }
});

document.addEventListener('keydown', (event) => {
    if (event.key === 'Escape') {
        closePicker();
    }
});

ensureSourceOptions();
renderStyleOptions();

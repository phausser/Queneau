const applyBtn = document.getElementById('apply-btn');
const input = document.getElementById('input');
const styleSelect = document.getElementById('style-select');
const output = document.getElementById('output-text');
const copyBtn = document.getElementById('copy-btn');
const loadingOverlay = document.getElementById('loading-overlay');

function toggleLoading(isLoading) {
    loadingOverlay.style.display = isLoading ? 'flex' : 'none';
    input.disabled = isLoading;
    styleSelect.disabled = isLoading;
    applyBtn.disabled = isLoading;
}

applyBtn.addEventListener('click', async () => {
    const text = encodeURIComponent(input.value.trim());
    const style = styleSelect.value;

    if (!text) {
        alert('Bitte gib einen Text ein.');
        output.textContent = '';
        copyBtn.style.display = 'none';
        return;
    }

    toggleLoading(true);

    try {
        const username = 'pat';
        const password = 'pat';
        const auth = btoa(`${username}:${password}`);

        const response = await fetch(`/exercice-de-style?text=${text}&style=${style}`, {
            method: 'GET',
            headers: {
                'Authorization': `Basic ${auth}`
            }
        });

        if (!response.ok) {
            throw new Error('API-Aufruf fehlgeschlagen');
        }

        const result = await response.text();
        output.textContent = result;
        copyBtn.style.display = 'block';
    } catch (error) {
        output.textContent = 'Fehler: ' + error.message;
        copyBtn.style.display = 'none';
    } finally {
        toggleLoading(false);
    }
});

copyBtn.addEventListener('click', () => {
    const textToCopy = output.textContent;
    if (textToCopy) {
        navigator.clipboard.writeText(textToCopy).then(() => {
            alert('Text kopiert!');
        }).catch(err => {
            alert('Kopieren fehlgeschlagen: ' + err);
        });
    }
});
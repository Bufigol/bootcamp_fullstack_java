document.addEventListener('DOMContentLoaded', function() {
    const siRadio = document.getElementById('si');
    const noRadio = document.getElementById('no');
    const pwdField = document.getElementById('pwd');
    const repeatField = document.getElementById('repeat');
    const pwdLabel = document.querySelector('label[for="pwd"]');
    const repeatLabel = document.querySelector('label[for="repeat"]');

    function togglePasswordFields() {
        const isEnabled = siRadio.checked;
        pwdField.disabled = !isEnabled;
        repeatField.disabled = !isEnabled;
        pwdField.required = isEnabled;
        repeatField.required = isEnabled;

        // Update ARIA attributes for accessibility
        pwdField.setAttribute('aria-disabled', !isEnabled);
        repeatField.setAttribute('aria-disabled', !isEnabled);

        // Visual feedback
        [pwdField, repeatField, pwdLabel, repeatLabel].forEach(el => {
            el.style.opacity = isEnabled ? '1' : '0.5';
        });
    }

    siRadio.addEventListener('change', togglePasswordFields);
    noRadio.addEventListener('change', togglePasswordFields);

    // Initial state setup
    noRadio.checked = true;
    togglePasswordFields();
});
// Obtener elementos del formulario y mensajes
const form = document.getElementById('contactForm');
const errorMsg = document.getElementById('errorMsg');
const successMsg = document.getElementById('successMsg');

// Función para validar email con regex simple
function validarEmail(email) {
  const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return re.test(email);
}

// Evento submit con validación
form.addEventListener('submit', function(e) {
  e.preventDefault();

  // Limpiar mensajes previos
  errorMsg.classList.add('hidden');
  successMsg.classList.add('hidden');

  const nombre = form.nombre.value.trim();
  const email = form.email.value.trim();
  const mensaje = form.mensaje.value.trim();

  // Validaciones básicas
  if (nombre === '' || email === '' || mensaje === '') {
    errorMsg.textContent = 'Por favor, completa todos los campos.';
    errorMsg.classList.remove('hidden');
    return;
  }

  if (!validarEmail(email)) {
    errorMsg.textContent = 'Por favor, ingresa un correo electrónico válido.';
    errorMsg.classList.remove('hidden');
    return;
  }

  // Si pasa validaciones
  successMsg.classList.remove('hidden');
  
  // Reseteamos el formulario
  form.reset();
});

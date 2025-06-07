// Obtiene el parámetro "id" desde la URL
const params = new URLSearchParams(window.location.search);
const id = parseInt(params.get("id"));

// Datos estáticos de juegos disponibles
const juegos = [
  {
    id: 1,
    titulo: "The Legend of Zelda",
    consola: "NES",
    año: 1986,
    imagen: "assets/covers/zelda.png",
    descripcion: "Explora calabozos, resuelve acertijos y salva a Hyrule en esta joya clásica."
  },
  {
    id: 2,
    titulo: "Super Metroid",
    consola: "SNES",
    año: 1994,
    imagen: "assets/covers/supermetroid.png",
    descripcion: "Una aventura galáctica intensa con exploración y mejoras progresivas."
  },
  {
    id: 3,
    titulo: "Chrono Trigger",
    consola: "SNES",
    año: 1995,
    imagen: "assets/covers/chronotrigger.png",
    descripcion: "Viaja por el tiempo y salva el futuro en este legendario RPG."
  }
];

// Busca el juego correspondiente al ID obtenido
const juego = juegos.find(j => j.id === id);

// Si no se encuentra el juego, muestra mensaje de error
if (!juego) {
  document.getElementById("detalle-juego").innerHTML = "<p class='text-red-500 text-center'>Juego no encontrado.</p>";
  throw new Error("Juego no encontrado");
}

// Genera el contenido HTML con los detalles del juego
const detalle = `
  <div class="bg-[#1a1a1a] rounded-2xl p-6 shadow-lg flex flex-col md:flex-row gap-6 items-center">
    <img src="${juego.imagen}" alt="${juego.titulo}" class="w-64 rounded">
    <div>
      <h2 class="text-3xl neon mb-4">${juego.titulo}</h2>
      <p class="text-[#6c63ff] mb-2"><strong>Consola:</strong> ${juego.consola}</p>
      <p class="text-[#6c63ff] mb-2"><strong>Año:</strong> ${juego.año}</p>
      <p class="text-white mb-4">${juego.descripcion}</p>
      <a href="index.html" class="cta-glow bg-[#00ffcc] text-black px-4 py-2 rounded font-semibold inline-block">← Volver al catálogo</a>
    </div>
  </div>
`;

// Inyecta el contenido en el contenedor principal
document.getElementById("detalle-juego").innerHTML = detalle;

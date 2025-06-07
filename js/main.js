// Datos del catálogo de juegos
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

// Referencia al contenedor del catálogo
const catalogo = document.getElementById("catalogo");

// Genera tarjetas para cada juego
juegos.forEach(juego => {
  const tarjeta = document.createElement("div");
  tarjeta.className = "card-uniforme bg-[#1a1a1a] rounded-2xl p-4 shadow-md hover:shadow-[#ff2e9d] transition duration-300 flex flex-col";

  tarjeta.innerHTML = `
    <img src="${juego.imagen}" alt="${juego.titulo}" class="card-img">
    <h3 class="text-xl font-bold neon mb-2">${juego.titulo}</h3>
    <p class="text-sm text-[#6c63ff] mb-1">Consola: ${juego.consola}</p>
    <p class="text-sm text-[#6c63ff] mb-1">Año: ${juego.año}</p>
    <p class="text-sm text-white mb-4">${juego.descripcion}</p>
    <a href="detalles.html?id=${juego.id}" class="bg-[#00ffcc] text-black text-center px-4 py-2 rounded font-semibold hover:scale-105 transition">Ver detalles</a>
  `;

  catalogo.appendChild(tarjeta); // Agrega la tarjeta al catálogo
});

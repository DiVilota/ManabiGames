// Datos estáticos del catálogo de juegos clásicos
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

// Render de juegos clásicos
function mostrarClasicos() {
  const catalogo = document.getElementById("catalogo");

  juegos.forEach(juego => {
    const tarjeta = document.createElement("div");
    tarjeta.className = "card bg-[#1a1a1a] rounded-2xl p-4 shadow-md hover:shadow-[#ff2e9d] transition duration-300 flex flex-col";

    tarjeta.innerHTML = `
      <img src="${juego.imagen}" alt="${juego.titulo}" class="card-img">
      <h3 class="text-xl font-bold neon mb-2">${juego.titulo}</h3>
      <p class="text-sm text-[#6c63ff] mb-1">Consola: ${juego.consola}</p>
      <p class="text-sm text-[#6c63ff] mb-1">Año: ${juego.año}</p>
      <p class="text-sm text-white mb-4">${juego.descripcion}</p>
      <a href="detalles.html?id=${juego.id}" class="bg-[#00ffcc] text-black text-center px-4 py-2 rounded font-semibold hover:scale-105 transition">Ver detalles</a>
    `;

    catalogo.appendChild(tarjeta);
  });
}

// Render juegos desde API RAWG
async function mostrarDesdeRAWG() {
    const API_KEY = "17a8c4f8d106455baf54eda6d323173e";
  try {
    const respuesta = await fetch(`https://api.rawg.io/api/games?key=${API_KEY}&dates=1980-01-01,1999-12-31&ordering=-rating&page_size=12`);
    const data = await respuesta.json();
    
    const catalogo = document.getElementById("catalogo");

    // Consolas retro válidas
    const consolasValidas = [
      "NES", "SNES", "PlayStation", "PlayStation 2", "Sega Genesis",
      "Nintendo 64", "Game Boy", "Game Boy Advance", "Sega Saturn",
      "Dreamcast", "GameCube"
    ];

    data.results.forEach(juego => {
      const plataformas = juego.platforms?.map(p => p.platform.name) || [];
      const consola = plataformas.find(p => consolasValidas.includes(p));

      // Si no tiene consola retro compatible, lo omitimos
      if (!consola) return;

      // Extraer año desde la fecha de lanzamiento
      const fechaLanzamiento = juego.released || ""; // formato: "1994-03-11"
      const año = fechaLanzamiento ? new Date(fechaLanzamiento).getFullYear() : "Año desconocido";

      const tarjeta = document.createElement("div");
      tarjeta.className = "card bg-[#1a1a1a] rounded-2xl p-4 shadow-md hover:shadow-[#ff2e9d] transition duration-300 flex flex-col";

      tarjeta.innerHTML = `
        <img src="${juego.background_image}" alt="${juego.name}" class="card-img">
        <h3 class="text-xl font-bold neon mb-2">${juego.name}</h3>
        <p class="text-sm text-[#6c63ff] mb-1">Consola: ${consola}</p>
        <p class="text-sm text-[#6c63ff] mb-1">Año: ${año}</p>
        <p class="text-sm text-white mb-4">${juego.slug.replaceAll("-", " ")}</p>
        <a href="detalles.html?id=${juego.id}" class="bg-[#00ffcc] text-black text-center px-4 py-2 rounded font-semibold hover:scale-105 transition">Ver más</a>
      `;

      catalogo.appendChild(tarjeta);
    });

  } catch (error) {
    console.error("Error al obtener juegos desde RAWG:", error);
  }
}

// Inicialización al cargar el DOM
document.addEventListener("DOMContentLoaded", () => {
  mostrarClasicos();
  mostrarDesdeRAWG();
});

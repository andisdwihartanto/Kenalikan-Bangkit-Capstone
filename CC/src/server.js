const Hapi = require('@hapi/hapi');
const { loadModel, predict } = require('./inference');

(async () => {
  const server = Hapi.server({
    host: '0.0.0.0',
    port: process.env.PORT || 8000
  })

  const model = await loadModel();
  console.log('Model loaded successfully!');

  server.route({
    method: 'POST',
    path: '/predict',
    handler: async (request, h) => {
      try {
        const data = await request.payload;
        if (data.image) {
          const imageBuffer = data.image;
          if (!Buffer.isBuffer(imageBuffer)) {
            throw new Error('Uploaded file is not a buffer');
          }
          const predictedFish = await predict(model, imageBuffer);
          const fishDescriptions = {
            'trout': 'Ikan trout tergolong ikan berminyak dan merupakan ikan makanan penting bagi manusia. Sebagai predator tingkat menengah, ikan trout memangsa hewan air yang lebih kecil termasuk serangga, krustasea, ikan umpan, dan berudu, dan ikan trout juga merupakan mangsa utama bagi banyak satwa liar termasuk beruang coklat, berang-berang, rakun, burung pemangsa (misalnya elang laut, osprey, burung hantu ikan), burung camar, burung kormoran dan burung pekakak, serta predator air besar lainnya.',
            'red mullet': 'Seekor ikan air hangat, red mullet atau belanak merah ditemukan di seluruh Mediterania dan Laut Hitam, dengan jangkauannya meluas ke pantai utara Afrika dan pantai Spanyol, Perancis dan Portugal. Ketika sudah dewasa, ikan ini lebih menyukai perairan yang lebih dalam dan umumnya ditemukan di dasar laut berpasir, sirap, dan campuran. Ikan belanak merah merupakan ikan demersal yang menjelajahi dasar laut untuk mencari kerang, kepiting, lobster kecil dan juga memakan cacing laut serta memakan ikan mati.',
            'sea bass': 'Ikan seabass umumnya memiliki bentuk tubuh yang memanjang, kepala dan mulut yang besar, sisik yang rapat, serta ekor yang lebar dan tidak bercabang. Seabass juga menghabiskan sebagian siklus hidupnya di laut. Di piringnya, seabass memiliki rasa yang ringan dan manis dengan daging yang berubah warna menjadi putih saat dimasak.',
            'striped red mullet': 'Ikan striped red mullet atau ikan belanak merah bergaris atau surmullet (Mullus surmuletus) adalah spesies ikan yang ditemukan di Laut Mediterania, Samudera Atlantik Utara bagian timur, dan Laut Hitam. Ikan ini dapat ditemukan di perairan dangkal 5 meter (16 kaki) atau sedalam 409 meter (1.342 kaki) tergantung pada wilayah jelajahnya. Spesies ini dapat mencapai panjang 40 cm (16 inci) meskipun kebanyakan hanya sekitar 25 sentimeter (9,8 in). Berat terbesar yang tercatat untuk spesies ini adalah 1 kilogram (2,2 lb). Ikan Ini adalah spesies yang dicari sebagai ikan buruan.',
            'black sea sprat': 'Black Sea Sprat atau ikan Sprat Laut Hitam atau Sprat Pontic (Clupeonellacultriventris), adalah ikan kecil dari keluarga ikan haring, Clupeidae. Ditemukan di Laut Hitam dan Laut Azov dan sungai-sungai di cekungannya: Danube, Dnister, Dnipro (Ukraina), Bug Selatan, Don, Kuban. Dagingnya berwarna putih abu-abu dan sisiknya berwarna abu-abu keperakan. Ukuran tipikal adalah 10 cm (maksimum 15 cm). Rentang hidup hingga 5 tahun. Puncak pemijahannya terjadi pada bulan April dan dapat ditemukan di perairan dangkal yang sangat besar di tepi laut, memenuhi perairan dangkal pesisir, bergerak cepat kembali ke laut pada kedalaman 6–30 meter. Digunakan untuk makanan dan mengandung sekitar 12% lemak dalam dagingnya.',
            'hourse mackerel': 'Berasal dari perairan Mediterania dan Atlantik, hourse mackerel adalah ikan dari keluarga Carangidae. Ikan ini sudah menjadi makanan pokok di Jepang dan popularitasnya mulai meningkat di Eropa. Tubuhnya yang berwarna keperakan berukuran antara 15 dan 45 cm; ia memiliki dua sirip punggung dan rahang yang menonjol. Ciri khasnya adalah tujuh duri tipis di perut bagian bawah, yang dirancang untuk menghalangi calon predator. Sering tertukar dengan makarel “biasa”, yang dibawa bepergian di sekolah, makarel ini lebih ramping. Ikan ini juga kaya akan protein dan vitamin D.',
            'shrimp': 'Shrimp atau udang dicirikan oleh tubuh semitransparan yang rata dari sisi ke sisi dan perut fleksibel yang diakhiri dengan ekor seperti kipas. Pelengkapnya dimodifikasi untuk berenang, dan antenanya panjang dan seperti cambuk. Udang terdapat di semua lautan—di perairan dangkal dan dalam—serta di danau dan sungai air tawar.',
            'red sea bream': 'Sebagai predator puncak di antara ikan-ikan pesisir di Laut Pedalaman Seto, ikan Red Sea Bream adalah perenang yang tangguh. Ia mengandalkan gigi dan rahangnya yang kuat untuk memakan ikan kecil, krustasea, dan moluska, seperti udang, kepiting, dan kerang. Gaya hidup dan pola makan yang aktif ini menghasilkan daging ikan yang bertekstur keras, ramping, dan berwarna putih serpihan kecil, yang menghasilkan rasa yang bersih, manis, lembut, dan kaya mineral dengan kekayaan umami yang halus.',
            'gilt-head bream': 'Gilt-head bream atau ikan air asin kepala emas adalah ikan air asin semi-minyak. Mereka terkenal dengan garis emas di bagian tengah badannya serta giginya yang kuat. Mereka selalu ditangkap di bebatuan. Ikan ini umumnya dijual segar. Ikan ini diolah dengan cara yang sama seperti ikan air tawar pada umumnya dan dapat juga disajikan dengan cara direbus.'
          };
    
          const description = fishDescriptions[predictedFish.toLowerCase()] || "Tidak ada deskripsi yang tersedia.";
    
          return h.response({
            name: predictedFish,
            description: description
          }).code(200);
        } else {
          throw new Error('Tidak ada gambar ikan yang ditemukan.');
        }
      } catch (error) {
        console.error('Prediction error:', error);
        return h.response({ error: 'Gagal membuat prediksi.' }).code(500);
      }
    },
    options: {
      payload: {
        allow: 'multipart/form-data',
        multipart: true,
        output: 'data', 
        maxBytes: 209715200  
      }
    }
  });
  
  await server.start();
  console.log(`Server running on ${server.info.uri}`);
})();
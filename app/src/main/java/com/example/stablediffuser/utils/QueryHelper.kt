package com.example.stablediffuser.utils

object QueryHelper {

    private val predefinedQueries: Set<String> = setOf(
        "A beautiful portrait of a cute cyberpunk dog by greg rutkowski and wlop, purple blue color scheme, digital art, highly detailed, fine detail, intricate, ornate, complex",
        "A young female shaman, blue hair and antlers on her head. blindfolded, heilung, in the style of Heather Theurer, made by karol bak",
        "Portrait of a 19th century woman with antlers on her head, 1900s photography",
        "A beautiful painted portrait of an old woman in the jungle surrounded by leaves and plants, tribal face paintings, matte painting, fantasy art",
        "Symmetrical close - up portrait of a woman wearing a translucent silicone beauty mask and orange hair, wearing a black bodysuit by alexander mcqueen, standing in a garden full of plastic translucent flowers, black background, soft diffused light, biotechnology, humanoide robot, bjork aesthetic, translucent, by rineke dijkstra, intricate details, highly detailed, masterpiece,",
        "Renaissance portrait of a black and white cat wearing a crown, in the style of eugene de blaas",
        "A rabbit dressed as a queen wearing a crown, 18th century oil painting",
        "Portrait of bernie sanders as gandalf in lord of the rings, beautiful, very detailed, hyperrealistic, medium shot, very detailed painting by Glenn Fabry, by Joao Ruas",
        "Hyper-realistic upper-body Portrait of the King of the Desert, Warrior, Gold Armour and Crown, Sword, handsome attractive face, attractive young man, beautiful face, photo realistic, dramatic lighting, majestic, trending on artstation, elegant, intricate, highly detailed, digital painting, concept art, sharp focus, illustration, art by artgerm and greg rutkowski and alphonse mucha",
        "Joe Biden as Captain America, western, D&D, fantasy, intricate, elegant, highly detailed, digital painting, artstation, concept art, matte, sharp focus, illustration, art by Artgerm and Greg Rutkowski and Alphonse Mucha",
        "Digital painting of masked incan dog warrior, by filipe pagliuso and justin gerard, symmetric, fantasy, realistic, highly detailed, realistic, intricate, sharp focus, tarot card, portrait, peruvian",
        "Banksy art monkey with sword, wall with city street background",
        "A mystical wonderland, high fantasy, magical elements, vibrant colors, lush landscape, sharp details, high res",
        "a circle portal structure built out of plants,mushrooms, trees, curves, arches, bright cyberpunk glow, epic surrealism, forest green, orange, lime green, dull red, pale blue, Detailed digital matte painting in the style of simon stalenhag, Greg Rutkowski and Greg Hildebrandt artstation",
        "Portrait art of 8k ultra realistic retro , lens flare, atmosphere, glow, detailed,intricate,blade runner, cybernetic, full of colour, cinematic lighting, trending on artstation, 4k, hyperrealistic, focused, extreme details,unreal engine 5, cinematic, masterpiece, art by ayami kojima",
        "A fluorescent giant mushrooms forest, beautiful crystal deposits glowing on the floor, in style of laurel d austin, 2 d art, concept art, fantasy, high detail, trending on artstation",
        "Elon Musk as a Greek god, gorgeous, amazing, muscular, fit, intricate, highly detailed, digital painting, artstation, concept art, sharp focus, illustration, art by greg rutkowski and alphonse mucha",
        "Portrait of teenage medusa, snake hair, naughty smile, wearing an embroidered orange tunic, intricate, elegant, copper and emerald jewelry, glowing lights, seductive. highly detailed, digital painting, artstation, concept art, smooth, sharp focus, illustration, art by wlop, mucha, artgerm, and greg rutkowski",
        "High quality 3 d render very cute fluffy! cyborg cow plays guitar, cyberpunk highly detailed, unreal engine cinematic smooth, in the style of blade runner & detective pikachu, hannah yata charlie immer, moody light, low angle, uhd 8 k, sharp focus",
        "Concrete architecture with moss and ivy growing all over, many antennas and towers, futuristic, late afternoon light, wispy clouds in a blue sky, by frank lloyd wright and greg rutkowski and ruan jia",
        "Elevation illustration of an awesome sunny day environment concept art on a cliff, nature meets architecture by kengo kuma, ian hubert and wes anderson with village, residential area, mixed development, highrise made up staircases, balconies, full of glass facades, cgsociety, fantastic realism, artstation hq",
        "Beautiful oil painting, full length portrait of Dwayne the rock Johnson as Louis xiv in coronation robes 1701, Dan Mumford, Dan Mumford, Alex grey, Alex grey, lsd visuals, dmt fractal patterns, visionary art, psychedelic art, highly detailed, ornate, vaporwave, hallucinogen",
        "totem animal totem aztek tribal color pastel vibrant style fanart ornate fantasy heartstone gta5 cover , official behance hd artstation by Jesper Ejsing, by RHADS, Makoto Shinkai and Lois van baarle, ilya kuvshinov, rossdraws totem",
        "Detailed portrait Neon Operator Girl, cyberpunk futuristic neon, reflective puffy coat, decorated with traditional Japanese ornaments by Ismail inceoglu dragan bibin hans thoma greg rutkowski Alexandros Pyromallis Nekro Rene Maritte Illustrated, Perfect face, fine details, realistic shaded, fine-face, pretty face",
        "Symmetry!! portrait of floral! horizon zero dawn machine, intricate, elegant, highly detailed, digital painting, artstation, concept art, smooth, sharp focus, illustration, art by artgerm and greg rutkowski and alphonse mucha, 8 k",
        "Portrait of shoebill - stork - mecha - carrion crawler, intricate abstract. intricate artwork, by tooth wu, wlop, beeple, dan mumford. concept art, octane render, trending on artstation, greg rutkowski very coherent symmetrical artwork. cinematic, key art, hyper realism, high detail, octane render, 8 k, iridescent accents",
        "Fennec fox,neon pink, palm trees, furry, cute, smug facial expression, disney zootopia, zootopia, concept art, aviator sunglasses, smug expression, synthwave style, artstation, detailed, award winning, dramatic lighting, miami vice, oil on canvas, vibes, GTA criminal",
        "A cyberpunk portrait of a obama by jean - michel basquiat, by hayao miyazaki by artgerm, highly detailed, sacred geometry, mathematics, snake, geometry, cyberpunk, vibrant, water",
        "Highly detailed vfx portrait of audrey hepburn mixed with emilia clarke, geometric polygons, global illumination, detailed and intricate environment by james jean, liam brazier, victo ngai and tristan eaton",
        "Zaha hadid 3 d construction printed house on the mountain, soft light, streetscapes stunning volumetric lighting sunset",
        "Art by bill mayers, beeple, concept art, surrealist"
    )

    private val remainingQueries: MutableSet<String> = predefinedQueries.toMutableSet()

    fun randomQuery(): String {
        val query = remainingQueries.random().also { element ->
            remainingQueries.remove(element)
        }
        if (remainingQueries.isEmpty()) {
            remainingQueries.addAll(predefinedQueries)
        }
        return query
    }
}
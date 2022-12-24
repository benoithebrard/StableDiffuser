package com.example.stablediffuser.utils

object PromptGenerator {

    const val PROMPT_EXAMPLE_1 =
        "A koala in a astronaut suit, 3d, sci-fi fantasy, intricate, elegant, highly detailed, lifelike, photorealistic, digital painting, artstation, illustration, concept art, sharp focus, art in the style of Shigenori Soejima"

    const val PROMPT_EXAMPLE_2 =
        "Tattoo design, stencil, beautiful portrait of a girl with flowers in her hair, upper body, by artgerm, artgerm, digital art, cat girl"

    const val PROMPT_EXAMPLE_3 =
        "Cyberpunk depiction of the city of gdansk during arctic conditions by simon stalenhag"

    const val PROMPT_EXAMPLE_4 =
        "Dog as a god, very detailed face, detailed features, fantasy, circuitry, explosion, dramatic, intricate, elegant, highly detailed, digital painting, artstation, concept art, smooth, sharp focus, illustration, art by gustave dore, octane render"

    const val PROMPT_EXAMPLE_5 =
        "A fat fluffy tabby cat with green eyes wearing aristocratic robes and a golden necklace and a crown sitting in a garden in the style of Rembrandt"

    const val PROMPT_EXAMPLE_6 =
        "Extremely detailed awe stunning beautiful futuristic smooth curvilinear city apartment interior at night, translucent orbs, hyper real, greenery, 8k, colorful, 3D cinematic volumetric light, atmospheric light, studio ghibli inspired, high contrast, epic composition, sci-fi, dreamlike, surreal, angelic"

    const val PROMPT_EXAMPLE_7 =
        "Oil painting on wood. limited color palette. 1 8 9 6. portrait of a young lana del rey with a doomed expression in blue and gold. by herbert james draper, sir lawrence alma - tadema, john william godward. epitome of victorian era.. cinematic criterion composition by kurosawa, kubrick. trippy breathing photograph. tricks of illusion to create depth. ambient occlusion. dark shadows, bright lights. composition of renaissance. dynamic background."

    const val PROMPT_EXAMPLE_8 =
        "Large openings frame views of the villa's positioning amidst a fir - tree woodland"

    const val PROMPT_EXAMPLE_9 =
        "A beautiful and highly detailed matte painting of an elven temple in a magical fantasy garden in a lush forest in the mystical mountains, celtic knots, intricate details, epic scale, insanely complex, 8 k, sharp focus, hyperrealism, very realistic, by caspar friedrich, albert bierstadt, james gurney, brian froud"

    const val PROMPT_EXAMPLE_10 =
        "Curved futuristic room interior with spongy walls. there is a swimming pool on the floor"

    const val PROMPT_EXAMPLE_11 =
        "Allegory of the cave, platos cave, detailed, centered, digital painting, artstation, concept art, donato giancola, joseph christian leyendecker, wlop, boris vallejo, breathtaking, 8 k resolution, extremely detailed, beautiful, establishing shot, artistic, hyperrealistic, beautiful face, octane render, cinematic lighting, dramatic lighting, masterpiece"

    private val predefinedQueries: Set<String> = setOf(
        PROMPT_EXAMPLE_1,
        PROMPT_EXAMPLE_2,
        PROMPT_EXAMPLE_3,
        PROMPT_EXAMPLE_4,
        PROMPT_EXAMPLE_5,
        PROMPT_EXAMPLE_6,
        PROMPT_EXAMPLE_7,
        PROMPT_EXAMPLE_8,
        PROMPT_EXAMPLE_9,
        PROMPT_EXAMPLE_10,
        PROMPT_EXAMPLE_11
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
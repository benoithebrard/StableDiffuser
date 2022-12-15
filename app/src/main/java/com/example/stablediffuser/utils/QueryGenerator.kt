package com.example.stablediffuser.utils

object QueryGenerator {

    const val PROMPT_EXAMPLE_1 =
        "a koala in a astronaut suit, 3d, sci-fi fantasy, intricate, elegant, highly detailed, lifelike, photorealistic, digital painting, artstation, illustration, concept art, sharp focus, art in the style of Shigenori Soejima"

    const val PROMPT_EXAMPLE_2 =
        "tattoo design, stencil, beautiful portrait of a girl with flowers in her hair, upper body, by artgerm, artgerm, digital art, cat girl"

    const val PROMPT_EXAMPLE_3 =
        "Cyberpunk depiction of the city of gdansk during arctic conditions by simon stalenhag"

    const val PROMPT_EXAMPLE_4 =
        "Dog as a god, very detailed face, detailed features, fantasy, circuitry, explosion, dramatic, intricate, elegant, highly detailed, digital painting, artstation, concept art, smooth, sharp focus, illustration, art by gustave dore, octane render"

    const val PROMPT_EXAMPLE_5 =
        "A fat fluffy tabby cat with green eyes wearing aristocratic robes and a golden necklace and a crown sitting in a garden in the style of Rembrandt"

    private val predefinedQueries: Set<String> = setOf(
        PROMPT_EXAMPLE_1,
        PROMPT_EXAMPLE_2,
        PROMPT_EXAMPLE_3,
        PROMPT_EXAMPLE_4,
        PROMPT_EXAMPLE_5
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
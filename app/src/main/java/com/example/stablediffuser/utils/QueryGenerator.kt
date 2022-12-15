package com.example.stablediffuser.utils

object QueryGenerator {

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
        "Fennec fox, pink, palm trees, furry, cute, aviator sunglasses, synthwave style, artstation, detailed, award winning, dramatic lighting, miami vice, oil on canvas"

    const val PROMPT_EXAMPLE_7 =
        "Portrait of teenage medusa, bald, naughty smile, wearing an embroidered orange tunic, wearing headdress made of black snakes, intricate, elegant, copper and emerald jewelry, glowing lights, highly detailed, digital painting, artstation, concept art, smooth, sharp focus, illustration, art by wlop, mucha, artgerm, and greg rutkowski"

    const val PROMPT_EXAMPLE_8 =
        "Large openings frame views of the villa's positioning amidst a fir - tree woodland"

    private val predefinedQueries: Set<String> = setOf(
        PROMPT_EXAMPLE_1,
        PROMPT_EXAMPLE_2,
        PROMPT_EXAMPLE_3,
        PROMPT_EXAMPLE_4,
        PROMPT_EXAMPLE_5,
        PROMPT_EXAMPLE_6,
        PROMPT_EXAMPLE_7,
        PROMPT_EXAMPLE_8
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
package com.example.stablediffuser.utils

object QueryGenerator {

    private val predefinedQueries: Set<String> = setOf(
        "tattoo design, stencil, beautiful portrait of a girl with flowers in her hair, upper body, by artgerm, artgerm, digital art, cat girl",
        "a koala in a astronaut suit, 3d, sci-fi fantasy, intricate, elegant, highly detailed, lifelike, photorealistic, digital painting, artstation, illustration, concept art, sharp focus, art in the style of Shigenori Soejima",
        "Cyberpunk depiction of the city of gdansk during arctic conditions by simon stalenhag"
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
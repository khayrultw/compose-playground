package com.khayrul.playground.domain.model

data class Complex(
    val x: Float,
    val y: Float
) {
    operator fun plus(b: Complex): Complex {
        return Complex(x+b.x, y+b.y)
    }

    operator fun times(b: Complex): Complex {
        return Complex(
            x*b.x - y*b.y,
            x*b.y + y*b.x
        )
    }
}

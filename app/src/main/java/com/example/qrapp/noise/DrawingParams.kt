package com.example.qrapp.noise

data class DrawingParams(
    val REDRAW_TIME: Int = 0,
    var PERCENT_NOISE: Int = 0,
    var NOISE_WIDTH: Float = 0.0F){
    init{
        PERCENT_NOISE = if (PERCENT_NOISE == 0) (13..17).random() else PERCENT_NOISE
        NOISE_WIDTH = if (NOISE_WIDTH == 0.0F) (4..6).random().toFloat() else NOISE_WIDTH
    }
}

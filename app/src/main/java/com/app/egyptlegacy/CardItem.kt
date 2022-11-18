package com.app.egyptlegacy

import androidx.annotation.DrawableRes
import java.util.*
import kotlin.random.Random

data class CardItem(

    @DrawableRes val resId: Int,
    val flipState: FlipState,
    val id: String = UUID.randomUUID().toString()
)

enum class FlipState{
    FRONT, BACK, INVISIBLE
}
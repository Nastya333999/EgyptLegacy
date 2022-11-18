package com.app.egyptlegacy

import android.content.Context
import java.io.File

class F(private val name: String, private val context: Context) {

    fun exists(): Boolean = File(context.filesDir, name).exists()

    fun readD() = context.openFileInput(name).bufferedReader().useLines { it.first() }

    fun write(data: String) {
        context.openFileOutput(name, Context.MODE_PRIVATE).use {
            it.write(data.toByteArray())
        }

    }

    companion object {
        const val PR = "https://"
        const val BU = "egyptlegacy.solutions/"
        const val BAU = BU + "egyptlegacy.php"
    }
}
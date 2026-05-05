package com.example.praktam_2417051022.model

import android.content.Context

object ReviewSource {
    fun getResourceId(context: Context, imageName: String): Int {
        return context.resources.getIdentifier(imageName, "drawable", context.packageName)
    }
}
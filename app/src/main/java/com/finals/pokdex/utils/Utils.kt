package com.finals.pokdex.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import com.google.android.material.snackbar.Snackbar
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


fun Fragment.showSnackbar(str: String) {
    Snackbar.make(requireView(), str, Snackbar.LENGTH_LONG).show()
}

fun calcDominantColor(bitmap: Bitmap, onFinish: (Int) -> Unit) {
    val bmp = bitmap.copy(Bitmap.Config.ARGB_8888, true)


    Palette.from(bmp)
        .generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(colorValue)
            }
    }
}

@Throws(MalformedURLException::class, IOException::class)
fun drawable_from_url(url: String?): Bitmap? {
    val connection: HttpURLConnection = URL(url).openConnection() as HttpURLConnection
    connection.setRequestProperty("User-agent", "Mozilla/4.0")
    connection.connect()
    val input: InputStream = connection.getInputStream()
    return BitmapFactory.decodeStream(input)
}
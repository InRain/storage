package com.learn.storage

import android.app.Activity
import android.content.Context
import android.widget.Toast
import java.io.*

class InternalStorageManager(private val activity: Activity) {


    fun save(contentToSave: String) {
        val fileOutputStream: FileOutputStream

        try {
            fileOutputStream = activity.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)
            fileOutputStream.write(contentToSave.toByteArray())
            Toast.makeText(activity, "Content saved", Toast.LENGTH_LONG).show()
        } catch (fileNotFound: FileNotFoundException) {
            Toast.makeText(activity, "file not found", Toast.LENGTH_LONG).show()
        } catch (ioException: IOException) {
            Toast.makeText(activity, "IO exception", Toast.LENGTH_LONG).show()
        }
    }

    fun load(): String? {
        val fileInputStream: FileInputStream
        try {
            fileInputStream = activity.openFileInput(FILE_NAME)
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)

            val stringBuilder = StringBuilder()

            for (line in bufferedReader.lines()) {
                if (line != null) {
                    stringBuilder.append(line)
                }
            }
            return stringBuilder.toString()
        } catch (e: Exception) {
            Toast.makeText(activity, "Something goes wrong while reading file", Toast.LENGTH_LONG)
                .show()
            return null
        }
    }

    companion object {
        private const val FILE_NAME = "sample.txt"
    }
}
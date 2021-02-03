package com.learn.storage

import android.app.Activity
import android.os.Environment
import android.widget.Toast
import java.io.*

class ExternalStorageManager(private val activity: Activity) {

    private var externalStorageAvailable = false
    private var externalStorageWritable = false

    private fun checkExternalStorage() {
        when (Environment.getExternalStorageState()) {
            Environment.MEDIA_MOUNTED -> {
                externalStorageAvailable = true
                externalStorageWritable = true
            }
            Environment.MEDIA_MOUNTED_READ_ONLY -> {
                externalStorageAvailable = true
                externalStorageWritable = false
            }
            else -> {
                externalStorageAvailable = false
                externalStorageWritable = false
            }
        }
    }

    fun writeExternal(content: String) {
        checkExternalStorage()
        if (externalStorageWritable && externalStorageAvailable) {
            val root = activity.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
            val directory = File(root?.absolutePath + LOCAL_DIR)
            directory.mkdirs()

            val contentFile = File(directory, FILE_NAME)

            try {
                val fileOutputStream = FileOutputStream(contentFile)
                val printWriter = PrintWriter(fileOutputStream)
                printWriter.println(content)
                printWriter.flush()
                printWriter.close()
                fileOutputStream.close()
            } catch (fileNotFoundException: FileNotFoundException) {
                Toast.makeText(activity, "File not found", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(activity, "Unable to write on external storage", Toast.LENGTH_LONG)
                .show()
        }
    }

    fun readExternal(): String? {
        checkExternalStorage()
        if (externalStorageAvailable) {
            val root = activity.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
            val directory = File(root?.absolutePath + LOCAL_DIR)

            val stringBuilder = StringBuilder()

            try {
                val contentFile = File(directory, FILE_NAME)
                val bufferedReader = BufferedReader(FileReader(contentFile))

                for (line in bufferedReader.lines()) {
                    stringBuilder.append(line)
                }
                bufferedReader.close()
                return stringBuilder.toString()
            } catch (fileNotFoundException: FileNotFoundException) {
                Toast.makeText(activity, "No file found to read", Toast.LENGTH_LONG).show()
                return null
            } catch (ioException: IOException) {
                Toast.makeText(activity, "Io exception while read from external", Toast.LENGTH_LONG)
                    .show()
                return null
            }
        } else {
            Toast.makeText(activity, "Unable to read from external storage", Toast.LENGTH_LONG)
                .show()
            return null
        }
    }


    companion object {
        private const val FILE_NAME = "external.txt"
        private const val LOCAL_DIR = "testapp"
    }
}
package com.bangkit.h_airup.utils

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import android.content.ContentResolver
import android.os.Build
import android.provider.DocumentsContract
import android.provider.OpenableColumns
import androidx.documentfile.provider.DocumentFile

fun uriToFile(context: Context, uri: Uri): File? {
    val contentResolver: ContentResolver = context.contentResolver

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, uri)) {
        val documentFile = DocumentFile.fromSingleUri(context, uri)
        val displayName = documentFile?.name ?: "temp"
        val outputFile = File(context.cacheDir, displayName)

        try {
            contentResolver.openInputStream(uri)?.use { inputStream ->
                FileOutputStream(outputFile).use { outputStream ->
                    val buffer = ByteArray(4 * 1024)
                    var bytesRead: Int
                    while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                        outputStream.write(buffer, 0, bytesRead)
                    }
                    outputStream.flush()
                }
            }
            return outputFile
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    } else {
        val scheme = uri.scheme
        if (ContentResolver.SCHEME_CONTENT == scheme) {
            val cursor = contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                it.moveToFirst()
                val displayName = it.getString(nameIndex)
                val outputFile = File(context.cacheDir, displayName)

                try {
                    contentResolver.openInputStream(uri)?.use { inputStream ->
                        FileOutputStream(outputFile).use { outputStream ->
                            val buffer = ByteArray(4 * 1024)
                            var bytesRead: Int
                            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                                outputStream.write(buffer, 0, bytesRead)
                            }
                            outputStream.flush()
                        }
                    }
                    return outputFile
                } catch (e: Exception) {
                    e.printStackTrace()
                    return null
                }
            }
        }
    }
    return null
}
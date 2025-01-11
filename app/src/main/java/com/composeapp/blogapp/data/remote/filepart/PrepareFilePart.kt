package com.composeapp.blogapp.data.remote.filepart

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

fun prepareFilePart(file: File, fileName: String): MultipartBody.Part {
    val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(
        fileName,
        file.name,
        requestFile
    )
}
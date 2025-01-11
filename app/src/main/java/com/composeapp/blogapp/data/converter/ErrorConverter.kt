package com.composeapp.blogapp.data.converter

import com.composeapp.blogapp.data.models.ErrorModel
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Converter

class ErrorConverter : Converter<ResponseBody, ErrorModel> {
    override fun convert(value: ResponseBody): ErrorModel {
        val gson = Gson()
        val error = gson.fromJson(value.string(), ErrorModel::class.java)
        return error
    }
}
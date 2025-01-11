package com.composeapp.blogapp.data.converter

import com.composeapp.blogapp.data.models.ErrorModel
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class ErrorConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ) = if (type == ErrorModel::class.java) ErrorConverter() else null
}
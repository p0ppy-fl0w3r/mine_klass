package com.atme.mineklass.utils

import com.atme.mineklass.classData.ClassData
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object JsonUtils {

    fun getClassFromJson( jsonString: String): List<ClassData>? {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        val listType = Types.newParameterizedType(List::class.java, ClassData::class.java)
        val adapter: JsonAdapter<List<ClassData>> = moshi.adapter(listType)

        return adapter.fromJson(jsonString)
    }

}
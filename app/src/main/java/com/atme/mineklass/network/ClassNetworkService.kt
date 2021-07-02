package com.atme.mineklass.network

import com.atme.mineklass.classData.ClassData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


private const val BASE_URL = "https://jsonkeeper.com/b/"
private const val BIN_ID = "DJIC"


private val moshi: Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ClassNetworkService {
    @GET(BIN_ID)
    suspend fun getClassData():List<ClassData>
}

object ClassNetworkApi{
    val classNetworkApi: ClassNetworkService by lazy{
        retrofit.create(ClassNetworkService::class.java)
    }
}


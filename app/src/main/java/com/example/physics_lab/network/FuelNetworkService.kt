package com.example.physics_lab.network

import com.example.physics_lab.data.GeneralNetWorkResponse
import com.example.physics_lab.utils.removeQuotes
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.coroutines.awaitString
import com.github.kittinunf.fuel.coroutines.awaitStringResult
import com.google.gson.Gson
import org.json.JSONObject
import timber.log.Timber

abstract class FuelNetworkService {
    private val BASE_API_URL = "https://hse-class.ru/api/"
    private val gson = Gson()

    init {
        FuelManager.instance.basePath = BASE_API_URL
    }


    protected suspend fun postWithJsonStringResult(
        path: String,
        parameters: Any
    ): String? {
        try {
            return Fuel.post(path)
                .jsonBody(JSONObject(gson.toJson(parameters)).toString(), Charsets.UTF_8)
                .awaitString()

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    protected suspend fun <T> postWithJson(
        path: String,
        clazz: Class<T>,
        parameters: Any
    ): T? {
        Timber.i("parameters are ${JSONObject(gson.toJson(parameters))}")
        try {
            return Fuel.post(path)
                .jsonBody(JSONObject(gson.toJson(parameters)).toString(), Charsets.UTF_8)
                .awaitStringResult()
                .fold({ jsonResponse ->
                    return@fold gson.fromJson(jsonResponse, clazz)
                }) { error ->
                    return@fold gson.fromJson(error.errorData.toString(Charsets.UTF_8), clazz)
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    protected suspend fun <T> postWithParamsAndToken(
        path: String,
        clazz: Class<T>,
        parameters: Any,
        token: String
    ): T? {
        try {
            return Fuel.post(path)
                .header(Headers.AUTHORIZATION, "Bearer ${token.removeQuotes()}")
                .jsonBody(JSONObject(gson.toJson(parameters)).toString(), Charsets.UTF_8)
                .awaitStringResult()
                .fold({ jsonResponse ->
                    Timber.i("response $jsonResponse")
                    return@fold gson.fromJson(jsonResponse, clazz)
                }) { error ->
                    Timber.i("error $error")
                    return@fold gson.fromJson(error.errorData.toString(Charsets.UTF_8), clazz)
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
    protected suspend fun <T> postWithToken(
        path: String,
        clazz: Class<T>,
        token: String
    ): T? {
        try {
            return Fuel.post(path)
                .header(Headers.AUTHORIZATION, "Bearer ${token.removeQuotes()}")
                .awaitStringResult()
                .fold({ jsonResponse ->
                    return@fold gson.fromJson(jsonResponse, clazz)
                }) { error ->
                    return@fold gson.fromJson(error.errorData.toString(Charsets.UTF_8), clazz)
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
    protected suspend fun <T> getWithToken(
        path: String,
        clazz: Class<T>,
        token: String
    ): T? {
        try {
            return Fuel.get(path)
                .header(Headers.AUTHORIZATION, "Bearer ${token.removeQuotes()}")
                .awaitStringResult()
                .fold({ jsonResponse ->
                    Timber.i("response $jsonResponse")
                    return@fold gson.fromJson(jsonResponse, clazz)
                }) { error ->
                    Timber.i("error $error")
                    return@fold gson.fromJson(error.errorData.toString(Charsets.UTF_8), clazz)
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        Timber.i("this shit called")
        return null
    }


    protected suspend fun <T> putWithToken(
        path: String,
        clazz: Class<T>,
        token: String
    ): T? {
        try {
            return Fuel.put(path)
                .header(Headers.AUTHORIZATION, "Bearer ${token.removeQuotes()}")
                .awaitStringResult()
                .fold({ jsonResponse ->
                    Timber.i("response $jsonResponse")
                    return@fold gson.fromJson(jsonResponse, clazz)
                }) { error ->
                    Timber.i("error $error")
                    return@fold gson.fromJson(error.errorData.toString(Charsets.UTF_8), clazz)
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        Timber.i("this shit called")
        return null
    }

    protected suspend fun <T> getWithParamsAndToken(
        path: String,
        clazz: Class<T>,
        parameters: Any,
        token: String
    ): T? {
        try {
            return Fuel.delete(path)
                .header(Headers.AUTHORIZATION, "Bearer ${token.removeQuotes()}")
                .jsonBody(JSONObject(gson.toJson(parameters)).toString(), Charsets.UTF_8)
                .awaitStringResult()
                .fold({ jsonResponse ->
                    Timber.i("response $jsonResponse")
                    return@fold gson.fromJson(jsonResponse, clazz)
                }) { error ->
                    Timber.i("error $error")
                    return@fold gson.fromJson(error.errorData.toString(Charsets.UTF_8), clazz)
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        Timber.i("this shit called")
        return null
    }

    protected suspend fun <T> deleteWithToken(
        path: String,
        clazz: Class<T>,
        token: String
    ): T? {
        try {
            return Fuel.delete(path)
                .header(Headers.AUTHORIZATION, "Bearer ${token.removeQuotes()}")
                .awaitStringResult()
                .fold({ jsonResponse ->
                    Timber.i("response $jsonResponse")
                    return@fold gson.fromJson(jsonResponse, clazz)
                }) { error ->
                    Timber.i("error $error")
                    return@fold gson.fromJson(error.errorData.toString(Charsets.UTF_8), clazz)
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        Timber.i("this shit called")
        return null
    }

}
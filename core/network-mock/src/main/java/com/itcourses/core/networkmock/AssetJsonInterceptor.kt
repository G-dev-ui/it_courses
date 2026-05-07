package com.itcourses.core.networkmock

import android.content.Context
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

/**
 * Very small mock layer: maps request paths to JSON assets.
 * Feature data modules should depend on this only in debug or mock flavor,
 * but for the educational project we wire it directly.
 */
class AssetJsonInterceptor(
    private val context: Context,
    private val routes: Map<String, String>,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val path = request.url.encodedPath

        val assetFile = routes[path]
        if (assetFile == null) return chain.proceed(request)

        val json = context.assets.open(assetFile).bufferedReader().use { it.readText() }
        val body = json.toResponseBody("application/json; charset=utf-8".toMediaType())

        return Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .body(body)
            .build()
    }
}


package id.allana.githubapp_bfaa.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {

    companion object {
        private const val API_KEY = "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"
        private const val BASE_URL = "https://api.github.com"
        fun getApiService(): ApiService {
            val client = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor {
                    val req = it.request()
                    val requestHeaders = req.newBuilder()
                        .addHeader("Authorization", API_KEY)
                        .build()
                    it.proceed(requestHeaders)
                }
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}
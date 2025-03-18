package health.swastham

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object retrofitClient {
    private const val BASE_URL = "https://ap-south-1.aws.data.mongodb-api.com/app/data-vfphn/endpoint/data/v1/"

    val instance:MongoDBApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MongoDBApi::class.java)
    }
}
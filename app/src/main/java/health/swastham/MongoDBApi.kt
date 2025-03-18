package health.swastham

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface MongoDBApi {
    @Headers(
        "Content-Type: application/json",
        "Access-Control-Request-Headers: *",
        "api-key: RsMp9RB3dNnEVd7ELkJzJdLmcFvNNdDaLo4GLkFITiAYVjxl08qygTTS87oPQaEd"
    )
    @POST("action/find")
    fun fetchHealthData(@Body requestBody: FetchHealthDataRequest):retrofit2.Call<HealthDataResponse>



}
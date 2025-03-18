package health.swastham

import com.google.gson.annotations.SerializedName




data class FetchHealthDataRequest(
    val collection: String,
    val database: String,
    val dataSource: String,
)

data class HealthDataResponse(
   @SerializedName("documents") val document:List<HealthData>?
)

data class HealthData(
    @SerializedName("_id") val id:String,
    @SerializedName("heartRate") val heartRate:Int,
    @SerializedName("spo2") val spo2:Int,
    @SerializedName("bodyTemp") val bodyTemp:Double,
    @SerializedName("timestamp") val timestamp:String,
)

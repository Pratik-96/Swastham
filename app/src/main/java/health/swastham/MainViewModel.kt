package health.swastham


import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainViewModel : ViewModel() {

    //    private val APP_ID = "data-xajjh"

    init {
        fetchData()
    }

    data class dataState(
        var data:List<HealthData>? =  null,
        var error: String? = null,
        var loading: Boolean = true
    )

    private val _healthData = mutableStateOf(dataState())
    val healthData: State<dataState> = _healthData

    fun fetchData(){
        val requestBody = FetchHealthDataRequest(
            collection = "sensorsdata",
            database = "healthdata",
            dataSource = "swastham-data",

        )

        viewModelScope.launch {
            retrofitClient.instance.fetchHealthData(requestBody).enqueue(object :Callback<HealthDataResponse>{
                override fun onResponse(
                    call: Call<HealthDataResponse>,
                    response: Response<HealthDataResponse>
                ) {
                    if (response.isSuccessful){
                        val data = response.body()?.document
                        _healthData.value = _healthData.value.copy(
                            error = null,
                            data = data,
                            loading = false
                        )
                        Log.d("API_SUCCESS", "Sensor Data: $data")
                    }
                }

                override fun onFailure(call: Call<HealthDataResponse>, t: Throwable) {
                    Log.e("API_FAILURE", "Error fetching data: ${t.message}")
                    _healthData.value = _healthData.value.copy(
                        error = t.message,
                        data = null,
                        loading = false
                    )
                }

            })
        }
    }

}
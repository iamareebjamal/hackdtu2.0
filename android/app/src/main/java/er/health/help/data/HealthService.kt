package er.health.help.data

import er.health.help.data.model.DiseasePrediction
import er.health.help.data.model.FoodPrediction
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.POST
import retrofit2.http.Part

interface HealthService {

    @POST("/disease")
    fun predictDisease(@Part file: MultipartBody.Part) : Observable<DiseasePrediction>

    @POST("/food")
    fun predictFood(@Part file: MultipartBody.Part) : Observable<FoodPrediction>

}
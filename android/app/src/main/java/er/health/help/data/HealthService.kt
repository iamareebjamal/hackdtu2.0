package er.health.help.data

import er.health.help.data.model.DiseasePrediction
import er.health.help.data.model.FoodPrediction
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Part

interface HealthService {

    @POST("/disease")
    fun predictDisease(@Body file: RequestBody) : Observable<DiseasePrediction>

    @POST("/food")
    fun predictFood(@Body file: RequestBody) : Observable<FoodPrediction>

}
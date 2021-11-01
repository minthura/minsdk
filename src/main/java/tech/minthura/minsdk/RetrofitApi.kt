package tech.minthura.minsdk

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import tech.minthura.minsdk.models.Mock

interface RetrofitApi {
    @GET("/403")
    fun getMock() : Observable<Mock>
}
package tech.minthura.minsdk.services

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.disposables.Disposable
import tech.minthura.minsdk.RetrofitApi
import tech.minthura.minsdk.models.Error
import tech.minthura.minsdk.models.Mock

class MockService(private val retrofitApi: RetrofitApi) : BaseService() {

    fun getMock(onSuccess: (Mock) -> Unit, onError : (error : Error) -> Unit): @NonNull Disposable {
        return handleResponse(retrofitApi.getMock(), onSuccess, onError)
    }
}
package tech.minthura.minsdk.services

import tech.minthura.minsdk.RetrofitApi
import tech.minthura.minsdk.models.Error
import tech.minthura.minsdk.models.Mock

class MockService(private val retrofitApi: RetrofitApi) : BaseService() {

    fun getMock(onSuccess: (Mock) -> Unit, onError : (error : Error) -> Unit) {
        handleResponse(retrofitApi.getMock(), onSuccess, onError)
    }
}
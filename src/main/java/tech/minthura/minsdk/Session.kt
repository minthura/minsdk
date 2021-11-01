package tech.minthura.minsdk

import tech.minthura.minsdk.models.Error
import tech.minthura.minsdk.models.Mock

class Session(private val minSDK: MinSDK) {
    companion object  {
        lateinit var instance : Session
        fun init(baseUrl: String) {
            instance = Session(MinSDK(baseUrl))
        }
    }

    fun getMock(onSuccess: (Mock) -> Unit, onError : (error : Error) -> Unit){
        minSDK.getMock(onSuccess, onError)
    }
}
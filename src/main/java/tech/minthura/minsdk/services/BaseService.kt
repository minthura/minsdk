package tech.minthura.minsdk.services

import android.util.Log
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import tech.minthura.minsdk.models.Error
import tech.minthura.minsdk.models.ErrorResponse
import tech.minthura.minsdk.models.Errors
import javax.net.ssl.HttpsURLConnection

const val TAG = "MinSDK"

open class BaseService {

    open fun handleApiError(error: Throwable, onError : (error : Error) -> Unit) {
        if (error is HttpException) {
            Log.e(TAG,error.message())
            when (error.code()) {
                HttpsURLConnection.HTTP_UNAUTHORIZED -> onError(Error(Errors.UNAUTHORIZED, getErrorResponse(error)))
                HttpsURLConnection.HTTP_FORBIDDEN -> onError(Error(Errors.FORBIDDEN, getErrorResponse(error)))
                HttpsURLConnection.HTTP_INTERNAL_ERROR -> onError(Error(Errors.INTERNAL_SERVER_ERROR, getErrorResponse(error)))
                HttpsURLConnection.HTTP_BAD_REQUEST -> onError(Error(Errors.BAD_REQUEST, getErrorResponse(error)))
                else -> onError(Error(Errors.UNKNOWN, getErrorResponse(error)))
            }
        }
    }

    open fun <T : Any>handleResponse(observable : Observable<T>, onSuccess : (t : T) -> Unit, onError : (error : Error) -> Unit){
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe ({
            onSuccess(it)
        }, {
            handleApiError(it, onError)
        })
    }

    private fun buildErrorResponse(json : String) : ErrorResponse? {
        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val jsonAdapter: JsonAdapter<ErrorResponse> = moshi.adapter(
            ErrorResponse::class.java
        )
        return jsonAdapter.fromJson(json)
    }

    private fun getErrorResponse(error : HttpException) : ErrorResponse? {
        error.response()?.let { it ->
            it.errorBody()?.let {
                return buildErrorResponse(it.string())
            }
        }
        return null
    }

}
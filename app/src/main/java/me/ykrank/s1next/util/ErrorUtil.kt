package me.ykrank.s1next.util

import android.content.Context
import android.util.Log
import com.fasterxml.jackson.core.JsonProcessingException
import me.ykrank.s1next.BuildConfig
import me.ykrank.s1next.R
import me.ykrank.s1next.data.api.ApiException
import retrofit2.HttpException
import java.io.IOException

object ErrorUtil {

    private val TAG_LOG = ErrorUtil::class.java.simpleName

    fun parse(context: Context, throwable: Throwable): String {
        var msg = parseNetError(context, throwable)
        var cause: Throwable? = throwable.cause
        while (msg == null && cause != null) {
            msg = parseNetError(context, cause)
            cause = cause.cause
        }
        if (msg == null) {
            L.report(throwable)
            return context.getString(R.string.message_unknown_error)
        }
        return msg
    }

    private fun parseNetError(context: Context, throwable: Throwable): String? {
        var msg: String? = null
        when (throwable) {
            is ApiException -> msg = throwable.getLocalizedMessage()
            is JsonProcessingException -> {
                msg = context.getString(R.string.message_server_data_error)
                L.report(throwable)
            }
            is IOException -> {
                msg = context.getString(R.string.message_network_error)
                L.e(throwable)
            }
            is HttpException -> {
                msg = throwable.getLocalizedMessage()
                if (msg.isNullOrEmpty()) {
                    msg = context.getString(R.string.message_server_connect_error)
                }
            }
        }
        return msg
    }

    fun throwNewErrorIfDebug(throwable: RuntimeException) {
        if (BuildConfig.DEBUG) {
            throw throwable
        } else {
            L.report(throwable, Log.WARN)
        }
    }
}

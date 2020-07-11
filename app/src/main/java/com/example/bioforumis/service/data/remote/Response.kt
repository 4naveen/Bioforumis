package com.example.bioforumis.service.data.remote

data class Response<T>(var status: Status, var data: T?, val message: String?) {

    enum class Status {
        SUCCESS, ERROR, LOADING, UNKNOWN
    }
    companion object {
        fun <T> success(data: T): Response<T> =
            Response(
                status = Status.SUCCESS,
                data = data,
                message = null
            )

        fun <T> error(data: T?, message: String): Response<T> =
            Response(
                status = Status.ERROR,
                data = data,
                message = message
            )

        fun <T> loading(data: T?): Response<T> =
            Response(
                status = Status.LOADING,
                data = data,
                message = null
            )
    }
}
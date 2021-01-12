package com.dnedev.photoeditor.utils

import com.android.volley.Request
import com.android.volley.RequestQueue

class RequestQueueUtil(private val requestQueue: RequestQueue) {

    fun start() {
        requestQueue.start()
    }

    fun <T> addRequest(request: Request<T>) {
        requestQueue.add(request)
    }

    fun stop() {
        requestQueue.stop()
    }
}
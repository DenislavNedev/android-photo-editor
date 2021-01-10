package com.dnedev.photoeditor.di.modules

import com.android.volley.Cache
import com.android.volley.Network
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.dnedev.photoeditor.utils.CACHE_DIRECTORY
import com.dnedev.photoeditor.utils.RequestQueueUtil
import dagger.Module
import dagger.Provides
import java.io.File
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideCache(): Cache = DiskBasedCache(File(CACHE_DIRECTORY))

    @Singleton
    @Provides
    fun provideNetwork(): Network = BasicNetwork(HurlStack())

    @Singleton
    @Provides
    fun provideRequestQueue(
        cache: Cache,
        network: Network
    ) = RequestQueue(cache, network)

    @Singleton
    @Provides
    fun provideRequestQueueUtil(requestQueue: RequestQueue) = RequestQueueUtil(requestQueue)
}
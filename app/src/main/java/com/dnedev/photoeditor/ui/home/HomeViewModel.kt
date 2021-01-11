package com.dnedev.photoeditor.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.volley.VolleyError
import com.dnedev.photoeditor.BuildConfig
import com.dnedev.photoeditor.data.PexelsDataResponse
import com.dnedev.photoeditor.data.convertToPhotoItemUiModel
import com.dnedev.photoeditor.repositories.photos.PhotosRepository
import com.dnedev.photoeditor.repositories.photos.PhotosResponseCallback
import com.dnedev.photoeditor.utils.*
import kotlinx.coroutines.launch
import java.net.URLEncoder
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    application: Application,
    private val photosRepository: PhotosRepository
) : AndroidViewModel(application),
    HomePresenter,
    PhotosResponseCallback {

    private val _uiModelLiveData = MutableLiveData<HomeUiModel>().apply {
        value = HomeUiModel(emptyList())
    }
    val uiModelLiveData: LiveData<HomeUiModel>
        get() = _uiModelLiveData

    private var nextPageUrl: String? = null

    override fun handlePhotosSuccessfulResponse(dataResponse: PexelsDataResponse) {
        with(dataResponse) {
            if (totalResults == ZERO) {
                //TODO add no result string
                return
            }

            nextPageUrl = nextPage
            photos.map { it.convertToPhotoItemUiModel() }.let {
                _uiModelLiveData.value = _uiModelLiveData.value?.copy(
                    photos = _uiModelLiveData.value?.photos?.plus(it) ?: emptyList()
                )?.apply {
                    areTherePhotos = photos.isNotEmpty()
                    areLoadingMorePhotos = false
                }
            }
        }
    }

    override fun handlePhotosErrorResponse(errorResponse: VolleyError) {
        //TODO add error handling
        _uiModelLiveData.value?.areLoadingMorePhotos = true
    }

    fun loadMorePhotos() {
        nextPageUrl?.let { nextPage ->
            if (_uiModelLiveData.value?.areLoadingMorePhotos == false) {
                _uiModelLiveData.value?.areLoadingMorePhotos = true
                viewModelScope.launch {
                    try {
                        loadPhotos(nextPage)
                    } catch (exception: Exception) {
                        //TODO add error handling
                    }
                }
            }
        }
    }

    override fun search() {
        if (!isInternetAvailable()) {
            //TODO add error handling
            return
        }

        _uiModelLiveData.value?.let {
            _uiModelLiveData.value = _uiModelLiveData.value?.copy(photos = emptyList())
            viewModelScope.launch {
                try {
                    loadPhotos(getApiUrl(it.searchQuery))
                } catch (exception: Exception) {
                    //TODO add error handling
                }
            }
        }
    }

    private suspend fun loadPhotos(url: String) {
        photosRepository.getPhotos(
            url,
            mutableMapOf(AUTHORIZATION_HEADER to BuildConfig.API_AUTHORIZATION_KEY),
            this@HomeViewModel
        )
    }

    private fun getApiUrl(searchQuery: String) =
        PEXELS_SERACH_API_URL + addQueryParameters(API_PARAMETER_QUERY, searchQuery)

    private fun addQueryParameters(key: String, value: String) =
        URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(value, "UTF-8")

    private fun isInternetAvailable() =
        getApplication<Application>().applicationContext.isNetworkAvailable()
}
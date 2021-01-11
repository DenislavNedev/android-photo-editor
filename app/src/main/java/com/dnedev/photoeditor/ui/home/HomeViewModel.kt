package com.dnedev.photoeditor.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.volley.VolleyError
import com.dnedev.photoeditor.BuildConfig
import com.dnedev.photoeditor.R
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
                _uiModelLiveData.value?.infoText = R.string.no_results
                _uiModelLiveData.value?.areLoadingMorePhotos = false
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
        _uiModelLiveData.value?.infoText = R.string.error_occurred
        _uiModelLiveData.value?.areLoadingMorePhotos = false
    }

    fun loadMorePhotos() {
        nextPageUrl?.let { nextPage ->
            if (_uiModelLiveData.value?.areLoadingMorePhotos == false) {

                if (!isInternetAvailable()) {
                    _uiModelLiveData.value?.infoText = R.string.no_internet
                    _uiModelLiveData.value?.areLoadingMorePhotos = false
                    return
                }

                _uiModelLiveData.value?.areLoadingMorePhotos = true
                viewModelScope.launch {
                    try {
                        loadPhotos(nextPage)
                    } catch (exception: Exception) {
                        _uiModelLiveData.value?.infoText = R.string.error_occurred
                    }
                }
            }
        }
    }

    override fun search() {
        if (!isInternetAvailable()) {
            _uiModelLiveData.value?.infoText = R.string.no_internet
            _uiModelLiveData.value?.areLoadingMorePhotos = false
            return
        }

        if (isSearchFieldEmpty()) {
            return
        } else {
            _uiModelLiveData.value?.searchQueryError = null
        }

        _uiModelLiveData.value?.let {
            _uiModelLiveData.value = _uiModelLiveData.value?.copy(photos = emptyList())
            _uiModelLiveData.value?.areLoadingMorePhotos = true

            viewModelScope.launch {
                try {
                    loadPhotos(getApiUrl(it.searchQuery))
                } catch (exception: Exception) {
                    _uiModelLiveData.value?.infoText = R.string.error_occurred
                    _uiModelLiveData.value?.areLoadingMorePhotos = false
                }
            }
        }
    }

    private fun isSearchFieldEmpty(): Boolean =
        _uiModelLiveData.value?.searchQuery.equals(EMPTY_EDIT_TEXT).let {
            _uiModelLiveData.value?.searchQueryError =
                if (it) getApplication<Application>().getString(R.string.field_must_not_be_empty)
                else null

            it
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
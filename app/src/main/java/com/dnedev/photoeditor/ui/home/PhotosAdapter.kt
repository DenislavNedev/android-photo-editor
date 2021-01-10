package com.dnedev.photoeditor.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.dnedev.photoeditor.R
import com.dnedev.photoeditor.databinding.PhotoItemBinding
import com.dnedev.photoeditor.utils.DataBoundListAdapter

class PhotosAdapter() : DataBoundListAdapter<PhotoItemUiModel, PhotoItemBinding>(PhotoDiffUtil()) {

    override fun createBinding(
        parent: ViewGroup,
        viewType: Int
    ): PhotoItemBinding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.context),
        R.layout.photo_item,
        parent,
        false
    )

    override fun bind(binding: PhotoItemBinding, item: PhotoItemUiModel) {
        binding.uiModel = item
    }

}

class PhotoDiffUtil : DiffUtil.ItemCallback<PhotoItemUiModel>() {
    override fun areItemsTheSame(oldItem: PhotoItemUiModel, newItem: PhotoItemUiModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: PhotoItemUiModel, newItem: PhotoItemUiModel): Boolean =
        oldItem == newItem

}
package com.dnedev.photoeditor.ui.edit.colors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.dnedev.photoeditor.R
import com.dnedev.photoeditor.databinding.ColorItemBinding
import com.dnedev.photoeditor.utils.DataBoundListAdapter

class ColorsListAdapter(private val callback: ChooseColorCallback) :
    DataBoundListAdapter<ColorOverlay, ColorItemBinding>(ColorDiffUtil()) {

    override fun createBinding(
        parent: ViewGroup,
        viewType: Int
    ): ColorItemBinding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.context),
        R.layout.color_item,
        parent,
        false
    )

    override fun bind(binding: ColorItemBinding, item: ColorOverlay) {
        binding.colorOverlay = item
        binding.callback = callback
    }
}

class ColorDiffUtil : DiffUtil.ItemCallback<ColorOverlay>() {
    override fun areItemsTheSame(oldItem: ColorOverlay, newItem: ColorOverlay): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: ColorOverlay, newItem: ColorOverlay): Boolean =
        oldItem.equals(newItem)
}
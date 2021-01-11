package com.dnedev.photoeditor.ui.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.dnedev.photoeditor.R
import com.dnedev.photoeditor.databinding.EditFragmentBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class EditFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: EditViewModel by activityViewModels { viewModelFactory }

    private lateinit var binding: EditFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.edit_fragment,
            container,
            false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeUiModel()
    }

    private fun observeUiModel() {
        binding.presenter = viewModel
        viewModel.uiModelLiveData.observe(viewLifecycleOwner, {
            binding.uiModel = it
        })
    }
}
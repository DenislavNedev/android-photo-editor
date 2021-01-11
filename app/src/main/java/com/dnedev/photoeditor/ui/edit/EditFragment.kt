package com.dnedev.photoeditor.ui.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dnedev.photoeditor.R
import com.dnedev.photoeditor.databinding.EditFragmentBinding
import com.dnedev.photoeditor.ui.edit.colors.ColorsListAdapter
import com.dnedev.photoeditor.utils.EMPTY_EDIT_TEXT
import com.dnedev.photoeditor.utils.PHOTO_URL_BUNDLE_ID
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.edit_fragment.*
import javax.inject.Inject

class EditFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: EditViewModel by activityViewModels { viewModelFactory }

    private lateinit var binding: EditFragmentBinding
    private val colorsAdapter by lazy { ColorsListAdapter(viewModel) }

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
        viewModel.initViewModel(arguments?.getString(PHOTO_URL_BUNDLE_ID) ?: EMPTY_EDIT_TEXT)
        observeUiModel()
        initRecyclerView()
    }

    private fun observeUiModel() {
        binding.presenter = viewModel
        binding.sliderCallback = viewModel
        viewModel.uiModelLiveData.observe(viewLifecycleOwner, {
            binding.uiModel = it
            colorsAdapter.submitList(it.colors)
        })
    }

    private fun initRecyclerView() {
        with(colors_recycler_view) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = colorsAdapter
        }
    }
}
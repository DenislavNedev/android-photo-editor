package com.dnedev.photoeditor.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dnedev.photoeditor.R
import com.dnedev.photoeditor.databinding.HomeFragmentBinding
import com.dnedev.photoeditor.utils.ZERO
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.home_fragment.*
import javax.inject.Inject

class HomeFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: HomeViewModel by activityViewModels { viewModelFactory }

    private lateinit var binding: HomeFragmentBinding
    private val photosAdapter by lazy { PhotosAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.home_fragment,
            container,
            false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeUiModel()
        initRecyclerView()
    }

    private fun observeUiModel() {
        binding.presenter = viewModel
        viewModel.uiModelLiveData.observe(viewLifecycleOwner, {
            binding.uiModel = it
            photosAdapter.submitList(it.photos)
        })
    }

    private fun initRecyclerView() {
        with(photos_recycler_view) {
            layoutManager = GridLayoutManager(context, SPAN_COUNT)
            adapter?.setHasStableIds(true)
            adapter = photosAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    with(layoutManager as GridLayoutManager) {
                        if (childCount + findFirstVisibleItemPosition() >= itemCount
                            && findFirstVisibleItemPosition() >= ZERO
                        ) {
                            viewModel.loadMorePhotos()
                        }
                    }
                }
            })
        }
    }

    companion object {
        private const val SPAN_COUNT = 3
    }
}
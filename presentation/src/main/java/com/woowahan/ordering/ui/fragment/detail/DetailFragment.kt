package com.woowahan.ordering.ui.fragment.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import com.woowahan.ordering.R
import com.woowahan.ordering.databinding.FragmentDetailBinding
import com.woowahan.ordering.ui.adapter.detail.DetailThumbImagesAdapter
import com.woowahan.ordering.ui.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModels()
    private var binding: FragmentDetailBinding? = null

    private lateinit var hash: String
    private lateinit var title: String

    private val detailImagesAdapter by lazy { DetailThumbImagesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFlow()
        initArguments()
        initRecyclerView()

        viewModel.getFoodDetail(hash)
    }

    private fun initFlow() {
        lifecycleScope.launchWhenStarted {
            viewModel.foodDetail.collect {
                detailImagesAdapter.submitList(it.thumbImages)
            }
        }
    }

    private fun initArguments() = with(requireArguments()) {
        hash = getString("hash", "")
        title = getString("title", "")
    }

    private fun initRecyclerView() = with(binding!!) {
        val adapter = ConcatAdapter(detailImagesAdapter)
        rvDetail.adapter = adapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
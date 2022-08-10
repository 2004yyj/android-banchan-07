package com.woowahan.ordering.ui.fragment.home.other

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.woowahan.ordering.databinding.FragmentOtherDishBinding
import com.woowahan.ordering.domain.model.Menu
import com.woowahan.ordering.ui.fragment.home.other.kind.OtherKind

class OtherDishFragment(
    private val onDetailClick: (hash: String) -> Unit
) : Fragment() {

    private lateinit var binding: FragmentOtherDishBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOtherDishBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val kind = arguments?.get(OTHER_KIND) as OtherKind?
    }

    companion object {
        private const val OTHER_KIND = "otherKind"

        fun newInstance(onDetailClick: (hash: String) -> Unit, otherKind: OtherKind) =
            OtherDishFragment(onDetailClick).apply {
                arguments = bundleOf(OTHER_KIND to otherKind)
            }
    }
}
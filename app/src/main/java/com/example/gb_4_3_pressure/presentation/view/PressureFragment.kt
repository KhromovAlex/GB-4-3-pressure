package com.example.gb_4_3_pressure.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gb_4_3_pressure.databinding.FragmentPressureListBinding
import com.example.gb_4_3_pressure.domain.entity.PressureInfo
import com.example.gb_4_3_pressure.presentation.adapter.PressureRecyclerViewAdapter
import com.example.gb_4_3_pressure.presentation.viewmodel.PressureViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PressureFragment : Fragment() {

    private var columnCount = 1
    private var _binding: FragmentPressureListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PressureViewModel by viewModel()
    private val adapterPressure = PressureRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPressureListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.list) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = adapterPressure
        }

        binding.addPressure.setOnClickListener {
            viewModel.sendNewPressure()
        }
        viewModel.liveData.observe(viewLifecycleOwner, ::renderData)
        viewModel.watchLessons()
    }

    private fun renderData(list: List<PressureInfo>) {
        adapterPressure.submitList(list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"

        fun newInstance(columnCount: Int) =
            PressureFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}

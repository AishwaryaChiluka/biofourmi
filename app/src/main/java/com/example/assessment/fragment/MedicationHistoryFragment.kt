package com.example.assessment.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assessment.MedicationDetails
import com.example.assessment.MedicationViewModel
import com.example.assessment.adapter.MedicationHistoryAdapter
import com.example.assessment.databinding.FragmentMedicationHistoryBinding
import java.util.ArrayList


class MedicationHistoryFragment : Fragment() {

    private lateinit var medicationViewModel : MedicationViewModel
    private var _binding: FragmentMedicationHistoryBinding? = null
    private var medicationHistoryAdapter = MedicationHistoryAdapter(arrayListOf())
    private val binding get() = _binding!!
    var list : List<MedicationDetails> =   listOf<MedicationDetails>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMedicationHistoryBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewModel()
        setHistoryAdapter()
        observeListofMedication()
    }

    private fun setHistoryAdapter() {
        _binding!!.recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = medicationHistoryAdapter
        }
    }

    private fun setViewModel() {
        medicationViewModel = ViewModelProvider(requireActivity()).get(MedicationViewModel::class.java)
    }

    private fun observeListofMedication() {
        medicationViewModel.details.observe(
            requireActivity(),  {
                    medication -> medication?.let {
                list = medication
                medicationHistoryAdapter.updateHistory(list as ArrayList<MedicationDetails>)
            }
            }
        )
    }
}
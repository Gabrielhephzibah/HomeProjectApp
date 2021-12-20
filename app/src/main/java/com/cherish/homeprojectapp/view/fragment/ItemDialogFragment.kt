package com.cherish.homeprojectapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cherish.homeprojectapp.R
import com.cherish.homeprojectapp.databinding.FragmentItemDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class ItemDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentItemDialogBinding? = null
    private val binding get() = _binding!!
    private val args: ItemDialogFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.id.text = args.id.toString()
        binding.name.text = args.name
        binding.desc.text = args.desc
        binding.nodeId.text = args.nodeId
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
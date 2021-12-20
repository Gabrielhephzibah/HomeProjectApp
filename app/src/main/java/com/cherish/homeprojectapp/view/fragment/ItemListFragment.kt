package com.cherish.homeprojectapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.cherish.homeprojectapp.R
import com.cherish.homeprojectapp.data.model.response.GetOrganizationResponse
import com.cherish.homeprojectapp.data.remote.ResponseManager
import com.cherish.homeprojectapp.databinding.FragmentItemListBinding
import com.cherish.homeprojectapp.utils.navigate
import com.cherish.homeprojectapp.view.adapter.ItemAdapter
import com.cherish.homeprojectapp.view.adapter.ItemListData
import com.cherish.homeprojectapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class ItemListFragment : Fragment() {
    private var _binding: FragmentItemListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private var itemAdapter: ItemAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        getOrganizationList()
    }

    private fun stopShimmer() {
        binding.loading.wrapper.apply {
            binding.loading.shimmerLayout.stopShimmer()
            visibility = View.GONE
        }
    }

    private fun getOrganizationList() {
        viewModel.getOrganization().observe(viewLifecycleOwner) { response ->
            when (response) {
                is ResponseManager.Failure -> {
                    stopShimmer()
                    binding.emptyOrError.wrapper.visibility = View.VISIBLE
                }
                is ResponseManager.Loading -> {
                    when (response.state) {
                        true -> binding.loading.shimmerLayout.startShimmer()
                        false -> stopShimmer()
                    }
                }
                is ResponseManager.Success -> {
                    showData(response.data)
                }
            }
        }
    }

    private fun setUpAdapter() {
        itemAdapter = ItemAdapter { listItem ->
            val action = listItem.id?.let {
                ItemListFragmentDirections.actionItemListFragmentToItemDialogFragment(
                    it, listItem.login, listItem.description, listItem.node_id)
            }
            navigate(action!!)
        }
        binding.recyclerView.adapter = itemAdapter
    }

    private fun showData(response: List<GetOrganizationResponse>) {
        if (response.isEmpty()) {
            binding.emptyOrError.wrapper.visibility = View.VISIBLE
            binding.emptyOrError.message.text = getString(R.string.empty_state)
        } else {
            binding.recyclerView.visibility = View.VISIBLE
            val item = response.map {
                ItemListData(
                    it.login,
                    it.url,
                    it.description,
                    it.avatar_url,
                    it.node_id,
                    it.id
                )
            }
            itemAdapter!!.submitList(item)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
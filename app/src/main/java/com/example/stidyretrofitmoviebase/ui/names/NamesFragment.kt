package com.example.stidyretrofitmoviebase.ui.names

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stidyretrofitmoviebase.databinding.FragmentNamesBinding
import com.example.stidyretrofitmoviebase.domain.models.Names
import com.example.stidyretrofitmoviebase.presentation.names.NamesSearchViewModel
import com.example.stidyretrofitmoviebase.presentation.names.models.NamesState
import org.koin.androidx.viewmodel.ext.android.viewModel

class NamesFragment : Fragment() {

    private var _binding: FragmentNamesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: NamesAdapter

    private val vm: NamesSearchViewModel by viewModel()

    private var textWatcher: TextWatcher? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.namesList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter = NamesAdapter()
        binding.namesList.adapter = adapter

        vm.observeState().observe(viewLifecycleOwner) {
            render(it)
        }

        setTextWatcher()

    }

    private fun setTextWatcher() {
        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                vm.searchDebounce(s?.toString() ?: "")
            }

            override fun afterTextChanged(s: Editable?) {
            }

        }
        textWatcher.let { binding.queryInput.addTextChangedListener(it) }
    }

    private fun render(state: NamesState) {
        when (state) {
            is NamesState.Loading -> showLoading()
            is NamesState.Content -> showContent(state.names)
            is NamesState.Error -> showError(state.errorMessage)
            is NamesState.Empty -> showEmpty(state.message)
        }
    }

    private fun showLoading() {
        binding.namesList.visibility = View.GONE
        binding.placeholderMessage.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showError(errorMessage: String) {
        binding.namesList.visibility = View.GONE
        binding.placeholderMessage.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE

        binding.placeholderMessage.text = errorMessage
    }

    private fun showEmpty(emptyMessage: String) {
        showError(emptyMessage)
    }

    private fun showContent(names: List<Names>) {
        binding.namesList.visibility = View.VISIBLE
        binding.placeholderMessage.visibility = View.GONE
        binding.progressBar.visibility = View.GONE

        adapter.names.clear()
        adapter.names.addAll(names)
        adapter.notifyDataSetChanged()
    }
}
package com.deras.id.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.deras.id.R
import com.deras.id.database.HistoryEntity
import com.deras.id.databinding.FragmentHistoryBinding
import com.deras.id.utils.Helper
import com.google.android.material.dialog.MaterialAlertDialogBuilder

@Suppress("DEPRECATION")
class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private val historyViewModel: HistoryViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        binding.historyList.layoutManager = layoutManager

        historyViewModel.historyList.observe(viewLifecycleOwner) { historyList ->
            setHistoryData(historyList)
        }
    }
    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_history -> {
                val adapter = binding.historyList.adapter as? HistoryAdapter
                if (adapter != null && adapter.itemCount > 0) {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle(getString(R.string.question_delet_history))
                        .setMessage(getString(R.string.question_delet_permanen))
                        .setCancelable(true)
                        .setIcon(R.drawable.ic_delete)
                        .setPositiveButton(getString(R.string.Delet)) { _, _ ->
                            historyViewModel.removeAllHistory()
                            Helper.toast(requireContext(), getString(R.string.history_delet))
                        }
                        .setNegativeButton(getString(R.string.Cancel)) { dialog, _ -> dialog.dismiss() }
                        .show()
                } else {
                    Helper.toast(requireContext(), getString(R.string.You_have_history_detection))
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setHistoryData(consumer: List<HistoryEntity>) {
        val adapter = HistoryAdapter()
        adapter.submitList(consumer)
        binding.historyList.adapter = adapter
    }
}

package com.deras.id.ui.history

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.deras.id.R
import com.deras.id.database.HistoryEntity
import com.deras.id.databinding.ActivityHistoryBinding
import com.deras.id.utils.Helper
import com.deras.id.utils.HistoryAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder

@Suppress("DEPRECATION")
class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private val viewModel: HistoryViewModel by viewModels { HistoryViewModel(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.historyList.layoutManager = layoutManager

        viewModel.historyList.observe(this) {
            setHistoryData(it)
            setSupportActionBar(binding.toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.action_history -> {
                val adapter = binding.historyList.adapter as? HistoryAdapter
                if (adapter != null && adapter.itemCount > 0) {
                    MaterialAlertDialogBuilder(this@HistoryActivity)
                        .setTitle("Yakin ingin menghapus history?")
                        .setMessage("history akan terhapus permanen")
                        .setCancelable(true)
                        .setIcon(R.drawable.ic_delete)
                        .setPositiveButton("Hapus") { _, _ ->
                            viewModel.removeAllHistory()
                            Helper.toast(this@HistoryActivity, "History dihapus")
                        }
                        .setNegativeButton("Batal") { _, _ -> }
                        .show()
                } else {
                    Helper.toast(this@HistoryActivity, "Anda tidak memiliki history")
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
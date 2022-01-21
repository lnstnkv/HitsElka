package com.tsu.hitselka.activity_record_book

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.tsu.hitselka.R
import com.tsu.hitselka.activity_enhancement.EnhancementActivity
import com.tsu.hitselka.databinding.ActivityRecordBookBinding
import com.tsu.hitselka.model.GameLogic
import com.tsu.hitselka.model.Object
import com.tsu.hitselka.model.setFullscreen

class RecordBookActivity : AppCompatActivity() {
    private val binding by lazy { ActivityRecordBookBinding.inflate(layoutInflater) }

    private val listener = object : ImprovementAdapter.ImprovementAdapterListener {
        override fun onItemClick(item: Object) {
            val intent = Intent(this@RecordBookActivity, EnhancementActivity::class.java)
            intent.putExtra("ObjectInfo", item)
            startActivity(intent)
        }
    }

    private lateinit var adapter: ImprovementAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initActivity()
    }

    private fun initActivity() {
        setProgressBar()
        initRecyclerView()
        initListeners()
        setFullscreen()
    }

    private fun setProgressBar() {
        val progress = MutableLiveData<Int>()
        GameLogic.getYearProgress(progress)

        progress.observe(this) { percentage ->
            binding.progressTextView.text = resources.getString(R.string.midterms_progress, percentage)

            val width = getProgressBarWidth(percentage)
            if (width > 0) {
                binding.progressView.visibility = View.VISIBLE
                binding.progressView.layoutParams.width = width
            }
        }
    }

    private fun initRecyclerView() {
        adapter = ImprovementAdapter(this, listener)

        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(ImprovementItemDecoration())

        val buildings = MutableLiveData<List<Object>>()
        GameLogic.getBuildings(buildings)

        buildings.observe(this) {
            adapter.submitList(it)
        }
    }

    private fun initListeners() {
        binding.closeBtnView.setOnClickListener {
            finish()
        }
    }

    private fun getProgressBarWidth(percentage: Int): Int {
        val maxWidth = resources.getDimensionPixelSize(R.dimen.midterms_progress_width)
        return maxWidth * percentage / 100
    }
}
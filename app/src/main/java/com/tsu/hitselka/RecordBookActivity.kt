package com.tsu.hitselka

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tsu.hitselka.databinding.ActivityRecordBookBinding
import com.tsu.hitselka.model.Object

class RecordBookActivity : AppCompatActivity() {

    private val binding by lazy { ActivityRecordBookBinding.inflate(layoutInflater) }
    private val  improveAdapterListener = object : ImprovementAdapter.ImprovementAdapterListener {

        override fun onItemClick(item: Object) {
            println()
        }
    }
    private val improvementAdapter = ImprovementAdapter(improveAdapterListener)
    private fun initView() = with(binding) {
        recordRecycler.apply {
            adapter = improvementAdapter
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_book)
    }
}
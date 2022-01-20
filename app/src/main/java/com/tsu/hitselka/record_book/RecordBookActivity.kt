package com.tsu.hitselka.record_book

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.tsu.hitselka.R
import com.tsu.hitselka.databinding.ActivityRecordBookBinding
import com.tsu.hitselka.model.Object
import com.tsu.hitselka.model.setFullscreen

class RecordBookActivity : AppCompatActivity() {

    private val binding by lazy { ActivityRecordBookBinding.inflate(layoutInflater) }
    private val improveAdapterListener = object : ImprovementAdapter.ImprovementAdapterListener {

        override fun onItemClick(item: Object) {
            println()
        }
    }
    private val improvementAdapter = ImprovementAdapter(improveAdapterListener)
    private fun initView() = with(binding) {
        recordRecycler.layoutManager =
            LinearLayoutManager(this@RecordBookActivity,LinearLayoutManager.HORIZONTAL,false)
        recordRecycler.apply {
            adapter = improvementAdapter
            addItemDecoration(ImprovementItemDecoration())
        }
        val improvement = mutableListOf<Object>()

        improvement.add(Object("Hedgehog", R.drawable.hedgehog,0, 0))
        improvement.add(Object("Winter Maiden",  R.drawable.winter_maiden,0, 0))
        improvement.add(Object("Father Frost",  R.drawable.father_frost,0, 0))
        improvement.add(Object("University", R.drawable.university, 0, 0))

        improvementAdapter.submitList(improvement)
    }

    private fun initListeners() {
        binding.closeBtnView.setOnClickListener {
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
        initListeners()
        setFullscreen()

    }
}
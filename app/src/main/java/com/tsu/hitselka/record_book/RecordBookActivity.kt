package com.tsu.hitselka.record_book

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tsu.hitselka.R
import com.tsu.hitselka.databinding.ActivityRecordBookBinding
import com.tsu.hitselka.model.Object
import com.tsu.hitselka.model.setFullscreen

class RecordBookActivity : AppCompatActivity() {
    private val binding by lazy { ActivityRecordBookBinding.inflate(layoutInflater) }
    private val listener = object : ImprovementAdapter.ImprovementAdapterListener {
        override fun onItemClick(item: Object) {
            println()
        }
    }
    private lateinit var adapter: ImprovementAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initRecyclerView()
        initListeners()
        setFullscreen()
    }

    private fun initRecyclerView() {
        adapter = ImprovementAdapter(this, listener)

        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(ImprovementItemDecoration())

        val objects = mutableListOf<Object>()
        objects.add(Object("Hedgehog", R.drawable.hedgehog, 2, 0))
        objects.add(Object("Winter Maiden", R.drawable.winter_maiden, 2, 0))
        objects.add(Object("Father Frost", R.drawable.father_frost, 2, 0))
        objects.add(Object("University", R.drawable.university, 2, 0))
        objects.add(Object("Hedgehog", R.drawable.hedgehog, 3, 0))
        objects.add(Object("Winter Maiden", R.drawable.winter_maiden, 3, 0))
        objects.add(Object("Father Frost", R.drawable.father_frost, 3, 0))
        objects.add(Object("University", R.drawable.university, 3, 0))
        adapter.submitList(objects)
    }

    private fun initListeners() {
        binding.closeBtnView.setOnClickListener {
            finish()
        }
    }
}
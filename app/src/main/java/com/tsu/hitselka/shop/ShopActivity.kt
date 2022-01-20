package com.tsu.hitselka.shop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.tsu.hitselka.R
import com.tsu.hitselka.databinding.ActivityRecordBookBinding
import com.tsu.hitselka.databinding.ActivityShopBinding
import com.tsu.hitselka.model.ItemShop
import com.tsu.hitselka.model.Object
import com.tsu.hitselka.model.setFullscreen
import com.tsu.hitselka.record_book.ImprovementAdapter
import com.tsu.hitselka.record_book.ImprovementItemDecoration

class ShopActivity : AppCompatActivity() {
    private val binding by lazy { ActivityShopBinding.inflate(layoutInflater) }
    private val listener = object : ShopAdapter.ShopAdapterListener {
        override fun onItemClick(item: ItemShop) {
            println()
        }
    }
    private lateinit var adapter: ShopAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initRecyclerView()
        initListeners()
        setFullscreen()

    }

    private fun initRecyclerView() {
        adapter = ShopAdapter(this, listener)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(ImprovementItemDecoration())

        val shopItems = mutableListOf<ItemShop>()
        shopItems.add(ItemShop("Cup", R.drawable.hedgehog, 200))
        shopItems.add(ItemShop("Book", R.drawable.winter_maiden, 250))
        shopItems.add(ItemShop("Father Frost", R.drawable.father_frost, 340))
        shopItems.add(ItemShop("University", R.drawable.university, 410))
        shopItems.add(ItemShop("Hedgehog", R.drawable.hedgehog, 500))
        shopItems.add(ItemShop("Winter Maiden", R.drawable.winter_maiden,650))
        shopItems.add(ItemShop("Father Frost", R.drawable.father_frost, 700))
        shopItems.add(ItemShop("University", R.drawable.university, 1000))
        adapter.submitList(shopItems)
    }

    private fun initListeners() {
        binding.closeBtnView.setOnClickListener {
            finish()
        }
    }

}
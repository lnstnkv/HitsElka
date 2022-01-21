package com.tsu.hitselka.shop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tsu.hitselka.R
import com.tsu.hitselka.activity_record_book.ImprovementItemDecoration
import com.tsu.hitselka.databinding.ActivityShopBinding
import com.tsu.hitselka.model.ItemShop
import com.tsu.hitselka.model.setFullscreen

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
        shopItems.add(ItemShop("Cup", R.drawable.toy_cup, 200))
        shopItems.add(ItemShop("Sleep Potion", R.drawable.toy_cauldron, 250))
        shopItems.add(ItemShop("C++ Manual", R.drawable.toy_book, 340))
        shopItems.add(ItemShop("Wild Rose", R.drawable.toy_flower, 410))
        shopItems.add(ItemShop("Narcissus", R.drawable.toy_flower2, 500))
        shopItems.add(ItemShop("Chrysanthemum", R.drawable.toy_chrysanthemum, 650))
        shopItems.add(ItemShop("Pot of Gold", R.drawable.toy_money, 700))
        shopItems.add(ItemShop("Luck Potion", R.drawable.toy_luck_potion, 1000))
        adapter.submitList(shopItems)
    }

    private fun initListeners() {
        binding.closeBtnView.setOnClickListener {
            finish()
        }
    }

}
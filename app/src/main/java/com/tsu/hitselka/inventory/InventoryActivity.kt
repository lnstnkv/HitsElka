package com.tsu.hitselka.inventory

import android.content.ClipData
import android.content.ClipDescription
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tsu.hitselka.R
import com.tsu.hitselka.databinding.ActivityInventoryBinding
import com.tsu.hitselka.model.Inventory
import com.tsu.hitselka.model.setFullscreen
import com.tsu.hitselka.record_book.ImprovementItemDecoration


class InventoryActivity : AppCompatActivity() {
    private val binding by lazy { ActivityInventoryBinding.inflate(layoutInflater) }
    private val listener = object : InventoryAdapter.InventoryAdapterListener {
        override fun onItemClick(item: Inventory) {
            println()
        }
    }
    private lateinit var adapter: InventoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initRecycler()
        setFullscreen()
        //binding.rootDrag.setOnDragListener()
        binding.imageView.setOnLongClickListener {
            val cliptText="Thi is Clip Test"
            val item= ClipData.Item(cliptText)
            val mimeTypes= arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data=ClipData(cliptText,mimeTypes,item)

            val dragShadowBuilder=View.DragShadowBuilder(it)
            it.startDragAndDrop(data,dragShadowBuilder,it,0)
            it.visibility=View.VISIBLE
            true
        }

        val dragListener=View.OnDragListener { view, dragEvent ->
            when(dragEvent.action){
                DragEvent.ACTION_DRAG_STARTED->{
                    dragEvent.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
                }
                DragEvent.ACTION_DRAG_ENTERED->{
                    view.invalidate()
                    true
                }
                DragEvent.ACTION_DRAG_LOCATION->true
                DragEvent.ACTION_DRAG_EXITED->{
                    view.invalidate()
                    true
                }
                DragEvent.ACTION_DROP->{
                    val item=dragEvent.clipData.getItemAt(0)
                    val dragData=item.text
                    Toast.makeText(this,dragData,Toast.LENGTH_LONG).show()
                    view.invalidate()
                    val v=dragEvent.localState as View
                    val owner=v.parent as ViewGroup
                    owner.removeView(v)
                    val destination= view as ConstraintLayout
                    destination.addView(v)
                    v.visibility=View.VISIBLE
                    true
                }
                DragEvent.ACTION_DRAG_ENDED->{
                    view.invalidate()
                    true
                }
                else->false
            }
        }
    }


    private fun initRecycler() {
        itemTouchHelper.attachToRecyclerView(binding.recyclerInventory)
        adapter = InventoryAdapter()
        binding.recyclerInventory.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerInventory.addItemDecoration(ImprovementItemDecoration())
        val shopItems = mutableListOf<Inventory>()
        shopItems.add(Inventory(R.drawable.toy_cup, 200))
        shopItems.add(Inventory(R.drawable.toy_cauldron, 250))
        shopItems.add(Inventory(R.drawable.toy_book, 340))
        shopItems.add(Inventory(R.drawable.toy_flower, 410))
        shopItems.add(Inventory(R.drawable.toy_flower2, 500))
        shopItems.add(Inventory(R.drawable.toy_chrysanthemum, 650))
        shopItems.add(Inventory(R.drawable.toy_money, 700))
        shopItems.add(Inventory(R.drawable.toy_luck_potion, 1000))
        adapter.differ.submitList(shopItems)
        binding.recyclerInventory.adapter = adapter
    }

    private val itemTouchHelper by lazy {
        val itemTouchCallback =
            object : ItemTouchHelper.SimpleCallback(UP or DOWN or START or END, 0) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    val recyclerviewAdapter = recyclerView.adapter as InventoryAdapter
                    val fromPosition = viewHolder.bindingAdapterPosition
                    val toPosition = target.bindingAdapterPosition
                    recyclerviewAdapter.moveItem(fromPosition, toPosition)
                    recyclerviewAdapter.notifyItemMoved(fromPosition, toPosition)
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                }

                override fun onSelectedChanged(
                    viewHolder: RecyclerView.ViewHolder?,
                    actionState: Int
                ) {
                    super.onSelectedChanged(viewHolder, actionState)
                    if (actionState == ACTION_STATE_DRAG) {
                        viewHolder?.itemView?.scaleY = 1.3f
                        viewHolder?.itemView?.alpha = 0.7f

                    }
                }

                override fun clearView(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder
                ) {
                    super.clearView(recyclerView, viewHolder)
                    viewHolder.itemView.scaleY = 1.0f
                    viewHolder?.itemView?.alpha = 1.0f
                }

            }
        ItemTouchHelper(itemTouchCallback)
    }


}

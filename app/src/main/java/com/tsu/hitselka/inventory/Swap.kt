package com.tsu.hitselka.inventory

import android.content.Context
import androidx.recyclerview.widget.ItemTouchHelper

abstract class Swap(context: Context):ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END,ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {







}
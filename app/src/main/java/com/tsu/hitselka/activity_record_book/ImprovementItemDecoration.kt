package com.tsu.hitselka.activity_record_book

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tsu.hitselka.R

class ImprovementItemDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) return

        val sideMargin = parent.context.resources.getDimensionPixelSize(R.dimen.side_margin)
        val margin = parent.context.resources.getDimensionPixelSize(R.dimen.margin_8)

        val newRect = when (position) {
            0 -> Rect(sideMargin, 0, margin, 0)
            state.itemCount-1 -> Rect(0, 0, sideMargin, 0)
            else -> Rect(margin, 0, margin, 0)
        }

        outRect.apply {
            left = newRect.left
            right = newRect.right
            top = newRect.top
            bottom = newRect.bottom
        }
    }
}
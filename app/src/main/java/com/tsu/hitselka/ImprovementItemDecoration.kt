package com.tsu.hitselka

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ImprovementItemDecoration:RecyclerView.ItemDecoration(){
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) return


        val margin4 = parent.context.resources.getDimensionPixelSize(R.dimen.margin_4)
        val margin8 = parent.context.resources.getDimensionPixelSize(R.dimen.margin_8)


        val newRect = when (position) {
            0 -> Rect(margin8, margin4, margin4, margin4)
            state.itemCount-1-> Rect(margin4, margin4, margin8, margin4)

            else -> {
                    Rect(margin4, margin4, margin4, margin4)
            }


        }

        outRect.apply {
            left = newRect.left
            right = newRect.right
            top = newRect.top
            bottom = newRect.bottom
        }
    }
}
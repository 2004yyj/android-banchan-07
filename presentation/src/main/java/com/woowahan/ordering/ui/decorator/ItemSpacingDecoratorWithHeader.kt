package com.woowahan.ordering.ui.decorator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ItemSpacingDecoratorWithHeader(
    private val spacing: Int = 0,
    private val removeSpacePosition: List<Int> = listOf(-1),
    private val layoutDirection: Int = HORIZONTAL
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val adapterPosition = parent.getChildAdapterPosition(view)
        val itemCount = parent.adapter?.itemCount ?: 0

        with(outRect) {
            if (layoutDirection == HORIZONTAL) {
                if (!removeSpacePosition.contains(adapterPosition))
                    left = spacing
                if (adapterPosition == itemCount - 1)
                    right = spacing
            }

            if (layoutDirection == VERTICAL) {
                if (!removeSpacePosition.contains(adapterPosition))
                    top = spacing
                if (adapterPosition == itemCount - 1)
                    bottom = spacing
            }

            if (layoutDirection == GRID) {
                if (!removeSpacePosition.contains(adapterPosition)) {
                    val layoutManager = parent.layoutManager as? GridLayoutManager ?: return

                    val cols: Int = layoutManager.spanCount
                    val rows = itemCount / cols
                    outRect.left = spacing
                    outRect.right = if (adapterPosition % cols == cols - 1) spacing else 0
                    outRect.top = spacing
                    outRect.bottom = if (adapterPosition / cols == rows - 1) spacing else 0
                }
            }
        }
    }

    companion object {
        val HORIZONTAL = 0
        val VERTICAL = 1
        val GRID = 2
    }
}
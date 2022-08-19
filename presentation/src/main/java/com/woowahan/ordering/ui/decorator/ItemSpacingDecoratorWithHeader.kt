package com.woowahan.ordering.ui.decorator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ItemSpacingDecoratorWithHeader(
    private val spacing: Int = 0,
    private val spaceAdapters: List<RecyclerView.Adapter<*>>,
    private val layoutDirection: Int = HORIZONTAL
) : RecyclerView.ItemDecoration() {
    private fun isSpaceAdapter(adapter: RecyclerView.Adapter<*>?) = spaceAdapters.contains(adapter)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val viewHolder = parent.getChildViewHolder(view)
        val adapterPosition = viewHolder.bindingAdapterPosition
        val itemCount = viewHolder.bindingAdapter?.itemCount ?: 0
        if (isSpaceAdapter(viewHolder.bindingAdapter)) {
            with(outRect) {
                if (layoutDirection == HORIZONTAL) {
                    left = spacing
                    if (adapterPosition == itemCount - 1)
                        right = spacing
                }

                if (layoutDirection == VERTICAL) {
                    top = spacing
                    if (adapterPosition == itemCount - 1)
                        bottom = spacing
                }

                if (layoutDirection == GRID) {
                    val layoutManager = parent.layoutManager as? GridLayoutManager ?: return

                    val cols: Int = layoutManager.spanCount
                    val rows = itemCount / cols

                    outRect.left = if (adapterPosition % cols == 0) spacing else spacing / 2
                    outRect.right = if (adapterPosition % cols == cols - 1) spacing else spacing / 2
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
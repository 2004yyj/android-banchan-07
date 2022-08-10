package com.woowahan.ordering.data.mapper

import com.woowahan.ordering.data.entity.BestEntity
import com.woowahan.ordering.domain.model.Best

fun BestEntity.toModel(): Best {
    return Best(
        categoryId = categoryId,
        name = name,
        items = items.map {
            it.toModel()
        }
    )
}
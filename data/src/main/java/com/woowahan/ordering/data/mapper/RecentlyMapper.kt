package com.woowahan.ordering.data.mapper

import com.woowahan.ordering.data.entity.RecentlyEntity
import com.woowahan.ordering.domain.model.Recently

fun Recently.toEntity(): RecentlyEntity {
    return RecentlyEntity(
        detailHash,
        title,
        thumbnail,
        price,
        discountPrice,
        latestViewedTime
    )
}

fun RecentlyEntity.toModel(): Recently {
    return Recently(
        detailHash,
        title,
        thumbnail,
        price,
        discountPrice,
        latestViewedTime
    )
}
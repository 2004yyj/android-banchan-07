package com.woowahan.ordering.domain.model

sealed class SortType {
    object Default: SortType()
    object MoneyDesc: SortType()
    object MoneyAsc: SortType()
    object RateDesc: SortType()
}
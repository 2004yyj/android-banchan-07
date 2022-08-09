package com.woowahan.ordering.data.datasource

import com.woowahan.ordering.domain.model.Recently

interface RecentlyDataSource {
    fun insertRecently(recently: Recently)
    fun updateRecently(recently: Recently)
    fun getRecently(): List<Recently>
    fun getSimpleRecently(size: Int): List<Recently>
}

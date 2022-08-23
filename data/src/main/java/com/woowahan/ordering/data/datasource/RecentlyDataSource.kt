package com.woowahan.ordering.data.datasource

import com.woowahan.ordering.domain.model.Recently
import kotlinx.coroutines.flow.Flow

interface RecentlyDataSource {
    fun insertRecently(recently: Recently)
    fun updateRecently(recently: Recently)
    fun getRecently(): List<Recently>
    fun getSimpleRecently(size: Int): Flow<List<Recently>>
}

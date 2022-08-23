package com.woowahan.ordering.domain.repository

import com.woowahan.ordering.domain.model.Recently
import kotlinx.coroutines.flow.Flow

interface RecentlyRepository {
    fun insertRecently(recently: Recently)
    fun updateRecently(recently: Recently)
    fun getRecently(): List<Recently>
    fun getSimpleRecently(size: Int): Flow<List<Recently>>
}
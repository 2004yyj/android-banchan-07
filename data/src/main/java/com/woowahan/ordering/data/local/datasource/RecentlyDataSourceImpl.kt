package com.woowahan.ordering.data.local.datasource

import com.woowahan.ordering.data.datasource.RecentlyDataSource
import com.woowahan.ordering.data.local.dao.RecentlyDao
import com.woowahan.ordering.data.mapper.toEntity
import com.woowahan.ordering.data.mapper.toModel
import com.woowahan.ordering.domain.model.Recently
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecentlyDataSourceImpl @Inject constructor(
    private val recentlyDao: RecentlyDao
): RecentlyDataSource {
    override fun insertRecently(recently: Recently) {
        recentlyDao.insertRecently(recently.toEntity())
    }

    override fun updateRecently(recently: Recently) {
        recentlyDao.updateRecently(recently.toEntity())
    }

    override fun getRecently(): List<Recently> {
        return recentlyDao.getRecently().map {
            it.toModel()
        }
    }

    override fun getSimpleRecently(size: Int): Flow<List<Recently>> {
        return recentlyDao.getSimpleRecently(size).map {
            it.map { recently -> recently.toModel() }
        }
    }
}
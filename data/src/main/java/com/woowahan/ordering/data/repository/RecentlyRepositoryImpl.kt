package com.woowahan.ordering.data.repository

import com.woowahan.ordering.data.datasource.RecentlyDataSource
import com.woowahan.ordering.domain.model.Recently
import com.woowahan.ordering.domain.repository.RecentlyRepository
import javax.inject.Inject

class RecentlyRepositoryImpl @Inject constructor(
    private val dataSource: RecentlyDataSource
): RecentlyRepository {
    override fun insertRecently(recently: Recently) {
        return dataSource.insertRecently(recently)
    }

    override fun updateRecently(recently: Recently) {
        return dataSource.updateRecently(recently)
    }

    override fun getRecently(): List<Recently> {
        return dataSource.getRecently()
    }

    override fun getSimpleRecently(size: Int): List<Recently> {
        return dataSource.getSimpleRecently(size)
    }
}
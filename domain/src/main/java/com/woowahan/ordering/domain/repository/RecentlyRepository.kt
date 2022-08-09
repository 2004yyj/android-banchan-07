package com.woowahan.ordering.domain.repository

import com.woowahan.ordering.domain.model.Recently

interface RecentlyRepository {
    fun insertRecently(recently: Recently)
    fun updateRecently(recently: Recently)
    fun getRecently(): List<Recently>
    fun getSimpleRecently(size: Int): List<Recently>
}
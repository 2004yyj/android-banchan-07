package com.woowahan.ordering.domain.model

sealed class Menu {
    object Main : Menu()
    object Soup : Menu()
    object Side : Menu()
}
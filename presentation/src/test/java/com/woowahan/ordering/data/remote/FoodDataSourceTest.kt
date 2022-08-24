package com.woowahan.ordering.data.remote

import com.woowahan.ordering.data.datasource.FoodDataSource
import com.woowahan.ordering.data.entity.*
import com.woowahan.ordering.data.remote.datasource.FoodDataSourceImpl
import com.woowahan.ordering.impl.service.FakeFoodService
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FoodDataSourceTest {
    private lateinit var fakeFoodService: FakeFoodService
    private lateinit var foodDataSource: FoodDataSource

    @Before
    fun before() {
        fakeFoodService = FakeFoodService()
        foodDataSource = FoodDataSourceImpl(fakeFoodService)
    }

    @Test
    fun getBestList() = runTest {
        UnconfinedTestDispatcher()
        val expected = ListResponse(
            statusCode = 200,
            body = listOf(
                BestEntity(
                    categoryId = "17011000",
                    name = "풍성한 고기반찬",
                    items = listOf(
                        FoodEntity(
                            detailHash = "HBDEF",
                            image = "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_T.jpg",
                            alt = "오리 주물럭_반조리",
                            title = "오리 주물럭_반조리",
                            deliveryType = listOf("전국배송", "전국택배"),
                            description = "감칠맛 나는 매콤한 양념",
                            price = "15,800원",
                            discountedPrice = "12,640원",
                            badge = listOf("런칭특가")
                        ),
                        FoodEntity(
                            detailHash = "HBDEF",
                            image = "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_T.jpg",
                            alt = "소갈비찜",
                            title = "소갈비찜",
                            deliveryType = listOf("전국배송", "전국택배"),
                            description = "감칠맛 나는 매콤한 양념",
                            price = "28,900원",
                            discountedPrice = "26,010원",
                            badge = listOf("런칭특가")
                        )
                    )
                )
            )
        )
        val actual = fakeFoodService.getBestList().execute().body()
        assertEquals(expected, actual)
    }

    @Test
    fun getMainList() = runTest {
        val expected = ListResponse(
            statusCode = 200,
            body = listOf(
                FoodEntity(
                    detailHash = "HBDEF",
                    image = "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_T.jpg",
                    alt = "오리 주물럭_반조리",
                    title = "오리 주물럭_반조리",
                    deliveryType = listOf("전국배송", "전국택배"),
                    description = "감칠맛 나는 매콤한 양념",
                    price = "15,800원",
                    discountedPrice = "12,640원",
                    badge = listOf("런칭특가")
                ),
                FoodEntity(
                    detailHash = "HBDEF",
                    image = "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_T.jpg",
                    alt = "소갈비찜",
                    title = "소갈비찜",
                    deliveryType = listOf("전국배송", "전국택배"),
                    description = "감칠맛 나는 매콤한 양념",
                    price = "28,900원",
                    discountedPrice = "26,010원",
                    badge = listOf("런칭특가")
                )
            )
        )
        val actual = fakeFoodService.getMainList().execute().body()
        assertEquals(expected, actual)
    }

    @Test
    fun getSoupList() = runTest {
        val expected = ListResponse(
            statusCode = 200,
            body = listOf(
                FoodEntity(
                    detailHash = "HBDEF",
                    image = "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_T.jpg",
                    alt = "오리 주물럭_반조리",
                    title = "오리 주물럭_반조리",
                    deliveryType = listOf("전국배송", "전국택배"),
                    description = "감칠맛 나는 매콤한 양념",
                    price = "15,800원",
                    discountedPrice = "12,640원",
                    badge = listOf("런칭특가")
                ),
                FoodEntity(
                    detailHash = "HBDEF",
                    image = "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_T.jpg",
                    alt = "소갈비찜",
                    title = "소갈비찜",
                    deliveryType = listOf("전국배송", "전국택배"),
                    description = "감칠맛 나는 매콤한 양념",
                    price = "28,900원",
                    discountedPrice = "26,010원",
                    badge = listOf("런칭특가")
                )
            )
        )
        val actual = fakeFoodService.getSoupList().execute().body()
        assertEquals(expected, actual)
    }

    @Test
    fun getSideList() = runTest {
        val expected = ListResponse(
            statusCode = 200,
            body = listOf(
                FoodEntity(
                    detailHash = "HBDEF",
                    image = "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_T.jpg",
                    alt = "오리 주물럭_반조리",
                    title = "오리 주물럭_반조리",
                    deliveryType = listOf("전국배송", "전국택배"),
                    description = "감칠맛 나는 매콤한 양념",
                    price = "15,800원",
                    discountedPrice = "12,640원",
                    badge = listOf("런칭특가")
                ),
                FoodEntity(
                    detailHash = "HBDEF",
                    image = "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_T.jpg",
                    alt = "소갈비찜",
                    title = "소갈비찜",
                    deliveryType = listOf("전국배송", "전국택배"),
                    description = "감칠맛 나는 매콤한 양념",
                    price = "28,900원",
                    discountedPrice = "26,010원",
                    badge = listOf("런칭특가")
                )
            )
        )
        val actual = fakeFoodService.getSideList().execute().body()
        assertEquals(expected, actual)
    }

    @Test
    fun getFoodDetail() = runTest {
        val expected = FoodDetailResponse(
            hash = "HBDEF",
            data = FoodDetailEntity(
                topImage = "http://public.codesquad.kr/jk/storeapp/data/1155_ZIP_P_0081_T.jpg",
                thumbImages = listOf(
                    "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_T.jpg",
                    "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_S.jpg"
                ),
                productDescription = "오리 주물럭_반조리",
                point = "126원",
                deliveryInfo = "서울 경기 새벽 배송, 전국 택배 배송",
                deliveryFee = "2,500원 (40,000원 이상 구매 시 무료)",
                prices = listOf(
                    "15,800원",
                    "12,640원"
                ),
                detailSection = listOf(
                    "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_D1.jpg",
                    "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_D2.jpg",
                    "http://public.codesquad.kr/jk/storeapp/data/pakage_regular.jpg"
                )
            )
        )
        val actual = fakeFoodService.getFoodDetail("HBDEF").execute().body()
        assertEquals(expected, actual)
    }
}
package com.woowahan.ordering.impl.service

import com.woowahan.ordering.data.entity.*
import com.woowahan.ordering.data.remote.service.FoodService
import com.woowahan.ordering.network.FakeCall
import retrofit2.Call
import retrofit2.Response

class FakeFoodService : FoodService {
    override fun getBestList(): Call<ListResponse<BestEntity>> {
        return object : FakeCall<ListResponse<BestEntity>>() {
            override fun execute(): Response<ListResponse<BestEntity>> {
                return Response.success(
                    ListResponse(
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
                )
            }
        }
    }

    override fun getMainList(): Call<ListResponse<FoodEntity>> {
        return object : FakeCall<ListResponse<FoodEntity>>() {
            override fun execute(): Response<ListResponse<FoodEntity>> {
                return Response.success(
                    ListResponse(
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
                )
            }
        }
    }

    override fun getSoupList(): Call<ListResponse<FoodEntity>> {
        return object : FakeCall<ListResponse<FoodEntity>>() {
            override fun execute(): Response<ListResponse<FoodEntity>> {
                return Response.success(
                    ListResponse(
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
                )
            }
        }
    }

    override fun getSideList(): Call<ListResponse<FoodEntity>> {
        return object : FakeCall<ListResponse<FoodEntity>>() {
            override fun execute(): Response<ListResponse<FoodEntity>> {
                return Response.success(
                    ListResponse(
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
                )
            }
        }
    }

    override fun getFoodDetail(hash: String): Call<FoodDetailResponse> {
        return object : FakeCall<FoodDetailResponse>() {
            override fun execute(): Response<FoodDetailResponse> {
                return Response.success(
                    FoodDetailResponse(
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
                )
            }
        }
    }
}



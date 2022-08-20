package com.woowahan.ordering.ui.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.woowahan.ordering.domain.usecase.order.UpdateOrderUseCase
import com.woowahan.ordering.ui.receiver.CartReceiver.Companion.DELIVERY_FINISHED_TIME
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.coroutineScope

@HiltWorker
class DeliveryTimeWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val updateOrderUseCase: UpdateOrderUseCase
): CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        coroutineScope {
            val deliveryTime = inputData.getLong(DELIVERY_FINISHED_TIME, 0)
            updateOrderUseCase(deliveryTime, true)
        }

        return Result.success()
    }

}

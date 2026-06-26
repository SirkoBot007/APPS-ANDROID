package com.sirko007.cryptopulse.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sirko007.cryptopulse.data.repository.CryptoRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

/**
 * Periodic background job that refreshes prices even when the app is closed.
 * @HiltWorker lets Hilt inject the repository into a WorkManager worker.
 */
@HiltWorker
class PriceRefreshWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val repository: CryptoRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return repository.refresh().fold(
            onSuccess = { Result.success() },
            onFailure = { Result.retry() }
        )
    }

    companion object {
        const val NAME = "price_refresh_work"
    }
}

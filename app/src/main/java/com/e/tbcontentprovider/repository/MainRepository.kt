package com.e.tbcontentprovider.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.e.tbcontentprovider.model.ModelContent
import com.e.tbcontentprovider.persistence.MainDao
import com.e.tbcontentprovider.ui.DataState
import com.e.tbcontentprovider.ui.main.state.MainViewState
import com.e.tbreview.util.AbsentLiveData
import com.e.tbreview.util.ApiSuccessResponse
import com.e.tbreview.util.GenericApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepository
@Inject
constructor(
    val mainDao: MainDao
) : JobManager("MainRepository") {

    private val TAG = "MainRepository"
    fun getData(): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<Any, List<ModelContent>, MainViewState>(
            false,
            true,
            true,
            false
        ) {

            override fun createCall(): LiveData<GenericApiResponse<Any>> {
                return AbsentLiveData.create()
            }

            override fun setJob(job: Job) {
                addJob("getData", job)
            }

            override fun loadFromCache(): LiveData<MainViewState> {
                return AbsentLiveData.create()
            }

            override suspend fun createCacheRequestAndReturn() {
                withContext(Dispatchers.Main) {
                    result.addSource(loadFromCache()) { viewState ->
                        onCompleteJob(DataState.data(viewState, null))
                        Log.e(TAG, "createCacheRequestAndReturn: $viewState")
                    }

                }

            }

            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<Any>) {
                Log.e(TAG, "handleApiSuccessResponse: ${response}")
                createCacheRequestAndReturn()
            }

            override suspend fun updateLocalDb(cacheObject: List<ModelContent>?) {
                Log.e(TAG, "updateLocalDb: $cacheObject")
                if (cacheObject != null) {
                    withContext(Dispatchers.IO) {

//                        try {
//                            launch { mainDao.deleteAll() }
//                        }
//                        catch (e:Exception){
//                            Log.e(TAG, "updateLocalDb: error in deleting all " )
//                        }

                        for (item in cacheObject) {
                            try {
                                launch {
                                    Log.d(TAG, "updateLocalDb: inserting item: $item")
                                    mainDao.insert(item)
                                }

                            } catch (e: Exception) {
                                Log.e(
                                    TAG, "updateLocalDb: error updating cache" +
                                            "on item with name: ${item}"
                                )
                            }
                        }
                    }
                }
            }

        }.asLiveData()
    }
}
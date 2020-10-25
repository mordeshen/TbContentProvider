package com.e.tbcontentprovider.ui.main

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.e.tbcontentprovider.model.ModelContent
import com.e.tbcontentprovider.repository.MainRepository
import com.e.tbcontentprovider.ui.BaseViewModel
import com.e.tbcontentprovider.ui.DataState
import com.e.tbcontentprovider.ui.main.state.MainViewState
import com.e.tbreview.ui.main.state.MainStateEvent
import com.e.tbreview.util.AbsentLiveData
import javax.inject.Inject

class MainViewModel
@Inject
constructor(
    private val repository: MainRepository,
    private val sharedPreferences: SharedPreferences,
    private val editor: SharedPreferences.Editor
) : BaseViewModel<MainStateEvent, MainViewState>() {


    override fun handleStateEvent(stateEvent: MainStateEvent): LiveData<DataState<MainViewState>> {
        println("DEBUG: New StateEvent detected: $stateEvent")
        when (stateEvent) {

            is MainStateEvent.GetItemsEvent -> {
                return repository.getData()
            }

            is MainStateEvent.None -> {
                return AbsentLiveData.create()
            }
        }
    }

    fun setApiListData(items: List<ModelContent>) {
        val update = getCurrentViewStateOrNew()
        update.listField.fullList = items
        _viewState.value = update
    }

    fun getWholeList(): List<ModelContent> {
        getCurrentViewStateOrNew().let {
            return it.listField.fullList.let { fullList ->
                return fullList
            }
        }
    }

    fun cancelActiveJobs() {
        repository.cancelActiveJobs()
        handlePendingData()
    }

    private fun handlePendingData() {
        setStateEvent(MainStateEvent.None())
    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }

    override fun initNewViewState(): MainViewState {
        return MainViewState()
    }

}














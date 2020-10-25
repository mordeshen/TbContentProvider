package com.e.tbcontentprovider.ui.main.state

import com.e.tbcontentprovider.model.ModelContent


data class MainViewState(
    var listField: ListFields = ListFields()
) {
    data class ListFields(
        var fullList: List<ModelContent> = ArrayList()
    )
}
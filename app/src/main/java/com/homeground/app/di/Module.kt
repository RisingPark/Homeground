package com.homeground.app.di

import com.tistory.deque.kotlinmvvmsample.model.DataModel
import com.tistory.deque.kotlinmvvmsample.model.DataModelImpl
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

var modelPart = module {
    factory<DataModel> {
        DataModelImpl()
    }
}

var viewModelPart = module {
//    viewModel {
//        SettingViewModel(get())
//    }
}

var adapterPart = module {
//    factory { UserRecyclerViewAdapter() }
}

var module = listOf(viewModelPart, modelPart, adapterPart)
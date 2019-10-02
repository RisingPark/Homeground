package com.homeground.app.di

import com.homeground.app.view.main.adapter.MenuRecyclerViewAdapter
import com.tistory.deque.kotlinmvvmsample.model.DataModel
import com.homeground.app.model.DataModelImpl
import com.homeground.app.view.auth.signup.model.UserInfoViewModel
import com.homeground.app.view.intro.model.IntroViewModel
import com.homeground.app.view.main.model.*
import com.homeground.app.view.point.save.model.PointSaveModel
import com.homeground.app.view.point.save.model.PointSaveModelImpl
import com.homeground.app.view.point.save.model.PointSaveViewModel
import com.homeground.app.view.point.search.adapter.UserListRecyclerViewAdapter
import com.homeground.app.view.point.search.model.PointSearchModel
import com.homeground.app.view.point.search.model.PointSearchModelImpl
import com.homeground.app.view.point.search.model.PointSearchViewModel
import com.homeground.app.view.settings.model.SettingsModel
import com.homeground.app.view.settings.model.SettingsModelImpl
import com.homeground.app.view.settings.model.SettingsViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

var modelPart = module {
    factory<DataModel> { DataModelImpl() }
    factory<MainModel> { MainModelImpl() }
    factory<UserInfoModel> { UserInfoModelImpl() }
    factory<PointSaveModel> { PointSaveModelImpl() }
    factory<PointSearchModel> { PointSearchModelImpl() }
    factory<IntroModel> { IntroModelImpl() }
    factory<SettingsModel> { SettingsModelImpl() }
}

var viewModelPart = module {
    viewModel { MainViewModel(get()) }
    viewModel { UserInfoViewModel(get()) }
    viewModel { PointSaveViewModel(get()) }
    viewModel { PointSearchViewModel(get()) }
    viewModel { IntroViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
}

var adapterPart = module {
    factory { MenuRecyclerViewAdapter() }
    factory { UserListRecyclerViewAdapter() }
}

var module = listOf(viewModelPart, modelPart, adapterPart)
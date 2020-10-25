package com.e.tbcontentprovider.di.main

import androidx.lifecycle.ViewModel
import com.e.tbcontentprovider.di.ViewModelKey
import com.e.tbcontentprovider.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindBlogViewModel(mainViewModel: MainViewModel): ViewModel

}
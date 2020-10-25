package com.e.tbcontentprovider.di


import com.e.tbcontentprovider.di.main.MainModule
import com.e.tbcontentprovider.di.main.MainViewModelModule
import com.e.tbcontentprovider.ui.main.MainActivity
import com.e.tbreview.di.main.MainScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {


    @MainScope
    @ContributesAndroidInjector(
        modules = [MainModule::class, MainViewModelModule::class]
    )
    abstract fun contributeMainActivity(): MainActivity

}
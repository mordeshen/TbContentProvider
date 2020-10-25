package com.e.tbcontentprovider.di.main

import com.e.tbcontentprovider.persistence.AppDatabase
import com.e.tbcontentprovider.persistence.MainDao
import com.e.tbcontentprovider.repository.MainRepository
import com.e.tbreview.di.main.MainScope
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @MainScope
    @Provides
    fun provideMainRepository(
        mainDao: MainDao
    ): MainRepository {
        return MainRepository(mainDao)
    }

    @MainScope
    @Provides
    fun provideMainDao(db: AppDatabase): MainDao {
        return db.getMainDao()
    }
//
//    @MainScope
//    @Provides
//    fun provideContentProvider(mainDao: MainDao): MyContentProvider {
//        return MyContentProvider(mainDao)
//    }
}
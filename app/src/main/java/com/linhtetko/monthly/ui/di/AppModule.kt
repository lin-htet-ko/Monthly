package com.linhtetko.monthly.ui.di

import com.linhtetko.monthly.ui.locale.repository.LanguageRepository
import com.linhtetko.monthly.ui.locale.repository.LanguageRepositoryImpl
import com.linhtetko.monthly.ui.locale.sources.LocalLanguage
import com.linhtetko.monthly.ui.locale.sources.LocalLanguageImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [AppModule.AppModuleBinder::class])
@InstallIn(SingletonComponent::class)
class AppModule {

    @Module
    @InstallIn(SingletonComponent::class)
    interface AppModuleBinder {

        @Binds
        @Singleton
        fun bindLocalizationLocal(impl: LocalLanguageImpl): LocalLanguage

        @Binds
        @Singleton
        fun bindAppLanguageRepository(impl: LanguageRepositoryImpl): LanguageRepository
    }

}

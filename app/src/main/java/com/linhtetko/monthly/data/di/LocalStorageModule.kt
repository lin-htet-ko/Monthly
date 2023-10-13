package com.linhtetko.monthly.data.di

import android.content.Context
import android.content.SharedPreferences
import com.linhtetko.monthly.data.local.pref.PreferenceManager
import com.linhtetko.monthly.data.local.pref.SharePreferenceManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [LocalProviderModule.LocalBinderModule::class])
@InstallIn(SingletonComponent::class)
class LocalProviderModule {


    @Module
    @InstallIn(SingletonComponent::class)
    interface LocalBinderModule {
        @Binds
        @Singleton
        fun bindPref(impl: SharePreferenceManager): PreferenceManager
    }

    @Provides
    @Singleton
    fun bindSharePref(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(PreferenceManager.PREFERENCES, Context.MODE_PRIVATE)

}
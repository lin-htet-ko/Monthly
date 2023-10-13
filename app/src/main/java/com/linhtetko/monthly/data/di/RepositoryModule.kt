package com.linhtetko.monthly.data.di

import com.linhtetko.monthly.data.repositories.AuthRepository
import com.linhtetko.monthly.data.repositories.AuthRepositoryImpl
import com.linhtetko.monthly.data.repositories.ItemRepository
import com.linhtetko.monthly.data.repositories.ItemRepositoryImpl
import com.linhtetko.monthly.data.repositories.UserRepository
import com.linhtetko.monthly.data.repositories.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindAuthRepo(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    fun bindItemRepo(impl: ItemRepositoryImpl): ItemRepository

    @Binds
    fun bindUserRepo(impl: UserRepositoryImpl): UserRepository
}
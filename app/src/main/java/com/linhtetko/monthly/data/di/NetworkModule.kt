package com.linhtetko.monthly.data.di

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.linhtetko.monthly.data.local.pref.PreferenceManager
import com.linhtetko.monthly.data.network.ApiConsumer
import com.linhtetko.monthly.data.network.FirebaseApiConsumer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideFirebase(@ApplicationContext context: Context): FirebaseApp =
        Firebase.app

    @Singleton
    @Provides
    fun provideFirestore(app: FirebaseApp): FirebaseFirestore = Firebase.firestore(app)

    @Singleton
    @Provides
    fun provideFireAuth(app: FirebaseApp): FirebaseAuth = Firebase.auth(app)

    @Singleton
    @Provides
    fun provideFirebaseStorage(app: FirebaseApp): FirebaseStorage = Firebase.storage(app)

    @Singleton
    @Provides
    fun provideApiConsumer(
        firestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth,
        firebaseStorage: FirebaseStorage,
        preferenceManager: PreferenceManager
    ): ApiConsumer =
        FirebaseApiConsumer(firestore, firebaseAuth, firebaseStorage, preferenceManager)
}
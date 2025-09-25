package com.emirhankarci.tutorly.di

import android.content.Context
import com.emirhankarci.tutorly.data.manager.TokenManager
import com.emirhankarci.tutorly.data.repository.AuthRepositoryImpl
import com.emirhankarci.tutorly.data.repository.TokenRepository
import com.emirhankarci.tutorly.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideTokenManager(
        @ApplicationContext context: Context
    ): TokenManager = TokenManager(context)

    @Provides
    @Singleton
    fun provideTokenRepository(
        tokenManager: TokenManager,
        firebaseAuth: FirebaseAuth,
        @ApplicationContext context: Context
    ): TokenRepository = TokenRepository(tokenManager, firebaseAuth, context)

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth,
        tokenRepository: TokenRepository,
        @ApplicationContext context: Context
    ): AuthRepository = AuthRepositoryImpl(firebaseAuth, tokenRepository, context)
}
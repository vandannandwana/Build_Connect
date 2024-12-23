package com.nandwana.buildconnect.di

import android.content.Context
import android.content.SharedPreferences
import com.nandwana.buildconnect.data.network.EngineerService
import com.nandwana.buildconnect.data.network.ProjectService
import com.nandwana.buildconnect.data.network.UserService
import com.nandwana.buildconnect.utils.Constants.Companion.BASE_URL_PROFESSIONAL
import com.nandwana.buildconnect.utils.Constants.Companion.BASE_URL_PROJECT
import com.nandwana.buildconnect.utils.Constants.Companion.BASE_URL_USER
import com.nandwana.buildconnect.utils.Constants.Companion.USER_PREFERENCE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {


    @Provides
    @Singleton
    fun provideUserService(): UserService {
        return Retrofit.Builder().baseUrl(BASE_URL_USER)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun provideEngineerService(): EngineerService {
        return Retrofit.Builder().baseUrl(BASE_URL_PROFESSIONAL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(EngineerService::class.java)
    }

    @Provides
    @Singleton
    fun providesProjectsService(): ProjectService {
        return Retrofit.Builder().baseUrl(BASE_URL_PROJECT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProjectService::class.java)
    }

    @Named("userPreference")
    @Provides
    @Singleton
    fun provideUserPreference(@ApplicationContext context: Context): SharedPreferences {
        val userPreference = context.getSharedPreferences(USER_PREFERENCE, Context.MODE_PRIVATE)
        return userPreference
    }


}
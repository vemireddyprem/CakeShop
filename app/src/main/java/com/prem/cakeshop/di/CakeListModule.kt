package com.prem.cakeshop.di

import com.prem.cakeshop.data.remote.CakeInfoApi
import com.prem.cakeshop.data.remote.Constants
import com.prem.cakeshop.data.remote.repository.CakeInfoRepositoryImp
import com.prem.cakeshop.domain.repository.CakeInfoRepository
import com.prem.cakeshop.domain.usecase.GetCakesUsecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CakeListModule {

    @Provides
    @Singleton
    fun provideGetCakesListUseCase(repository: CakeInfoRepository): GetCakesUsecase {
        return GetCakesUsecase(repository)
    }

    @Provides
    @Singleton
    fun provideCakeInfoRepository(api: CakeInfoApi): CakeInfoRepository {
        return CakeInfoRepositoryImp(api)
    }

    @Provides
    @Singleton
    fun provideCakeInfoApi(): CakeInfoApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(CakeInfoApi::class.java)
    }
}
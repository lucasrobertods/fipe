package br.com.lrds.fipe.di

import br.com.lrds.fipe.data.api.FipeApi
import br.com.lrds.fipe.data.repository.FipeRepository
import br.com.lrds.fipe.data.repository.FipeRepositoryImpl
import br.com.lrds.fipe.domain.usecase.GetBrandsUseCase
import br.com.lrds.fipe.domain.usecase.GetBrandsUseCaseImpl
import br.com.lrds.fipe.presenter.ui.viewmodel.FipeViewModel
import br.com.lrds.fipe.shared.network.Service
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
class FipeModule {

    @Provides
    fun providesFipeApi() : FipeApi = Service().createService(FipeApi::class.java)

    @Provides
    fun providesFipeRepository(
        service: FipeApi
    ) : FipeRepository = FipeRepositoryImpl(service)

    @Provides
    fun providesGetBrandsUseCase(
        fipeRepository: FipeRepository
    ) : GetBrandsUseCase = GetBrandsUseCaseImpl(fipeRepository)

}
package br.com.lrds.fipe.presenter.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.lrds.fipe.domain.model.Brand
import br.com.lrds.fipe.domain.usecase.GetBrandsUseCase
import br.com.lrds.fipe.presenter.viewmodel.FipeViewModel
import br.com.lrds.fipe.shared.network.NetworkResult
import br.com.lrds.fipe.shared.ui.ViewState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.net.HttpURLConnection

@ExperimentalCoroutinesApi
internal class FipeViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    private val dispatcher = StandardTestDispatcher()

    private val getBrandsUseCase = mockk<GetBrandsUseCase>()
    private val fipeViewModel = FipeViewModel(getBrandsUseCase, dispatcher)

    @Test
    fun `given a load brand caller when observe then return loading and after content state successful`() = runTest {

        val viewStates = mutableListOf<ViewState>()
        val brandList = (0..2).map {
            Brand(
                code = it.toString(),
                name = "name $it"
            )
        }
        coEvery { getBrandsUseCase.invoke() } returns NetworkResult.Success(brandList)

        fipeViewModel.viewState.observeForever {
            viewStates.add(it)
        }

        fipeViewModel.loadBrands()
        dispatcher.scheduler.advanceUntilIdle()

        assert(viewStates.size == 2)
        assert(viewStates[0] is ViewState.Loading)
        assert(viewStates[1] is ViewState.Content<*>)
        assert((viewStates[1] as ViewState.Content<List<Brand>>).data.size == brandList.size)

    }

    @Test
    fun `given a load brand caller when observe and get errors then return loading and after error state`() = runTest {

        val viewStates = mutableListOf<ViewState>()
        val code = HttpURLConnection.HTTP_INTERNAL_ERROR
        val message = "Internal Error"
        coEvery { getBrandsUseCase.invoke() } returns NetworkResult.Error(code, message)

        fipeViewModel.viewState.observeForever {
            viewStates.add(it)
        }

        fipeViewModel.loadBrands()
        dispatcher.scheduler.advanceUntilIdle()

        assert(viewStates.size == 2)
        assert(viewStates[0] is ViewState.Loading)
        assert(viewStates[1] is ViewState.Error)
        assert((viewStates[1] as ViewState.Error).message == message)

    }

}
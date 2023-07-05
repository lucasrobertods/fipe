package br.com.lrds.fipe.domain.usecase

import br.com.lrds.fipe.data.repository.FipeRepository
import br.com.lrds.fipe.domain.model.Brand
import br.com.lrds.fipe.shared.network.NetworkResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.net.HttpURLConnection


internal class GetBrandsUseCaseTest {

    private val fipeRepository = mockk<FipeRepository>()
    private val getBrandsUseCase = GetBrandsUseCaseImpl(fipeRepository)

    @Test
    fun `given a success network state with brand list when call getBrands then must return with success`() = runBlocking {

        val brandList = listOf(
            Brand(
                code = "1",
                name = "name 1"
            ),Brand(
                code = "2",
                name = "name 2"
            ),
        )
        coEvery { fipeRepository.getBrands() } returns NetworkResult.Success(brandList)

        val result = getBrandsUseCase()

        assert(result is NetworkResult.Success)
        assert((result as NetworkResult.Success).data.size == brandList.size)

    }

    @Test
    fun `given a error state with code and message when call getBrands then must return a error`() = runBlocking {

        val code = HttpURLConnection.HTTP_INTERNAL_ERROR
        val message = "Internal Error"
        coEvery { fipeRepository.getBrands() } returns NetworkResult.Error(code, message)

        val result = getBrandsUseCase()

        assert(result is NetworkResult.Error)
        assert((result as NetworkResult.Error).statusCode == code)
        assert(result.message == message)

    }

}
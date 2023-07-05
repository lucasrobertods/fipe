package br.com.lrds.fipe.data.repository

import br.com.lrds.fipe.data.api.FipeApi
import br.com.lrds.fipe.data.model.response.BrandResponse
import br.com.lrds.fipe.shared.network.NetworkResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.Response
import java.net.HttpURLConnection

@ExperimentalCoroutinesApi
internal class FipeRepositoryTest {

    private val api = mockk<FipeApi>()
    private val fipeRepository = FipeRepositoryImpl(api)

    @Test
    fun `given a success response when call get brands then it returns with network success list`() = runTest {

        val brandResponses = (0..2).map {
            BrandResponse(
                codigo = it.toString(),
                nome = "nome $it"
            )
        }
        coEvery { api.getBrands() } returns Response.success(brandResponses)

        val result = fipeRepository.getBrands()

        assert(result is NetworkResult.Success)
        assert((result as NetworkResult.Success).data.size == brandResponses.size)
        assert(result.data[2].code == "2")
        assert(result.data[1].name == "nome 1")

    }

    @Test
    fun `given a error response when call get brands then it returns with network error`() = runTest {

        val code = HttpURLConnection.HTTP_INTERNAL_ERROR
        val message = "Internal Error"
        coEvery { api.getBrands() } returns Response.error(code, message.toResponseBody())

        val result = fipeRepository.getBrands()

        assert(result is NetworkResult.Error)
        assert((result as NetworkResult.Error).statusCode == code)

    }

}
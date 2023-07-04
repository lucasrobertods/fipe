package br.com.lrds.fipe.data.repository

import br.com.lrds.fipe.data.api.FipeApi
import br.com.lrds.fipe.data.model.response.toBrand
import br.com.lrds.fipe.domain.model.Brand
import br.com.lrds.fipe.shared.network.NetworkResult
import br.com.lrds.fipe.shared.network.parseResponse
import javax.inject.Inject

class FipeRepositoryImpl @Inject constructor(
    private val api: FipeApi
) : FipeRepository {

    override suspend fun getBrands(): NetworkResult<List<Brand>> {
        return when (val result = api.getBrands().parseResponse()) {
            is NetworkResult.Success -> {
                NetworkResult.Success(result.value.toBrand())
            }
            is NetworkResult.Error -> result
        }
    }

}

interface FipeRepository {
    suspend fun getBrands(): NetworkResult<List<Brand>>
}
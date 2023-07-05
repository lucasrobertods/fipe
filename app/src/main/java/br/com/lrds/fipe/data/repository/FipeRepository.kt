package br.com.lrds.fipe.data.repository

import br.com.lrds.fipe.data.api.FipeApi
import br.com.lrds.fipe.data.model.response.toBrand
import br.com.lrds.fipe.domain.model.Brand
import br.com.lrds.fipe.shared.network.NetworkResult
import br.com.lrds.fipe.shared.network.safeApiCall
import javax.inject.Inject

class FipeRepositoryImpl @Inject constructor(
    private val api: FipeApi
) : FipeRepository {

    override suspend fun getBrands(): NetworkResult<List<Brand>> {
        return safeApiCall {
            api.getBrands()
        }.let { networkResult ->
            when (networkResult) {
                is NetworkResult.Success -> {
                    NetworkResult.Success(networkResult.data.toBrand())
                }
                is NetworkResult.Error -> networkResult
            }
        }
    }

}

interface FipeRepository {
    suspend fun getBrands(): NetworkResult<List<Brand>>
}
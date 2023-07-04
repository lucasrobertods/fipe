package br.com.lrds.fipe.domain.usecase

import br.com.lrds.fipe.data.repository.FipeRepository
import br.com.lrds.fipe.domain.model.Brand
import br.com.lrds.fipe.shared.network.NetworkResult
import javax.inject.Inject

class GetBrandsUseCaseImpl @Inject constructor(
    private val repository: FipeRepository
) : GetBrandsUseCase {
    override suspend fun invoke(): NetworkResult<List<Brand>> = repository.getBrands()
}

interface GetBrandsUseCase {
    suspend operator fun invoke() : NetworkResult<List<Brand>>
}
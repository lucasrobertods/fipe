package br.com.lrds.fipe.data.api

import br.com.lrds.fipe.data.model.response.BrandResponse
import retrofit2.Response
import retrofit2.http.GET

interface FipeApi {

    @GET(BRAND_ENDPOINT)
    suspend fun getBrands() : Response<List<BrandResponse>>

    companion object {
        private const val BRAND_ENDPOINT = "carros/marcas"
    }

}
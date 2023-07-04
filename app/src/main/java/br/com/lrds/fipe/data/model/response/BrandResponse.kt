package br.com.lrds.fipe.data.model.response

import br.com.lrds.fipe.domain.model.Brand

data class BrandResponse(
    val codigo: String,
    val nome: String
)

fun BrandResponse.toBrand() = Brand(
    code = codigo,
    name = nome
)

fun List<BrandResponse>.toBrand() = map {
    it.toBrand()
}
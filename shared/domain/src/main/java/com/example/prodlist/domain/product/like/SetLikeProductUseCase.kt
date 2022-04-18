package com.example.prodlist.domain.product.like

import com.example.prodlist.domain.product.ProductRepository
import com.example.prodlist.domain.usecase.CoroutineUseCase
import com.example.prodlist.ktutil.coroutine.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SetLikeProductUseCase @Inject constructor(
    @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
    private val productRepository: ProductRepository,
) : CoroutineUseCase<SetLikeProductUseCase.Params, Unit>(defaultDispatcher) {

    override suspend fun execute(parameters: Params) {
        productRepository.setLike(parameters.productKey, parameters.like)
    }

    data class Params(
        val productKey: String,
        val like: Boolean,
    )
}
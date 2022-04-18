package com.example.prodlist.domain.product

import com.example.prodlist.domain.usecase.CoroutineUseCase
import com.example.prodlist.ktutil.coroutine.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class IsProductExistUseCase @Inject constructor(
    @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
    private val productRepository: ProductRepository,
) : CoroutineUseCase<IsProductExistUseCase.Params, Boolean>(defaultDispatcher) {

    override suspend fun execute(parameters: Params): Boolean {
        return productRepository.isProductExist(parameters.productKey)
    }

    data class Params(val productKey: String)
}
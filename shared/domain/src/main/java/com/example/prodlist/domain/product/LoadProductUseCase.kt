package com.example.prodlist.domain.product

import com.example.prodlist.domain.usecase.FlowUseCase
import com.example.prodlist.ktutil.coroutine.DefaultDispatcher
import com.example.prodlist.ktutil.flow.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadProductUseCase @Inject constructor(
    @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
    private val productRepository: ProductRepository,
) : FlowUseCase<LoadProductUseCase.Params, Product>(defaultDispatcher) {

    override fun execute(parameters: Params): Flow<Result<Product>> {
        return productRepository.getProduct(parameters.productKey)
    }

    data class Params(val productKey: String)
}
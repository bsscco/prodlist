package com.example.prodlist.domain.product

import com.example.prodlist.domain.usecase.FlowUseCase
import com.example.prodlist.ktutil.coroutine.DefaultDispatcher
import com.example.prodlist.ktutil.flow.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadProductsUseCase @Inject constructor(
    @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
    private val productRepository: ProductRepository,
) : FlowUseCase<LoadProductsUseCase.Params, List<Product>>(defaultDispatcher) {

    override fun execute(parameters: Params): Flow<Result<List<Product>>> {
        return when (parameters) {
            is Params.FilterByCategory -> productRepository.getFilteredProductsByCategory(parameters.categoryKey)
            is Params.OnlyFavorite -> productRepository.getFavoriteProducts()
            is Params.OnlySearchedFavorite -> productRepository.getSearchedFavoriteProducts(parameters.keyword)
        }
    }

    sealed interface Params {

        data class FilterByCategory(val categoryKey: String) : Params

        object OnlyFavorite : Params

        data class OnlySearchedFavorite(val keyword: String) : Params
    }
}
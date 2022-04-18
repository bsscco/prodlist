package com.example.prodlist.domain.category

import com.example.prodlist.domain.usecase.CoroutineUseCase
import com.example.prodlist.ktutil.coroutine.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class LoadCategoriesUseCase @Inject constructor(
    @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
    private val categoryRepository: CategoryRepository,
) : CoroutineUseCase<Unit, List<Category>>(defaultDispatcher) {

    override suspend fun execute(parameters: Unit): List<Category> {
        return categoryRepository.getCategories()
    }
}
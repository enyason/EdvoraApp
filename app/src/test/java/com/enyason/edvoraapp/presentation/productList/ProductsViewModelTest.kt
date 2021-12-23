package com.enyason.edvoraapp.presentation.productList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.enyason.edvoraapp.core.domain.Address
import com.enyason.edvoraapp.core.domain.ProductInformation
import com.enyason.edvoraapp.core.domain.usecase.GetProducts
import com.enyason.edvoraapp.presentation.utils.TestDispatcher
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ProductsViewModelTest {

    private val getProducts: GetProducts = mockk()

    private val dispatcher = TestDispatcher()

    lateinit var sut: ProductsViewModel

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        sut = ProductsViewModel(getProducts, dispatcher)
    }

    @Test
    fun `When getting products then verify get products is executed`() = runBlockingTest {

        sut.getProducts()

        coVerify { getProducts.execute() }
    }

    @Test
    fun `When getting products then verify get products is fetched successfully`() = runBlockingTest {

        val productList = listOf(testProduct)

        coEvery { getProducts.execute() } returns GetProducts.Result.Success(productList)

        val screenStateObserver = sut.viewState.observeWithMock()

        sut.getProducts()

        val slot = slot<ProductsViewModel.ViewState.ProductCategories>()

        verify {
            screenStateObserver.onChanged(capture(slot))
        }

        assertTrue(slot.captured.productCategories.isNotEmpty())
    }

    @Test
    fun `When getting products then verify loading states`() = runBlockingTest {

        val productList = listOf(testProduct)

        coEvery { getProducts.execute() } returns GetProducts.Result.Success(productList)

        val loadingStateObserver = sut.loading.observeWithMock()


        sut.getProducts()

        verify {
            loadingStateObserver.onChanged(true)
            loadingStateObserver.onChanged(false)
        }

    }

    @Test
    fun `When getting products fails then verify error state`() = runBlockingTest {

        coEvery { getProducts.execute() } returns GetProducts.Result.Error

        val viewStateObserver = sut.viewState.observeWithMock()

        sut.getProducts()

        verify {
            viewStateObserver.onChanged(ProductsViewModel.ViewState.Error)
        }

    }

    companion object {

        val testProduct = ProductInformation(
            address = Address("Uyo", "Akwa Ibom"),
            brandName = "Enyason",
            date = "2014-10-04T10:49:38.385Z",
            time = "2021-04-16T04:20:28.126Z",
            description = "This is a good product",
            image = "https://toppng.com/uploads/preview/sitemap-infos-transparent-i-phone-x-phone-in-hand-11563198189tafc9ocrkg.png",
            price = 300,
            productName = "Edvora"
        )
    }
}
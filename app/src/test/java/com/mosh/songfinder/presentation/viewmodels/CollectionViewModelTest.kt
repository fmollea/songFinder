package com.mosh.songfinder.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mosh.songfinder.data.repository.CollectionRepository
import com.mosh.songfinder.data.services.data.CollectionResponse
import com.mosh.songfinder.utils.TestContextProvider
import com.mosh.songfinder.utils.TestCoroutineRules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.Response
import java.lang.Error

@ExperimentalCoroutinesApi
@Suppress("UNCHECKED_CAST")
class CollectionViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRules()

    @Mock
    private lateinit var repository: CollectionRepository

    @Mock
    private lateinit var viewStateObserver: Observer<CollectionViewState>

    private lateinit var viewModel: CollectionViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = CollectionViewModel(
            repository,
            TestContextProvider()
        ).apply {
            getStateLiveData.observerForEver(viewStateObserver)
        }
    }

    @Test
    fun `test get collections from server success`() {
        testCoroutineRule.runBlockingTest {
            val data =  Response.success(mock(CollectionResponse::class.java))
            val idCollection = 1

            `when`(repository.getCollectionFromServer(idCollection)).thenReturn(data)
            viewModel.getCollectionsFromServer(idCollection)

            verify(viewStateObserver).onChanged(CollectionStateView.Loading)
            verify(viewStateObserver).onChanged(CollectionStateView.SuccessSong(data.body()))
        }
    }

    @Test
    fun `test get collections from server fail`() {
        testCoroutineRule.runBlockingTest {
            val error = Error()
            `when`(repository.getCollectionsFromServer(idCollection)).thenThrow(error)
            viewModel.getCollectionsFromServer(idCollection)

            verify(viewStateObserver).onChanged(CollectionStateView.Loading)
            verify(viewStateObserver).onChanged(CollectionStateView.Error(error))
        }
    }
}
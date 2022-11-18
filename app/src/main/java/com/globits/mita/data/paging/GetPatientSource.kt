package com.globits.mita.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.globits.mita.data.model.Patient
import com.globits.mita.data.model.PatientFilter
import com.globits.mita.data.network.TestApi

class GetPatientSource(
    private val testApi: TestApi,
    private val status: Int
) : PagingSource<Int, Patient>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Patient> {
        val currentPage = params.key ?: 1
        return try {
            val response = testApi.getPatient(PatientFilter("", currentPage, 4, status))
            val endOfPaginationReached = response.content.isEmpty()
            if (response.content.isNotEmpty()) {
                LoadResult.Page(
                    data = response.content,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (endOfPaginationReached) null else currentPage + 1
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Patient>): Int? {
        return state.anchorPosition
    }

}


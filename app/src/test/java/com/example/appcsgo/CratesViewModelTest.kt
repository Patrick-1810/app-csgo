package com.example.appcsgo.ui.crates

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.appcsgo.data.model.Crate
import com.example.appcsgo.data.repository.FakeCratesRepository
import com.example.appcsgo.utils.MainDispatcherRule
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class CratesViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val listaFake = listOf(
        Crate("1", "Alpha Case", null, null, null),
        Crate("2", "Bravo Case", null, null, null),
        Crate("3", "Gamma Case", null, null, null),
        Crate("4", null, null, null, null)
    )

    private fun criarVM() = CratesViewModel(
        FakeCratesRepository(listaFake)
    )

    @Test
    fun `filterCrates retorna matches corretos`() = runTest {
        val vm = criarVM()

        vm.fetchCrates()
        advanceUntilIdle()

        vm.filterCrates("alpha")

        assertEquals(1, vm.filteredCrates.value?.size)
        assertEquals("Alpha Case", vm.filteredCrates.value?.first()?.name)
    }

    @Test
    fun `filterCrates ignora nomes nulos`() = runTest {
        val vm = criarVM()

        vm.fetchCrates()
        advanceUntilIdle()

        vm.filterCrates("case")

        assertEquals(3, vm.filteredCrates.value?.size)
    }

    @Test
    fun `filterCrates retorna vazio quando nao encontra`() = runTest {
        val vm = criarVM()

        vm.fetchCrates()
        advanceUntilIdle()

        vm.filterCrates("nada")

        assertEquals(0, vm.filteredCrates.value?.size)
    }

    @Test
    fun `fetchCrates retorna todos os itens`() = runTest {
        val vm = criarVM()

        vm.fetchCrates()
        advanceUntilIdle()

        assertEquals(4, vm.crates.value?.size)
    }
}

package com.teste_x_brain.teste_x_brain.service.Impl;

import com.teste_x_brain.teste_x_brain.dto.VendaRequestDTO;
import com.teste_x_brain.teste_x_brain.dto.VendaResponseDTO;
import com.teste_x_brain.teste_x_brain.dto.VendedorResumoResponseDTO;
import com.teste_x_brain.teste_x_brain.dto.mappers.VendaMapper;
import com.teste_x_brain.teste_x_brain.model.Venda;
import com.teste_x_brain.teste_x_brain.model.Vendedor;
import com.teste_x_brain.teste_x_brain.repository.VendaRepository;
import com.teste_x_brain.teste_x_brain.repository.VendedorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VendaServiceImplTest {

    @InjectMocks
    private VendaServiceImpl vendaService;

    @Mock
    private VendaRepository vendaRepository;
    @Mock
    private VendedorRepository vendedorRepository;
    @Mock
    private VendaMapper vendaMapper;

    private Vendedor vendedor;
    private Venda venda;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        vendedor = new Vendedor();
        vendedor.setId(1L);
        vendedor.setNome("Matheus");

        venda = new Venda();
        venda.setVendedor(vendedor);
        venda.setValor(new BigDecimal("100.00"));
        venda.setDataVenda(LocalDate.of(2025, 10, 13));
    }

    // -------------------------------
    // TESTE 1: criarVenda
    // -------------------------------
    @Test
    void deveCriarVendaComSucesso() {
        VendaRequestDTO request = mock(VendaRequestDTO.class);
        when(request.getVendedorId()).thenReturn(1L);
        when(request.getVendedorNome()).thenReturn("Matheus");

        when(vendedorRepository.findById(1L)).thenReturn(Optional.of(vendedor));
        when(vendaMapper.toEntity(request, vendedor)).thenReturn(venda);
        when(vendaRepository.save(venda)).thenReturn(venda);

        VendaResponseDTO responseMock = new VendaResponseDTO(UUID.randomUUID(),LocalDate.now(), new BigDecimal("100.00"),1L, "Matheus");
        when(vendaMapper.toDto(venda)).thenReturn(responseMock);

        VendaResponseDTO response = vendaService.criarVenda(request);

        assertNotNull(response);
        assertEquals("Matheus", response.vendedorNome());
        verify(vendaRepository, times(1)).save(venda);
    }

    // -------------------------------
    // TESTE 2: findVendedor (novo vendedor)
    // -------------------------------
    @Test
    void deveCriarNovoVendedorQuandoNaoExiste() {
        when(vendedorRepository.findById(1L)).thenReturn(Optional.empty());
        when(vendedorRepository.save(any(Vendedor.class))).thenReturn(vendedor);

        Vendedor result = vendaService.findVendedor(1L, "Matheus");

        assertEquals("Matheus", result.getNome());
        verify(vendedorRepository, times(1)).save(any(Vendedor.class));
    }

    // -------------------------------
    // TESTE 3: findVendedor (existente)
    // -------------------------------
    @Test
    void deveRetornarVendedorExistente() {
        when(vendedorRepository.findById(1L)).thenReturn(Optional.of(vendedor));

        Vendedor result = vendaService.findVendedor(1L, "Matheus");

        assertEquals(vendedor, result);
        verify(vendedorRepository, never()).save(any());
    }

    // -------------------------------
    // TESTE 4: listarVendas (cálculo de resumo)
    // -------------------------------
    @Test
    void deveListarResumoDeVendasCorretamente() {
        LocalDate inicio = LocalDate.of(2025, 10, 1);
        LocalDate fim = LocalDate.of(2025, 10, 3);

        Venda venda1 = new Venda();
        venda1.setValor(new BigDecimal("100"));
        venda1.setDataVenda(LocalDate.of(2025, 10, 1));
        venda1.setVendedor(vendedor);

        Venda venda2 = new Venda();
        venda2.setValor(new BigDecimal("200"));
        venda2.setDataVenda(LocalDate.of(2025, 10, 2));
        venda2.setVendedor(vendedor);

        when(vendaRepository.findAllByDataVendaBetween(inicio, fim))
                .thenReturn(List.of(venda1, venda2));

        List<VendedorResumoResponseDTO> result = vendaService.listarVendas(inicio, fim);

        assertEquals(1, result.size());
        VendedorResumoResponseDTO resumo = result.getFirst();

        assertEquals("Matheus", resumo.nome());
        assertEquals(new BigDecimal("300"), resumo.totalVendas());
        assertTrue(resumo.mediaDiaria().compareTo(BigDecimal.ZERO) > 0);
    }
}

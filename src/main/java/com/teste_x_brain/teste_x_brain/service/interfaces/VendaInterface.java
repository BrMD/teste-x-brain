package com.teste_x_brain.teste_x_brain.service.interfaces;

import com.teste_x_brain.teste_x_brain.dto.VendaRequestDTO;
import com.teste_x_brain.teste_x_brain.dto.VendaResponseDTO;
import com.teste_x_brain.teste_x_brain.dto.VendedorResumoResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface VendaInterface {
    VendaResponseDTO criarVenda(VendaRequestDTO request);
    List<VendedorResumoResponseDTO> listarVendas(LocalDate inicio, LocalDate fim);
}

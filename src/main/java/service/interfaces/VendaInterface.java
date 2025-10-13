package service.interfaces;

import dto.VendaRequestDTO;
import dto.VendaResponseDTO;
import dto.VendedorResumoResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface VendaInterface {
    VendaResponseDTO criarVenda(VendaRequestDTO request);
    List<VendedorResumoResponseDTO> listarVendas(LocalDate inicio, LocalDate fim);
}

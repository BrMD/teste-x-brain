package service.Impl;

import dto.VendaRequestDTO;
import dto.VendaResponseDTO;
import dto.VendedorResumoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.VendaRepository;
import repository.VendedorRepository;
import service.interfaces.VendaInterface;

import java.time.LocalDate;
import java.util.List;

@Service
public class VendaServiceImpl implements VendaInterface {

    @Autowired
    private VendaRepository vendaRepository;
    @Autowired
    private VendedorRepository vendedorRepository;

    @Override
    public VendaResponseDTO criarVenda(VendaRequestDTO request) {
        //TODO fazer implementação
        return null;
    }

    @Override
    public List<VendedorResumoResponseDTO> listarVendas(LocalDate inicio, LocalDate fim) {
        //TODO fazer implementação
        return List.of();
    }
}

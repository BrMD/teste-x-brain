package service.Impl;

import dto.VendaRequestDTO;
import dto.VendaResponseDTO;
import dto.VendedorResumoResponseDTO;
import dto.mappers.VendaMapper;
import model.Venda;
import model.Vendedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.VendaRepository;
import repository.VendedorRepository;
import service.interfaces.VendaInterface;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VendaServiceImpl implements VendaInterface {

    @Autowired
    private VendaRepository vendaRepository;
    @Autowired
    private VendedorRepository vendedorRepository;
    @Autowired
    private VendaMapper vendaMapper;

    @Override
    public VendaResponseDTO criarVenda(VendaRequestDTO request) {
        Vendedor vendedor = findVendedor(request.getVendedorId(), request.getVendedorNome());

        Venda venda = vendaMapper.toEntity(request, vendedor);
        vendaRepository.save(venda);
        return vendaMapper.toDto(venda);
    }

    @Override
    public List<VendedorResumoResponseDTO> listarVendas(LocalDate inicio, LocalDate fim) {
        //TODO fazer implementação
        return List.of();
    }

    public Vendedor findVendedor(Long id, String nome){
        Vendedor vendedor = new Vendedor();
        vendedor.setId(id);
        vendedor.setNome(nome);

        Optional<Vendedor> foundVendedor = vendedorRepository.findById(id);

        if(foundVendedor.isEmpty()){
            vendedorRepository.save(vendedor);
            return vendedor;
        }
        return foundVendedor.get();
    }
}

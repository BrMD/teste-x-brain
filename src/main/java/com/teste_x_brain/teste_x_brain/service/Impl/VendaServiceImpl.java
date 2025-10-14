package com.teste_x_brain.teste_x_brain.service.Impl;

import com.teste_x_brain.teste_x_brain.dto.VendaRequestDTO;
import com.teste_x_brain.teste_x_brain.dto.VendaResponseDTO;
import com.teste_x_brain.teste_x_brain.dto.VendedorResumoResponseDTO;
import com.teste_x_brain.teste_x_brain.dto.mappers.VendaMapper;
import com.teste_x_brain.teste_x_brain.model.Venda;
import com.teste_x_brain.teste_x_brain.model.Vendedor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.teste_x_brain.teste_x_brain.repository.VendaRepository;
import com.teste_x_brain.teste_x_brain.repository.VendedorRepository;
import com.teste_x_brain.teste_x_brain.service.interfaces.VendaInterface;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
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
        log.info("antes do save venda");
        vendaRepository.save(venda);
        log.info("depois do save venda");
        return vendaMapper.toDto(venda);
    }

    @Override
    public List<VendedorResumoResponseDTO> listarVendas(LocalDate inicio, LocalDate fim) {
        log.info("query findallVendas");
        List<Venda> listaVendas = vendaRepository.findAllByDataVendaBetween(inicio,fim);
        log.info("fim query findallVendas");

        Map<Long, List<Venda>> vendasSorted = listaVendas.stream().collect(Collectors.groupingBy(venda -> venda.getVendedor().getId()));

        return vendasSorted.values().stream().map(vendas -> {

            String nome = vendas.getFirst().getVendedor().getNome();
            Long id = vendas.getFirst().getVendedor().getId();
            Optional<BigDecimal> valorTotalDeVendasOptional = vendas.stream().map(Venda::getValor).reduce((valor, acc) -> acc.add(valor));
            Long totalDias = ChronoUnit.DAYS.between(inicio,fim);
            return valorTotalDeVendasOptional.map(bigDecimal ->
                    new VendedorResumoResponseDTO(id, nome, bigDecimal, bigDecimal.divide(new BigDecimal(totalDias),RoundingMode.HALF_UP))).
                    orElseGet(() -> new VendedorResumoResponseDTO(id, nome, new BigDecimal(0), new BigDecimal(0)));
        }).toList();
    }

    public Vendedor findVendedor(Long id, String nome){
        Vendedor vendedor = new Vendedor();
        vendedor.setId(id);
        vendedor.setNome(nome);

        Optional<Vendedor> foundVendedor = vendedorRepository.findById(id);

        if(foundVendedor.isEmpty()){
            log.info("antes do save vendedor");
            vendedorRepository.save(vendedor);
            log.info("depois do save vendedor");
            return vendedor;
        }
        return foundVendedor.get();
    }
}

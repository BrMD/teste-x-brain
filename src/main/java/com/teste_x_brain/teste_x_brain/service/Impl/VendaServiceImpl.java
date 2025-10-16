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
        log.info("[{}] Iniciando criacao de venda - Data: {}, Valor: {}, Vendedor ID: {}, Nome: {}",
                "Criar Venda",request.getDataVenda(), request.getValor(), request.getVendedorId(), request.getVendedorNome());

        log.debug("[{}] Buscando Vendedor ou Criando","Criar Venda");
        Vendedor vendedor = findVendedorOrCreate(request.getVendedorId(), request.getVendedorNome());
        log.debug("[{}] Vendedor criado ou encontrado - Vendedor ID: {}, Nome: {}",
                "Criar Venda", vendedor.getId(), vendedor.getNome());

        log.debug("[{}] Convertendo DTO par entidade Venda","Criar Venda");
        Venda venda = vendaMapper.toEntity(request, vendedor);
        log.debug("[{}] DTO convertida com sucesso","Criar Venda");

        log.debug("[{}] Salvar Venda no banco de dados","Criar Venda");
        vendaRepository.save(venda);
        log.info("[{}] Venda salva com sucesso", "Criar Venda");
        return vendaMapper.toDto(venda);
    }

    @Override
    public List<VendedorResumoResponseDTO> listarVendas(LocalDate inicio, LocalDate fim) {
        log.info("[{}] Inicio metodo listar vendas","Listar Vendas");

        log.debug("[{}] Execucao da query findAllByDataVendaBetween do repositorio Venda, periodo inicial:{}, periodo final:{}","Listar Venda", inicio, fim);
        List<Venda> listaVendas = vendaRepository.findAllByDataVendaBetween(inicio,fim);
        log.debug("[{}] Execucao com sucesso da query findAllByDataVendaBetween registros encontrados: {}", "Listar Vendas", listaVendas.size());


        log.debug("[{}] Execucao da montagem dos arrays com key cada vendedor será separado com suas respectivas vendas com a key sendo a ID ex: 1[array de vendas]", "Listar Vendas");
        Map<Long, List<Venda>> vendasSorted = listaVendas.stream().collect(Collectors.groupingBy(venda -> venda.getVendedor().getId()));
        log.debug("[{}] fim da criacao dos arrays dos vendedores", "Listar Vendas");

        log.debug("[{}] Execucao da montagem do map dos array de cada vendedor", "Listar Vendas");
        return vendasSorted.values().stream().map(vendas -> {
            log.debug("[{}] Inicio da execucao do retorno do map das vendas", "Listar Vendas");
            String nome = vendas.getFirst().getVendedor().getNome();
            Long id = vendas.getFirst().getVendedor().getId();
            log.debug("[{}] VendedorID: {} VendedorNome: {}", "Listar Vendas",id,nome);

            log.debug("[{}] Inicio da soma total de vendas com reduce das vendas do vendedor", "Listar Vendas");
            BigDecimal valorTotalDeVendas = vendas.stream()
                    .map(Venda::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            Long totalDias = ChronoUnit.DAYS.between(inicio,fim);

            log.info("[{}] Informacoes a serem retornadas do vendedor, ID:{}, Nome:{}, TotalDeVendas:{}, MediaDiaria:{}", "Listar Vendas",
                    id, nome, valorTotalDeVendas,valorTotalDeVendas.divide(new BigDecimal(totalDias), RoundingMode.HALF_UP));
            return new VendedorResumoResponseDTO(id, nome, valorTotalDeVendas, valorTotalDeVendas.divide(new BigDecimal(totalDias), RoundingMode.HALF_UP));
        }).toList();
    }

    public Vendedor findVendedorOrCreate(Long id, String nome){
        log.info("[{}]","findVendedorOrCreate");

        log.debug("[{}] Instanciado entidade Vendedor","findVendedorOrCreate");
        Vendedor vendedor = new Vendedor();
        vendedor.setId(id);
        vendedor.setNome(nome);
        log.debug("[{}] Entidade Vendedor Instanciada com sucesso - VendedorId: {}, VendedorNome: {}","findVendedorOrCreate", vendedor.getId(), vendedor.getNome());

        log.debug("[{}] Executar query para achar vendedor findById - ID:{}","findVendedorOrCreate", vendedor.getId());
        Optional<Vendedor> foundVendedor = vendedorRepository.findById(id);

        log.debug("[{}] Verificacao para saber se existe um vendedor com essa ID ja cadastrado","findVendedorOrCreate");
        if(foundVendedor.isEmpty()){
            log.debug("[{}] Vendedor com id:{} nao encontrado, e sera cadastrado","findVendedorOrCreate", vendedor.getId());
            vendedorRepository.save(vendedor);
            log.info("[{}] Vendedor nao encontrado e cadastrado com sucesso","findVendedorOrCreate");
            return vendedor;
        }
        log.info("[{}] Vendedor cadastrado e sendo retornado com sucesso","findVendedorOrCreate");
        return foundVendedor.get();
    }
}

package com.teste_x_brain.teste_x_brain.repository;

import com.teste_x_brain.teste_x_brain.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface VendaRepository extends JpaRepository<Venda, UUID> {
    List<Venda> findAllByDataVendaBetween(LocalDate inicio, LocalDate fim);
}

package com.teste_x_brain.teste_x_brain.repository;

import com.teste_x_brain.teste_x_brain.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VendaRepository extends JpaRepository<Venda, UUID> {
}

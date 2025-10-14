package com.teste_x_brain.teste_x_brain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "dataVenda", nullable = false)
    private LocalDate dataVenda;

    @Column(name = "valorVenda", nullable = false)
    private BigDecimal valor;

    @ManyToOne
    @JoinColumn(name = "vendedor_id", nullable = false)
    private Vendedor vendedor;

    public Venda(LocalDate dataVenda, BigDecimal valor, Vendedor vendedor) {
        this.dataVenda = dataVenda;
        this.valor = valor;
        this.vendedor = vendedor;
    }
}

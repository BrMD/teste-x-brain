package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "dataVenda", nullable = false)
    private LocalDateTime dataVenda;

    @Column(name = "valorVenda", nullable = false)
    private BigDecimal valor;

    @ManyToMany
    @JoinColumn(name = "vendedor_id")
    private Vendedor vendedor;
}

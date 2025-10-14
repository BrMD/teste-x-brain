package com.teste_x_brain.teste_x_brain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Vendedor {
    @Id
    private Long id;

    @Column(nullable = false, name = "nomeVendedor")
    private String nome;
}

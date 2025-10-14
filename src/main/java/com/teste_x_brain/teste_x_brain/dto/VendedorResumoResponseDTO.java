package com.teste_x_brain.teste_x_brain.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record VendedorResumoResponseDTO(
    Long id,
    String nome,
    BigDecimal totalVendas,
    BigDecimal mediaDiaria
){}

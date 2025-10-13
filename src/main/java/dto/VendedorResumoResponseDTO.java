package dto;

import java.math.BigDecimal;
import java.util.UUID;

public record VendedorResumoResponseDTO(
    UUID id,
    String nome,
    BigDecimal totalVendas,
    BigDecimal mediaDiaria
){}

package dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record VendaResponseDTO(
        UUID id,
        LocalDate dataVenda,
        BigDecimal valor,
        Long vendedorId,
        String vendedorNome
) {}
package dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class VendaRequestDTO {
    @NotNull(message = "Data não pode ser nulo")
    private LocalDate dataVenda;

    @NotNull(message = "Valor não pode ser nulo")
    @Positive(message = "Valor precisa ser positivo")
    private BigDecimal valor;

    @NotNull(message = "VendedorID não pode ser nulo")
    @Positive(message = "VendedorID precisa ser positivo")
    private Long vendedorId;

    @NotNull(message = "Nome do vendedor não pode ser nulo")
    @NotBlank(message = "Nome do vendedor não pode ser vazio")
    private String vendedorNome;
}

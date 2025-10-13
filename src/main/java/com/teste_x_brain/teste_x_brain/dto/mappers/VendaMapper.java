package com.teste_x_brain.teste_x_brain.dto.mappers;

import com.teste_x_brain.teste_x_brain.dto.VendaRequestDTO;
import com.teste_x_brain.teste_x_brain.dto.VendaResponseDTO;
import com.teste_x_brain.teste_x_brain.model.Venda;
import com.teste_x_brain.teste_x_brain.model.Vendedor;
import org.springframework.stereotype.Component;

@Component
public class VendaMapper {
    public VendaResponseDTO toDto(Venda venda){
        if(venda==null) return null;
        return new VendaResponseDTO(venda.getId(), venda.getDataVenda(), venda.getValor(),venda.getVendedor().getId(), venda.getVendedor().getNome());
    }

    public Venda toEntity(VendaRequestDTO vendaRequestDTO, Vendedor vendedor){
        if(vendaRequestDTO == null) return null;
        return new Venda(vendaRequestDTO.getDataVenda(), vendaRequestDTO.getValor(), vendedor);
    }
}

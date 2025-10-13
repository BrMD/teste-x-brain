package dto.mappers;

import dto.VendaRequestDTO;
import dto.VendaResponseDTO;
import model.Venda;
import model.Vendedor;

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

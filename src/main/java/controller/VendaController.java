package controller;

import dto.VendaRequestDTO;
import dto.VendaResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.Impl.VendaServiceImpl;

@RestController
@RequestMapping("/api")
public class VendaController {
    @Autowired
    private VendaServiceImpl vendaService;

    @PostMapping("/vendas")
    public ResponseEntity<VendaResponseDTO> createVenda(@Valid @RequestBody VendaRequestDTO request){
        VendaResponseDTO vendaResponseDTO = vendaService.criarVenda(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(vendaResponseDTO);
    }
}

package com.teste_x_brain.teste_x_brain.controller;

import com.teste_x_brain.teste_x_brain.dto.VendaRequestDTO;
import com.teste_x_brain.teste_x_brain.dto.VendaResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.teste_x_brain.teste_x_brain.service.Impl.VendaServiceImpl;

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

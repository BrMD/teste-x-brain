package com.teste_x_brain.teste_x_brain.controller;

import com.teste_x_brain.teste_x_brain.dto.VendaRequestDTO;
import com.teste_x_brain.teste_x_brain.dto.VendaResponseDTO;
import com.teste_x_brain.teste_x_brain.dto.VendedorResumoResponseDTO;
import com.teste_x_brain.teste_x_brain.exceptions.DataInvalidaException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.teste_x_brain.teste_x_brain.service.Impl.VendaServiceImpl;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/vendas")
public class VendaController {
    @Autowired
    private VendaServiceImpl vendaService;

    @PostMapping()
    public ResponseEntity<VendaResponseDTO> createVenda(@Valid @RequestBody VendaRequestDTO request){
        VendaResponseDTO vendaResponseDTO = vendaService.criarVenda(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(vendaResponseDTO);
    }

    @GetMapping()
    public ResponseEntity<List<VendedorResumoResponseDTO>> getVendas(
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate inicio,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate fim) {
        if (inicio.isAfter(fim)) {
            throw new DataInvalidaException("Data de início deve ser anterior à data de fim");
        }
        List<VendedorResumoResponseDTO> listaVendas = vendaService.listarVendas(inicio, fim);
        return ResponseEntity.status(HttpStatus.OK).body(listaVendas);
    }
}

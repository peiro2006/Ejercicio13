package com.prog4.ej10.controllers;

import com.prog4.ej10.dtos.SocioCreateRequestDto;
import com.prog4.ej10.services.SocioCreateService;
import com.prog4.ej10.dtos.SocioResponseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/socios")
@AllArgsConstructor
public class SocioPostController {

    private final SocioCreateService socioCreateService;

    @PostMapping
    public ResponseEntity<SocioResponseDto> crear(@Valid @RequestBody SocioCreateRequestDto dto) {

        SocioResponseDto creado = socioCreateService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }
}

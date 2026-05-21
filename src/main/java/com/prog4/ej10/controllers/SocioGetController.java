package com.prog4.ej10.controllers;

import com.prog4.ej10.dtos.SocioResponseDto;
import com.prog4.ej10.services.SocioGetService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/socios")
public class SocioGetController {
    private final SocioGetService socioGetService;

    @GetMapping
    public ResponseEntity<List<SocioResponseDto>> all() {
        return ResponseEntity.ok(
                socioGetService.all()
        );
    }
}

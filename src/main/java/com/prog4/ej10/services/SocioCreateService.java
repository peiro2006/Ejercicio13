package com.prog4.ej10.services;

import com.prog4.ej10.*;
import com.prog4.ej10.dtos.SocioCreateRequestDto;
import com.prog4.ej10.dtos.SocioResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class SocioCreateService {

    private final ISocioRepository socioRepository;

    @Transactional
    public SocioResponseDto crear(SocioCreateRequestDto dto) {
        Socio socio = SocioMapper.toEntity(dto);
        Socio guardado = socioRepository.save(socio);
        return SocioMapper.toResponseDto(guardado);
    }
}

package com.prog4.ej10.services;

import com.prog4.ej10.ISocioRepository;
import com.prog4.ej10.SocioMapper;
import com.prog4.ej10.dtos.SocioResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class SocioGetService {
    private final ISocioRepository socioRepository;

    @Transactional(readOnly = true)
    public List<SocioResponseDto> all() {
        return StreamSupport.stream(socioRepository.findAll().spliterator(), false)
                .map(SocioMapper::toResponseDto).toList();
    }
}

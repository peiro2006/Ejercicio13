package com.prog4.ej10;

import com.prog4.ej10.dtos.SocioCreateRequestDto;
import com.prog4.ej10.dtos.SocioResponseDto;
import org.springframework.stereotype.Component;

@Component
public class SocioMapper {
    public static Socio toEntity(SocioCreateRequestDto dto) {
        Socio socio = new Socio();
        socio.setNombre(dto.getNombre());
        socio.setApellido(dto.getApellido());
        return socio;
    }

    public static SocioResponseDto toResponseDto(Socio socio) {
        SocioResponseDto dto = new SocioResponseDto();
        dto.setId(socio.getId());
        dto.setNombre(socio.getNombre());
        dto.setApellido(socio.getApellido());
        return dto;
    }
}

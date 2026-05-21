package com.prog4.ej10.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class SocioResponseDto {

    private Long id;
    private String nombre;
    private String apellido;
}

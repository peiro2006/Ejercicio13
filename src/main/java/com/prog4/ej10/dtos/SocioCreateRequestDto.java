package com.prog4.ej10.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
public class SocioCreateRequestDto {

    @NotBlank
    private String nombre;
    @NotBlank
    private String apellido;
}

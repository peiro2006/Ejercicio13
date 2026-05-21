package com.prog4.ej10;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table (name = "socios")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Socio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String nombre;
    String apellido;
}

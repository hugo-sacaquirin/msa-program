package com.sipeip.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "programas")
@Builder // Lombok annotation to generate a builder for the class
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_programa")
    private Integer id;

    @Column(name = "nombre", length = 100, nullable = false, unique = true)
    private String name;

    @Column(name = "descripcion", length = 100)
    private String description;

    @Column(name = "alcance", length = 100)
    private String scope;

    @Column(name = "estado", length = 10, nullable = false)
    private String status;

    @Column(name = "responsable", length = 100, nullable = false)
    private String responsible;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "fecha_actualizacion", nullable = false)
    private LocalDateTime updatedAt;

}

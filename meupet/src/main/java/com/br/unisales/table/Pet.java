package com.br.unisales.table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pet")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", nullable = false, length = 150)
    private String nome;

    @Column(name = "peso", nullable = false)
    private Double peso;

    @Column(name = "idade", nullable = false)
    private Integer idade;

    @Column(name = "sexo", nullable = false, length = 1)
    private String sexo;

    @Column(name = "especie", nullable = false, length=100)
    private String especie;

    @Column(name = "raca", nullable = false, length = 50)
    private String raca;

    @Column(name = "proprietario", nullable = false, length = 150)
    private String proprietario;

    @Column(name = "id_proprietario")
    private Integer idProprietario;
    
}
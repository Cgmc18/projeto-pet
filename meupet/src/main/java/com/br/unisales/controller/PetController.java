package com.br.unisales.controller;

import java.util.ArrayList;
import java.util.List;

import com.br.unisales.configuration.ConfigurationManager;
import com.br.unisales.table.Pet;

import jakarta.persistence.TypedQuery;

public class PetController {

    private final ConfigurationManager config;

    public PetController() {
        this.config = new ConfigurationManager();
    }

    public Pet salvar(Integer id, String nome, Double peso, Integer idade, String sexo, String especie, String raca, String proprietario , Integer idProprietario) {
        Integer idPro = idProprietario;
        if(id!=null) {
            Pet petOld = this.buscarPorId(id);
            idPro = petOld.getIdProprietario();
        }
        
        Pet pet = Pet.builder()
                     .id(id)
                     .nome(nome)
                     .peso(peso)
                     .idade(idade)
                     .sexo(sexo)
                     .especie(especie)
                     .raca(raca)
                     .proprietario(proprietario)
                     .idProprietario(idPro)
                     .build();
        this.config.getEntityManager().getTransaction().begin();
        if(id == null) {
            this.config.getEntityManager().persist(pet);
        } else {
            this.config.getEntityManager().merge(pet);
        }

        this.config.getEntityManager().getTransaction().commit();
        return pet;
    }

    public Pet buscarPorId(Integer id) {
        try {
            TypedQuery<Pet> query = this.config.getEntityManager().createQuery("FROM Pet WHERE id = :id", Pet.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            return null;
        }
    }

    public List<Pet> listar() {
                try {
            TypedQuery<Pet> query = this.config.getEntityManager().createQuery("FROM Pet ORDER BY nome", Pet.class);
            return query.getResultList();
        } catch (Exception e) {          
            System.err.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    public String excluir(Integer id) {
        if(id != null) {
            Pet pet = this.buscarPorId(id);
            if (pet != null) {
                this.config.getEntityManager().getTransaction().begin();
                this.config.getEntityManager().remove(pet);
                this.config.getEntityManager().getTransaction().commit();
                return "excluido";
            }
        }
        
        return "erro";
    }


}

package com.example.transportapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.transportapp.model.Carro;

/** Capa de acceso a datos de Carro; Spring Data deriva las consultas del nombre de cada método. */
@Repository
public interface CarroRepository extends JpaRepository<Carro, Long> {

    boolean existsByPlaca(String placa);

    boolean existsByPlacaAndIdNot(String placa, Long id);

    List<Carro> findByPlacaContainingIgnoreCaseOrMarcaContainingIgnoreCase(String placa, String marca);
}

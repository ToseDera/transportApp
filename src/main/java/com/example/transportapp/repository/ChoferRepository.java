package com.example.transportapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.transportapp.model.Chofer;

/** Capa de acceso a datos de Chofer; Spring Data deriva las consultas del nombre de cada método. */
@Repository
public interface ChoferRepository extends JpaRepository<Chofer, Long> {

    boolean existsByDocumento(String documento);

    boolean existsByDocumentoAndIdNot(String documento, Long id);

    List<Chofer> findByNombresContainingIgnoreCaseOrDocumentoContainingIgnoreCase(String nombres, String documento);
}

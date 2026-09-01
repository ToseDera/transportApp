package com.example.transportapp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.transportapp.exception.RecursoNoEncontradoException;
import com.example.transportapp.model.Chofer;
import com.example.transportapp.repository.ChoferRepository;

/** Lógica de negocio de Chofer: normaliza el documento, valida unicidad y coordina el repositorio. */
@Service
@Transactional(readOnly = true)
public class ChoferService {

    private final ChoferRepository choferRepository;

    public ChoferService(ChoferRepository choferRepository) {
        this.choferRepository = choferRepository;
    }

    public List<Chofer> listar() {
        return choferRepository.findAll();
    }

    public List<Chofer> buscar(String termino) {
        if (termino == null || termino.isBlank()) {
            return listar();
        }
        return choferRepository.findByNombresContainingIgnoreCaseOrDocumentoContainingIgnoreCase(termino, termino);
    }

    public Chofer obtenerPorId(Long id) {
        return choferRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el chofer con id " + id));
    }

    @Transactional
    public Chofer guardar(Chofer chofer) {
        chofer.setDocumento(chofer.getDocumento() == null ? null : chofer.getDocumento().replace(" ", "").trim());
        boolean duplicado = chofer.getId() == null
                ? choferRepository.existsByDocumento(chofer.getDocumento())
                : choferRepository.existsByDocumentoAndIdNot(chofer.getDocumento(), chofer.getId());
        if (duplicado) {
            throw new IllegalArgumentException("Ya existe un chofer registrado con el documento " + chofer.getDocumento());
        }
        return choferRepository.save(chofer);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!choferRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("No se encontró el chofer con id " + id);
        }
        choferRepository.deleteById(id);
    }

    public long contar() {
        return choferRepository.count();
    }
}

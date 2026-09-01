package com.example.transportapp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.transportapp.exception.RecursoNoEncontradoException;
import com.example.transportapp.model.Carro;
import com.example.transportapp.repository.CarroRepository;

/** Lógica de negocio de Carro: normaliza la placa, valida unicidad y coordina el repositorio. */
@Service
@Transactional(readOnly = true)
public class CarroService {

    private final CarroRepository carroRepository;

    public CarroService(CarroRepository carroRepository) {
        this.carroRepository = carroRepository;
    }

    public List<Carro> listar() {
        return carroRepository.findAll();
    }

    public List<Carro> buscar(String termino) {
        if (termino == null || termino.isBlank()) {
            return listar();
        }
        return carroRepository.findByPlacaContainingIgnoreCaseOrMarcaContainingIgnoreCase(termino, termino);
    }

    public Carro obtenerPorId(Long id) {
        return carroRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el carro con id " + id));
    }

    @Transactional
    public Carro guardar(Carro carro) {
        carro.setPlaca(carro.getPlaca() == null ? null : carro.getPlaca().replace(" ", "").toUpperCase());
        boolean duplicado = carro.getId() == null
                ? carroRepository.existsByPlaca(carro.getPlaca())
                : carroRepository.existsByPlacaAndIdNot(carro.getPlaca(), carro.getId());
        if (duplicado) {
            throw new IllegalArgumentException("Ya existe un carro registrado con la placa " + carro.getPlaca());
        }
        return carroRepository.save(carro);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!carroRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("No se encontró el carro con id " + id);
        }
        carroRepository.deleteById(id);
    }

    public long contar() {
        return carroRepository.count();
    }
}

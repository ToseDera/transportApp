package com.example.transportapp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.transportapp.exception.RecursoNoEncontradoException;
import com.example.transportapp.model.Usuario;
import com.example.transportapp.repository.UsuarioRepository;

/** Lógica de negocio de Usuario: normaliza datos, valida unicidad y coordina el repositorio. */
@Service
@Transactional(readOnly = true)
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public List<Usuario> buscar(String termino) {
        if (termino == null || termino.isBlank()) {
            return listar();
        }
        return usuarioRepository.findByNombresContainingIgnoreCaseOrApellidosContainingIgnoreCase(termino, termino);
    }

    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el usuario con id " + id));
    }

    @Transactional
    public Usuario guardar(Usuario usuario) {
        usuario.setEmail(usuario.getEmail() == null ? null : usuario.getEmail().trim().toLowerCase());
        boolean duplicado = usuario.getId() == null
                ? usuarioRepository.existsByEmail(usuario.getEmail())
                : usuarioRepository.existsByEmailAndIdNot(usuario.getEmail(), usuario.getId());
        if (duplicado) {
            throw new IllegalArgumentException("Ya existe un usuario registrado con el correo " + usuario.getEmail());
        }
        if (usuario.getId() != null) {
            usuario.setFechaRegistro(obtenerPorId(usuario.getId()).getFechaRegistro());
        }
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("No se encontró el usuario con id " + id);
        }
        usuarioRepository.deleteById(id);
    }

    public long contar() {
        return usuarioRepository.count();
    }
}

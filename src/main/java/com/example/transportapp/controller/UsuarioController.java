package com.example.transportapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.transportapp.model.Usuario;
import com.example.transportapp.service.UsuarioService;

import jakarta.validation.Valid;

/** Controlador del MVC: traduce las peticiones HTTP de usuarios en llamadas al servicio y vistas. */
@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) String q, Model model) {
        model.addAttribute("titulo", "Usuarios");
        model.addAttribute("usuarios", usuarioService.buscar(q));
        model.addAttribute("q", q);
        return "usuarios/lista";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("titulo", "Nuevo usuario");
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("esEdicion", false);
        return "usuarios/formulario";
    }

    @PostMapping
    public String guardar(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult result,
            Model model, RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Nuevo usuario");
            model.addAttribute("esEdicion", false);
            return "usuarios/formulario";
        }
        try {
            usuarioService.guardar(usuario);
        } catch (IllegalArgumentException ex) {
            result.rejectValue("email", "duplicado", ex.getMessage());
            model.addAttribute("titulo", "Nuevo usuario");
            model.addAttribute("esEdicion", false);
            return "usuarios/formulario";
        }
        flash.addFlashAttribute("exito", "Usuario registrado correctamente");
        return "redirect:/usuarios";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        model.addAttribute("titulo", "Detalle del usuario");
        model.addAttribute("usuario", usuarioService.obtenerPorId(id));
        return "usuarios/detalle";
    }

    @GetMapping("/{id}/editar")
    public String formularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("titulo", "Editar usuario");
        model.addAttribute("usuario", usuarioService.obtenerPorId(id));
        model.addAttribute("esEdicion", true);
        return "usuarios/formulario";
    }

    @PostMapping("/{id}")
    public String actualizar(@PathVariable Long id, @Valid @ModelAttribute("usuario") Usuario usuario,
            BindingResult result, Model model, RedirectAttributes flash) {
        usuario.setId(id);
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Editar usuario");
            model.addAttribute("esEdicion", true);
            return "usuarios/formulario";
        }
        try {
            usuarioService.guardar(usuario);
        } catch (IllegalArgumentException ex) {
            result.rejectValue("email", "duplicado", ex.getMessage());
            model.addAttribute("titulo", "Editar usuario");
            model.addAttribute("esEdicion", true);
            return "usuarios/formulario";
        }
        flash.addFlashAttribute("exito", "Usuario actualizado correctamente");
        return "redirect:/usuarios";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        usuarioService.eliminar(id);
        flash.addFlashAttribute("exito", "Usuario eliminado correctamente");
        return "redirect:/usuarios";
    }
}

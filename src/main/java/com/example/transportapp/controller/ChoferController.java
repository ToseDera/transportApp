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

import com.example.transportapp.model.CategoriaLicencia;
import com.example.transportapp.model.Chofer;
import com.example.transportapp.service.ChoferService;

import jakarta.validation.Valid;

/** Controlador del MVC: traduce las peticiones HTTP de choferes en llamadas al servicio y vistas. */
@Controller
@RequestMapping("/choferes")
public class ChoferController {

    private final ChoferService choferService;

    public ChoferController(ChoferService choferService) {
        this.choferService = choferService;
    }

    @ModelAttribute("categorias")
    public CategoriaLicencia[] categorias() {
        return CategoriaLicencia.values();
    }

    @GetMapping
    public String listar(@RequestParam(required = false) String q, Model model) {
        model.addAttribute("titulo", "Choferes");
        model.addAttribute("choferes", choferService.buscar(q));
        model.addAttribute("q", q);
        return "choferes/lista";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("titulo", "Nuevo chofer");
        model.addAttribute("chofer", new Chofer());
        model.addAttribute("esEdicion", false);
        return "choferes/formulario";
    }

    @PostMapping
    public String guardar(@Valid @ModelAttribute("chofer") Chofer chofer, BindingResult result,
            Model model, RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Nuevo chofer");
            model.addAttribute("esEdicion", false);
            return "choferes/formulario";
        }
        try {
            choferService.guardar(chofer);
        } catch (IllegalArgumentException ex) {
            result.rejectValue("documento", "duplicado", ex.getMessage());
            model.addAttribute("titulo", "Nuevo chofer");
            model.addAttribute("esEdicion", false);
            return "choferes/formulario";
        }
        flash.addFlashAttribute("exito", "Chofer registrado correctamente");
        return "redirect:/choferes";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        model.addAttribute("titulo", "Detalle del chofer");
        model.addAttribute("chofer", choferService.obtenerPorId(id));
        return "choferes/detalle";
    }

    @GetMapping("/{id}/editar")
    public String formularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("titulo", "Editar chofer");
        model.addAttribute("chofer", choferService.obtenerPorId(id));
        model.addAttribute("esEdicion", true);
        return "choferes/formulario";
    }

    @PostMapping("/{id}")
    public String actualizar(@PathVariable Long id, @Valid @ModelAttribute("chofer") Chofer chofer,
            BindingResult result, Model model, RedirectAttributes flash) {
        chofer.setId(id);
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Editar chofer");
            model.addAttribute("esEdicion", true);
            return "choferes/formulario";
        }
        try {
            choferService.guardar(chofer);
        } catch (IllegalArgumentException ex) {
            result.rejectValue("documento", "duplicado", ex.getMessage());
            model.addAttribute("titulo", "Editar chofer");
            model.addAttribute("esEdicion", true);
            return "choferes/formulario";
        }
        flash.addFlashAttribute("exito", "Chofer actualizado correctamente");
        return "redirect:/choferes";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        choferService.eliminar(id);
        flash.addFlashAttribute("exito", "Chofer eliminado correctamente");
        return "redirect:/choferes";
    }
}

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

import com.example.transportapp.model.Carro;
import com.example.transportapp.service.CarroService;

import jakarta.validation.Valid;

/** Controlador del MVC: traduce las peticiones HTTP de carros en llamadas al servicio y vistas. */
@Controller
@RequestMapping("/carros")
public class CarroController {

    private final CarroService carroService;

    public CarroController(CarroService carroService) {
        this.carroService = carroService;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) String q, Model model) {
        model.addAttribute("titulo", "Carros");
        model.addAttribute("carros", carroService.buscar(q));
        model.addAttribute("q", q);
        return "carros/lista";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("titulo", "Nuevo carro");
        model.addAttribute("carro", new Carro());
        model.addAttribute("esEdicion", false);
        return "carros/formulario";
    }

    @PostMapping
    public String guardar(@Valid @ModelAttribute("carro") Carro carro, BindingResult result,
            Model model, RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Nuevo carro");
            model.addAttribute("esEdicion", false);
            return "carros/formulario";
        }
        try {
            carroService.guardar(carro);
        } catch (IllegalArgumentException ex) {
            result.rejectValue("placa", "duplicado", ex.getMessage());
            model.addAttribute("titulo", "Nuevo carro");
            model.addAttribute("esEdicion", false);
            return "carros/formulario";
        }
        flash.addFlashAttribute("exito", "Carro registrado correctamente");
        return "redirect:/carros";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        model.addAttribute("titulo", "Detalle del carro");
        model.addAttribute("carro", carroService.obtenerPorId(id));
        return "carros/detalle";
    }

    @GetMapping("/{id}/editar")
    public String formularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("titulo", "Editar carro");
        model.addAttribute("carro", carroService.obtenerPorId(id));
        model.addAttribute("esEdicion", true);
        return "carros/formulario";
    }

    @PostMapping("/{id}")
    public String actualizar(@PathVariable Long id, @Valid @ModelAttribute("carro") Carro carro,
            BindingResult result, Model model, RedirectAttributes flash) {
        carro.setId(id);
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Editar carro");
            model.addAttribute("esEdicion", true);
            return "carros/formulario";
        }
        try {
            carroService.guardar(carro);
        } catch (IllegalArgumentException ex) {
            result.rejectValue("placa", "duplicado", ex.getMessage());
            model.addAttribute("titulo", "Editar carro");
            model.addAttribute("esEdicion", true);
            return "carros/formulario";
        }
        flash.addFlashAttribute("exito", "Carro actualizado correctamente");
        return "redirect:/carros";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        carroService.eliminar(id);
        flash.addFlashAttribute("exito", "Carro eliminado correctamente");
        return "redirect:/carros";
    }
}

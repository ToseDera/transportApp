package com.example.transportapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.transportapp.service.CarroService;
import com.example.transportapp.service.ChoferService;
import com.example.transportapp.service.UsuarioService;

/** Controlador de la página de inicio: reúne los conteos de cada entidad para el tablero. */
@Controller
@RequestMapping("/")
public class HomeController {

    private final UsuarioService usuarioService;
    private final CarroService carroService;
    private final ChoferService choferService;

    public HomeController(UsuarioService usuarioService, CarroService carroService, ChoferService choferService) {
        this.usuarioService = usuarioService;
        this.carroService = carroService;
        this.choferService = choferService;
    }

    @GetMapping
    public String inicio(Model model) {
        model.addAttribute("titulo", "Inicio");
        model.addAttribute("totalUsuarios", usuarioService.contar());
        model.addAttribute("totalCarros", carroService.contar());
        model.addAttribute("totalChoferes", choferService.contar());
        return "index";
    }
}

package com.example.tiendaRopaSpring.controller;

import com.example.tiendaRopaSpring.model.Usuario;
import com.example.tiendaRopaSpring.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/web/usuarios")
public class UsuarioWebController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("listaUsuarios", usuarioService.listarUsuarios(null));

        model.addAttribute("nuevoUsuario", new Usuario());

        return "usuarios";
    }

    @PostMapping("/registrar")
    public String registrarUsuario(@ModelAttribute("nuevoUsuario") Usuario usuario, Model model) {
        try {
            usuarioService.registrarUsuario(usuario);
            return "redirect:/web/usuarios";
        } catch (ResponseStatusException e) {
            model.addAttribute("error", e.getReason());
            model.addAttribute("listaUsuarios", usuarioService.listarUsuarios(null));
            model.addAttribute("nuevoUsuario", usuario);
            return "usuarios";
        }
    }

    @GetMapping("/detalle/{id}")
    public String verDetalle(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.buscarPorId(id);
        model.addAttribute("usuario", usuario);
        return "usuario_detalle";
    }
}

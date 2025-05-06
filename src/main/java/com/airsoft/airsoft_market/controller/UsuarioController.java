package com.airsoft.airsoft_market.controller;

import com.airsoft.airsoft_market.model.Usuario;
import com.airsoft.airsoft_market.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @GetMapping("/register")
    public String mostrarFormularioRegistro() {
        return "register";
    }

    @PostMapping("/register")
    public String registrarUsuario(@RequestParam String email,
                                   @RequestParam String password) {
        Usuario nuevo = new Usuario(email, password);
        usuarioRepo.save(nuevo);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String mostrarFormularioLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String email,
                                 @RequestParam String password,
                                 HttpSession session) {
        Usuario usuario = usuarioRepo.findByEmail(email);
        if (usuario != null && usuario.getPassword().equals(password)) {
            session.setAttribute("usuarioLogueado", usuario);
            return "redirect:/";
        }
        return "redirect:/login?error=true";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) 
    {
        session.invalidate(); // Cierra la sesión
        return "redirect:/";  // Vuelve al inicio (puedes cambiar a "/login" si prefieres)
    }
}

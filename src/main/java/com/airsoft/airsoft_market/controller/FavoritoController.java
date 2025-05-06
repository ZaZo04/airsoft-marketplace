package com.airsoft.airsoft_market.controller;

import com.airsoft.airsoft_market.model.Favorito;
import com.airsoft.airsoft_market.model.Producto;
import com.airsoft.airsoft_market.model.Usuario;
import com.airsoft.airsoft_market.repository.FavoritoRepository;
import com.airsoft.airsoft_market.repository.ProductoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class FavoritoController {

    @Autowired
    private FavoritoRepository favoritoRepo;

    @Autowired
    private ProductoRepository productoRepo;

    @PostMapping("/favoritos/{productoId}")
    public String agregarAFavoritos(@PathVariable Long productoId, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/login";
        }

        Optional<Producto> optionalProducto = productoRepo.findById(productoId);
        if (optionalProducto.isEmpty()) {
            return "redirect:/productos";
        }

        Producto producto = optionalProducto.get();

        // Verifica si ya está en favoritos
        boolean yaFavorito = favoritoRepo.findByUsuarioAndProducto(usuario, producto).isPresent();
        if (!yaFavorito) {
            favoritoRepo.save(new Favorito(usuario, producto));
        }

        return "redirect:/productos";
    }

    @GetMapping("/favoritos")
    public String verFavoritos(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/login";
        }

        List<Favorito> favoritos = favoritoRepo.findByUsuario(usuario);
        model.addAttribute("favoritos", favoritos);

        return "favoritos";
    }

    @PostMapping("/favoritos/eliminar/{id}")
    public String eliminarFavorito(@PathVariable Long id, HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/login";
        }

        Optional<Favorito> favoritoOpt = favoritoRepo.findById(id);
        if (favoritoOpt.isPresent()) {
            Favorito favorito = favoritoOpt.get();
            // Solo permitir eliminar si el favorito es del usuario logueado
            if (favorito.getUsuario().getId().equals(usuario.getId())) {
                favoritoRepo.delete(favorito);
            }
        }

        return "redirect:/favoritos";
    }
}

package com.airsoft.airsoft_market.controller;
import com.airsoft.airsoft_market.model.Favorito;
import com.airsoft.airsoft_market.model.Producto;
import com.airsoft.airsoft_market.model.Usuario;
import com.airsoft.airsoft_market.repository.FavoritoRepository;
import com.airsoft.airsoft_market.repository.ProductoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import org.springframework.ui.Model;

@Controller
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepo;
    private FavoritoRepository favoritoRepo;

    @GetMapping("/publicar")
    public String mostrarFormularioPublicar(HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }
        return "publicar";
    }

    @PostMapping("/publicar")
    public String guardarProducto(@ModelAttribute Producto producto, HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/login";
        }

        producto.setUsuario(usuario);
        productoRepo.save(producto);
        return "redirect:/productos";
    }
    
    @GetMapping("/mis-publicaciones")
    public String verMisPublicaciones(HttpSession session, Model model) {
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/login";
        }

        List<Producto> misProductos = productoRepo.findByUsuario(usuario);
        model.addAttribute("misProductos", misProductos);
        return "mis-publicaciones";
    }

    @PostMapping("/eliminar-producto/{id}")
    public String eliminarProducto(@PathVariable Long id, HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/login";
        }

        Optional<Producto> productoOpt = productoRepo.findById(id);
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            // Solo permitir borrar si es el dueño
            if (producto.getUsuario().getId().equals(usuario.getId())) {
                productoRepo.delete(producto);
            }
        }

        return "redirect:/mis-publicaciones";
    }

    @GetMapping("/editar-producto/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, HttpSession session, Model model) {
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/login";
        }

        Optional<Producto> productoOpt = productoRepo.findById(id);
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            // Asegurarse de que el producto es del usuario logueado
            if (producto.getUsuario().getId().equals(usuario.getId())) {
                model.addAttribute("producto", producto);
                return "editar-producto";
            }
        }

        return "redirect:/mis-publicaciones";
    }

    @PostMapping("/actualizar-producto")
    public String actualizarProducto(@ModelAttribute Producto producto, HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/login";
        }

        // Verificamos que el producto existe y que pertenece al usuario
        Optional<Producto> productoOriginalOpt = productoRepo.findById(producto.getId());
        if (productoOriginalOpt.isPresent()) {
            Producto productoOriginal = productoOriginalOpt.get();

            if (productoOriginal.getUsuario().getId().equals(usuario.getId())) {
                productoOriginal.setTitulo(producto.getTitulo());
                productoOriginal.setPrecio(producto.getPrecio());
                productoOriginal.setCategoria(producto.getCategoria());
                productoOriginal.setEstado(producto.getEstado());
                productoOriginal.setImagenUrl(producto.getImagenUrl());
                productoOriginal.setDescripcion(producto.getDescripcion());

                productoRepo.save(productoOriginal);
            }
        }

        return "redirect:/mis-publicaciones";
    }

    @GetMapping("/productos")
    public String verProductos(@RequestParam(required = false) String nombre,
                           @RequestParam(required = false) String categoria,
                           HttpSession session,
                           Model model) {

        List<Producto> productos;

        if (nombre != null && !nombre.isEmpty()) {
            productos = productoRepo.findByTituloContainingIgnoreCase(nombre);
        } else if (categoria != null && !categoria.isEmpty()) {
            productos = productoRepo.findByCategoria(categoria);
        } else {
            productos = productoRepo.findAll();
        }

        model.addAttribute("productos", productos);

        try {
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

            if (usuario != null) {
                System.out.println("Usuario logueado: " + usuario.getEmail());

                List<Favorito> favoritos = favoritoRepo.findByUsuario(usuario);
                model.addAttribute("favoritosUsuario", favoritos);
            } else {
                System.out.println("Usuario logueado es null");
                model.addAttribute("favoritosUsuario", List.of());
            }

        } catch (Exception e) {
            System.out.println("⚠️ Error cargando favoritos: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("favoritosUsuario", List.of());
        }

        return "productos";
    }


}

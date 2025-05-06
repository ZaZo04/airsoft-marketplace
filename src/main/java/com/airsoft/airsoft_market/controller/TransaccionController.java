package com.airsoft.airsoft_market.controller;

import com.airsoft.airsoft_market.model.Producto;
import com.airsoft.airsoft_market.model.Transaccion;
import com.airsoft.airsoft_market.model.Usuario;
import com.airsoft.airsoft_market.repository.ProductoRepository;
import com.airsoft.airsoft_market.repository.TransaccionRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;



@Controller
public class TransaccionController {

    @Autowired
    private ProductoRepository productoRepo;

    @Autowired
    private TransaccionRepository transaccionRepo;

    @PostMapping("/comprar/{id}")
    public String comprarProducto(@PathVariable Long id, HttpSession session) {
        Usuario comprador = (Usuario) session.getAttribute("usuarioLogueado");

        if (comprador == null) {
            return "redirect:/login";
        }

        Optional<Producto> optionalProducto = productoRepo.findById(id);
        if (optionalProducto.isEmpty()) {
            return "redirect:/productos";
        }

        Producto producto = optionalProducto.get();
        Usuario vendedor = producto.getUsuario();

        if (vendedor.getId().equals(comprador.getId())) {
            // No puedes comprarte tu propio producto
            return "redirect:/productos";
        }

        Transaccion nueva = new Transaccion(
                comprador,
                vendedor,
                producto,
                producto.getPrecio(),
                "Pago en mano" // puedes poner otros métodos si quieres
        );

        transaccionRepo.save(nueva);

        return "redirect:/productos?compra=ok";


    }
}

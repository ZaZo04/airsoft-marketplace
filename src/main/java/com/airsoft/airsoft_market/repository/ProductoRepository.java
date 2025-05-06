package com.airsoft.airsoft_market.repository;

import com.airsoft.airsoft_market.model.Producto;
import com.airsoft.airsoft_market.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByCategoriaContainingIgnoreCase(String categoria);
    List<Producto> findByTituloContainingIgnoreCase(String Titulo);
    List<Producto> findByUsuario(Usuario usuario);
    List<Producto> findByCategoria(String categoria);


}

package com.airsoft.airsoft_market.repository;

import com.airsoft.airsoft_market.model.Favorito;
import com.airsoft.airsoft_market.model.Usuario;
import com.airsoft.airsoft_market.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoritoRepository extends JpaRepository<Favorito, Long> {
    List<Favorito> findByUsuario(Usuario usuario);
    Optional<Favorito> findByUsuarioAndProducto(Usuario usuario, Producto producto);
}

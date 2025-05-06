package com.airsoft.airsoft_market.repository;

import com.airsoft.airsoft_market.model.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
    List<Transaccion> findByCompradorId(Long compradorId);
    List<Transaccion> findByVendedorId(Long vendedorId);
}

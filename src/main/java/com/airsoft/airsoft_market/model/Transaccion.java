package com.airsoft.airsoft_market.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comprador_id")
    private Usuario comprador;

    @ManyToOne
    @JoinColumn(name = "vendedor_id")
    private Usuario vendedor;

    @OneToOne
    @JoinColumn(name = "producto_id", unique = true)
    private Producto producto;

    private double precioFinal;

    private String metodoPago;

    private String estado; // pendiente, aceptada, completada, etc.

    private LocalDate fecha;

    public Transaccion() {
        this.fecha = LocalDate.now();
        this.estado = "pendiente";
    }

    public Transaccion(Usuario comprador, Usuario vendedor, Producto producto, double precioFinal, String metodoPago) {
        this.comprador = comprador;
        this.vendedor = vendedor;
        this.producto = producto;
        this.precioFinal = precioFinal;
        this.metodoPago = metodoPago;
        this.estado = "pendiente";
        this.fecha = LocalDate.now();
    }

    // Getters y setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Usuario getComprador() { return comprador; }
    public void setComprador(Usuario comprador) { this.comprador = comprador; }

    public Usuario getVendedor() { return vendedor; }
    public void setVendedor(Usuario vendedor) { this.vendedor = vendedor; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public double getPrecioFinal() { return precioFinal; }
    public void setPrecioFinal(double precioFinal) { this.precioFinal = precioFinal; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
}

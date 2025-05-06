package com.airsoft.airsoft_market.model;

import jakarta.persistence.*;

@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private double precio;
    private String categoria;
    private String estado;
    private String imagenUrl;
    private String descripcion;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    

    // Constructor vacío
    public Producto() {}

    // Constructor con campos
    public Producto(String titulo, double precio, String categoria, String estado, String imagenUrl, String descripcion) {
        this.titulo = titulo;
        this.precio = precio;
        this.categoria = categoria;
        this.estado = estado;
        this.imagenUrl = imagenUrl;
        this.descripcion = descripcion;

    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}

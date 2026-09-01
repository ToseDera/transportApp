package com.example.transportapp.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/** Modelo del MVC: vehículo de la flota, mapeado a la tabla carros. */
@Entity
@Table(name = "carros")
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "La placa es obligatoria")
    @Pattern(regexp = "^[A-Za-z]{3}[0-9]{3}$",
            message = "La placa debe tener el formato ABC123")
    @Column(name = "placa", nullable = false, unique = true, length = 6)
    private String placa;

    @NotBlank(message = "La marca es obligatoria")
    @Size(max = 40, message = "La marca no puede superar 40 caracteres")
    @Column(name = "marca", nullable = false, length = 40)
    private String marca;

    @NotBlank(message = "El modelo es obligatorio")
    @Size(max = 40, message = "El modelo no puede superar 40 caracteres")
    @Column(name = "modelo", nullable = false, length = 40)
    private String modelo;

    @NotNull(message = "El año es obligatorio")
    @Min(value = 1990, message = "El año no puede ser anterior a 1990")
    @Max(value = 2030, message = "El año no puede ser posterior a 2030")
    @Column(name = "anio")
    private Integer anio;

    @NotBlank(message = "El color es obligatorio")
    @Size(max = 30, message = "El color no puede superar 30 caracteres")
    @Column(name = "color", nullable = false, length = 30)
    private String color;

    @NotNull(message = "El precio por día es obligatorio")
    @DecimalMin(value = "0.0", message = "El precio por día no puede ser negativo")
    @Column(name = "precio_dia", precision = 10, scale = 2)
    private BigDecimal precioDia;

    @Column(name = "disponible")
    private boolean disponible = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BigDecimal getPrecioDia() {
        return precioDia;
    }

    public void setPrecioDia(BigDecimal precioDia) {
        this.precioDia = precioDia;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}

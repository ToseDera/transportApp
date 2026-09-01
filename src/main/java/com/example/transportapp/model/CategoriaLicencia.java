package com.example.transportapp.model;

/** Categorías de licencia de conducción disponibles para un chofer. */
public enum CategoriaLicencia {

    C1("C1 - Vehículo particular"),
    C2("C2 - Transporte público"),
    C3("C3 - Carga pesada");

    private final String descripcion;

    CategoriaLicencia(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}

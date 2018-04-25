package com.example.estrella.fytproject.Interactor;

/**
 * Created by Estrella on 23/04/2018.
 */

public class PartidoEntity
{
    String partidoId;
    String nombre;
    String fecha;
    String hora;
    String deporte;
    String lugar;
    String descripcion;
    String userCreacion;
    String nrojugadores;
    String norma;
    String estado;
    String posicion;
    String tipo;

    public PartidoEntity() {
    }

    public PartidoEntity(String partidoId, String nombre, String fecha, String hora, String deporte, String lugar, String descripcion, String userCreacion, String nrojugadores, String norma, String estado, String posicion, String tipo) {
        this.partidoId = partidoId;
        this.nombre = nombre;
        this.fecha = fecha;
        this.hora = hora;
        this.deporte = deporte;
        this.lugar = lugar;
        this.descripcion = descripcion;
        this.userCreacion = userCreacion;
        this.nrojugadores = nrojugadores;
        this.norma = norma;
        this.estado = estado;
        this.posicion = posicion;
        this.tipo = tipo;
    }
}

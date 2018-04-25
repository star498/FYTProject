package com.example.estrella.fytproject.Interactor;

/**
 * Created by Estrella on 23/04/2018.
 */

public class UserEntity {

    String userId;
    String nombre;
    String correo;
    String numero;
    String about;
    String edad;
    String deporte;
    String sexo;

    public UserEntity() {
    }

    public UserEntity(String userId, String nombre, String correo) {
        this.userId = userId;
        this.nombre = nombre;
        this.correo = correo;
    }

    public UserEntity(String userId, String nombre) {
        this.userId = userId;
        this.nombre = nombre;
    }

    public UserEntity(String userId, String nombre, String correo, String numero, String about, String edad, String deporte, String sexo) {
        this.userId = userId;
        this.nombre = nombre;
        this.correo = correo;
        this.numero = numero;
        this.about = about;
        this.edad = edad;
        this.deporte = deporte;
        this.sexo = sexo;
    }

    public String getCorreo() {
        return correo;
    }

    public String getNumero() {
        return numero;
    }

    public String getAbout() {
        return about;
    }

    public String getEdad() {
        return edad;
    }

    public String getDeporte() {
        return deporte;
    }

    public String getSexo() {
        return sexo;
    }

    public String getUserId() {
        return userId;
    }

    public String getNombre() {
        return nombre;
    }
}

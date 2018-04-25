package com.example.estrella.fytproject.Interfaces;

/**
 * Created by Estrella on 19/04/2018.
 */

public interface InicioInterface

{

    interface  ViewInterface{
 void MostrarMenu();
}

interface  ModelInterface{
    void CrearCuenta(String correo, String pass);
}

interface PresenterInterface{
    void MostrarMenu();
    void CrearCuenta(String correo, String pass);
}

}

package com.example.estrella.fytproject.Presentador;

import com.example.estrella.fytproject.Interactor.InicioModel;
import com.example.estrella.fytproject.Interfaces.InicioInterface;
import com.example.estrella.fytproject.Vista.CrearCuenta;

/**
 * Created by Estrella on 19/04/2018.
 */

public class InicioPresenter implements InicioInterface.PresenterInterface {

    private InicioInterface.ViewInterface view;
    private InicioInterface.ModelInterface model;

    public InicioPresenter(CrearCuenta view){
        this.view = view;
        model = new InicioModel(this);
    }
    @Override
    public void MostrarMenu() {
        if(view!=null){
            view.MostrarMenu();
        }
    }

    @Override
    public void CrearCuenta(String correo, String pass) {
        if(view!=null){
            model.CrearCuenta(correo, pass);
        }
    }
}

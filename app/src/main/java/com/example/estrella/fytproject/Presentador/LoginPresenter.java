package com.example.estrella.fytproject.Presentador;

import com.example.estrella.fytproject.Interactor.LoginModel;
import com.example.estrella.fytproject.Interfaces.LoginInterface;
import com.example.estrella.fytproject.Vista.Login;
import com.facebook.AccessToken;

/**
 * Created by Estrella on 19/04/2018.
 */

public class LoginPresenter implements LoginInterface.Presenter {

    private LoginInterface.View view;
    private LoginInterface.Model model;

    public LoginPresenter(Login view){
        this.view = view;
        model = new LoginModel(this);
    }

    @Override
    public boolean validarexistencia(String correo) {
        return model.validarexistencia(correo);
    }

    @Override
    public void MostrarMenu() {
if(view!=null){
    view.MostrarMenu();
}
    }

    @Override
    public void AddUser(String correo, String usuario) {
        model.AddUser(correo, usuario);
    }

    @Override
    public void LoginFacebook(AccessToken token) {
        if(view!=null){
           model.LoginFacebook(token);
        }
    }

    @Override
    public void LoginGmail() {
        if(view!=null){
            model.LoginGmail();
        }
    }

    @Override
    public void LoginCuenta(String correo, String pass) {
        if(view!=null){
           model.LoginCuenta(correo, pass);
        }
    }
}

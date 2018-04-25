package com.example.estrella.fytproject.Interfaces;

import android.content.Intent;
import android.media.session.MediaSession;

import com.facebook.AccessToken;

/**
 * Created by Estrella on 19/04/2018.
 */

public interface LoginInterface {

    interface View{
        void MostrarMenu();;
    }
    interface  Presenter{
        boolean validarexistencia(String correo);
        void MostrarMenu();
        void AddUser(String correo , String usuario);
        void LoginFacebook(AccessToken token);
        void LoginGmail();
        void LoginCuenta(String correo, String pass);
    }
    interface Model{
        boolean validarexistencia(String correo);
        void AddUser(String correo, String usuario);
        void LoginFacebook(AccessToken token);
        void LoginGmail();
        void LoginCuenta(String correo, String pass);
    }
}

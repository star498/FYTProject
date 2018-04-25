package com.example.estrella.fytproject.Interactor;

import com.example.estrella.fytproject.Interfaces.InicioInterface;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Estrella on 19/04/2018.
 */

public class InicioModel  implements InicioInterface.ModelInterface{
    private InicioInterface.PresenterInterface presenter;
    private FirebaseAuth auth;
    public InicioModel(InicioInterface.PresenterInterface presenter){
        this.presenter = presenter;
    }

    @Override
    public void CrearCuenta(String correo, String pass) {
        auth = FirebaseAuth.getInstance();
        try {
            auth.createUserWithEmailAndPassword(correo, pass) ;
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if(user!=null){
                presenter.MostrarMenu();
            }

        }catch (Exception e){
            System.out.println("no se puedo crear cuenta " + e.getMessage() +  "  "  +e.getStackTrace());
        }
    }
}

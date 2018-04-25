package com.example.estrella.fytproject.Vista;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.estrella.fytproject.Interactor.PartidoEntity;
import com.example.estrella.fytproject.Interactor.UserEntity;
import com.example.estrella.fytproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Estrella on 20/04/2018.
 */

public class PartidoConfigFragment extends Fragment {

private EditText txtnrojugadores, txtnorma;
private Button btnpartidoconfig;
private Switch swparprivado, swexfallas, swcomredes;private FirebaseAuth firebaseAuth;
    final String TAG = "PartidoConfigFragment";
  private String posicionv;
  private String usuariopartido;

  //  Boolean switchState,  switchState2,  switchState3;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View vista= inflater.inflate(R.layout.frangment_partido_config, container, false);

       swparprivado = (Switch) vista.findViewById(R.id.swparprivado);
        swexfallas = (Switch) vista.findViewById(R.id.swexfallas);
        swcomredes = (Switch) vista.findViewById(R.id.swcomredes);
        txtnorma=(EditText)vista.findViewById(R.id.norma);
        txtnrojugadores=(EditText)vista.findViewById(R.id.txtnrojugadores);
       final SharedPreferences sharpref= getActivity().getSharedPreferences("datospartido", getContext().MODE_PRIVATE);



        //conexion usuario
        firebaseAuth= FirebaseAuth.getInstance();
        final FirebaseUser user= firebaseAuth.getCurrentUser();

       //guardar data local
        SharedPreferences.Editor editor= sharpref.edit();
        editor.putString("usercorreo", user.getEmail());
        editor.commit();


        Spinner spinner = (Spinner)vista.findViewById(R.id.spinnerposicion);
        String[] letra = {"Armador","Pibot","Alero","Arquero","Delantero", "Defensa", "Centrocampista"};
        spinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, letra));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
            {
                Toast.makeText(adapterView.getContext(),
                        (String) adapterView.getItemAtPosition(pos), Toast.LENGTH_SHORT).show();
                posicionv=(String) adapterView.getItemAtPosition(pos);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {    }
        });

        btnpartidoconfig=(Button)vista.findViewById(R.id.btnpartidoconfig);
        btnpartidoconfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor= sharpref.edit();
                editor.putString("nrojugadores", txtnrojugadores.getText().toString());
              /*  editor.putString("swparprivado", switchState.toString());
                editor.putBoolean("swexfallas", switchState2);
                editor.putBoolean("swcomredes", switchState3);*/
                editor.putString("norma", txtnorma.getText().toString());
              //  editor.putString("posicion", posicionv);

                editor.commit();

                String correov=sharpref.getString("usercorreo", " ");
                ConsultarUsuario(correov);

            }

            private void GuardarDtos() {

                String nrojugadoresv = sharpref.getString("nrojugadores", " ");
                String normav = sharpref.getString("norma", "  ");
                String fechav = sharpref.getString("fecha", "  ");

                String horav = sharpref.getString("hora", "  ");
                String nombrev = sharpref.getString("nombre", " ");
                String deportev = sharpref.getString("deporte", " ");

               String lugarv = sharpref.getString("lugar", " ");
                String descripcionv = sharpref.getString("descripcion", " ");

                String tipo = "publico";
                String estado="1";
                if( swparprivado.isChecked()){

                    tipo ="privado";

                }if(swexfallas.isChecked()){
                   estado="2";
                }

                savePartido(nrojugadoresv,normav,fechav, horav, nombrev,lugarv,descripcionv, tipo, estado , deportev, posicionv);


            }
            private void ConsultarUsuario(String correov) {

                DatabaseReference datauser= FirebaseDatabase.getInstance().getReference("users");

                Query id= datauser.orderByChild("correo").equalTo(correov).limitToFirst(1);

                id.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                            Log.d(TAG, "Key: " + postSnapshot.getKey());
                            Log.d(TAG, "Value: " + postSnapshot.getValue());
                            UserEntity us= postSnapshot.getValue(UserEntity.class);
                            SharedPreferences.Editor editor= sharpref.edit();
                            editor.putString("userid", us.getUserId().toString());
                            editor.putString("username", us.getNombre().toString());
                            editor.commit();

                            String d=sharpref.getString("userid", "");
                            Toast.makeText(getActivity(), "iidd es "+d , Toast.LENGTH_SHORT).show();
                            GuardarDtos();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            public void  savePartido(String  deportev,String posicionv,String  nrojugadoresv,String normav,String fechav, String horav,String nombrev,String lugarv,String descripcionv, String tipo, String estado ){

                String usernamev=sharpref.getString("username", " ");
                String useridv=sharpref.getString("userid", " no hay nada");

                Toast.makeText(getActivity(), "fecha es "+ useridv, Toast.LENGTH_SHORT).show();
               DatabaseReference datapartido = FirebaseDatabase.getInstance().getReference("partido").child(useridv);
                String idp= datapartido.push().getKey();

                try {
                    PartidoEntity p = new PartidoEntity(idp,nombrev,fechav,horav,deportev, lugarv, descripcionv,usernamev,nrojugadoresv, normav, estado, posicionv, tipo);
                    datapartido.child(idp).setValue(p);
                    Toast.makeText(getActivity(), "guardo partido", Toast.LENGTH_SHORT).show();
                    txtnrojugadores.setText("");
                    txtnorma.setText("");
                    swparprivado.setChecked(false);
                    swexfallas.setChecked(false);
                    swcomredes.setChecked(false);

                   sharpref.edit().clear().commit();
                }catch (DatabaseException e){
                    Toast.makeText(getActivity(), "erro al guardar partido"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }


        });
        //mostrarDatos();

        return vista;
    }


   /* private void mostrarDatos() {

        String nrojugadoresv = sharpref.getString("nrojugadores", " ");
        String normav = sharpref.getString("norma", "  ");
       String swprpartidov= sharpref.getString("swparprivado", "false");
        txtnrojugadores.setText(nrojugadoresv);
        txtnorma.setText(normav);
        Toast.makeText(getActivity(), "estado es " + swprpartidov, Toast.LENGTH_SHORT).show();


    }*/




}

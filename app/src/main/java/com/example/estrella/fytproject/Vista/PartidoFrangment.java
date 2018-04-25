package com.example.estrella.fytproject.Vista;


import android.app.Activity;
import android.app.DatePickerDialog;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.estrella.fytproject.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;


/**
 * Created by Estrella on 20/04/2018.
 */

public class PartidoFrangment extends Fragment {

    private Button btnfecha, btnhora, btnLugar, btnbtnguardardatos;
    private EditText txtfecha, txthora ,  txtDireccion, txtnompartido;
    private MultiAutoCompleteTextView txtdescripcion;
    private  Spinner spinner;
    private int dia,mes, ano, hora, minuto;
    private int PLACE_PICKER_REQUEST=1;
    private String fechap, horap, lugarp,deportep  ;

    @Nullable
    @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){


        View vista = inflater.inflate(R.layout.frangment_partido, container, false);
        final SharedPreferences  sharprefs= getActivity().getSharedPreferences("datospartido", getContext().MODE_PRIVATE);

       txtnompartido=(EditText)vista.findViewById(R.id.txtnompartido);
        txtfecha= (EditText)vista.findViewById(R.id.txtfecha);
        txthora=(EditText)vista.findViewById(R.id.txthora);
        txtDireccion=(EditText) vista.findViewById(R.id.txtDireccion);
        txtdescripcion=(MultiAutoCompleteTextView)vista.findViewById(R.id.txtdescripcion);
        btnfecha= (Button)vista.findViewById(R.id.btnfecha);
        btnfecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final Calendar c= Calendar.getInstance();
               dia= c.get(Calendar.DAY_OF_MONTH);
               mes= c.get(Calendar.MONTH);
               ano= c.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog= new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                       fechap=dayOfMonth + "- "+ (month+1)+ " - "+ year;
                        txtfecha.setText(dayOfMonth + "- "+ (month+1)+ " - "+ year) ;


                    }
                },dia,mes, ano);
                datePickerDialog.show();

            }
        });

        btnhora=(Button)vista.findViewById(R.id.btnhora);
        btnhora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        final Calendar c= Calendar.getInstance();
             hora = c.get(Calendar.HOUR_OF_DAY);
             minuto= c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog= new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        horap=hourOfDay + " : " + minute;
                        txthora.setText(hourOfDay + " : " + minute);
                    }
                }, hora, minuto,false);
                timePickerDialog.show();


            }
        });

        spinner = (Spinner)vista.findViewById(R.id.spinnerDeporte);
        String[] letra = {"Basquet","Futbol","Voleybol","Tenis","Bata"};
        spinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, letra));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
            {
                Toast.makeText(adapterView.getContext(),
                        (String) adapterView.getItemAtPosition(pos), Toast.LENGTH_SHORT).show();
                deportep=  (String) adapterView.getItemAtPosition(pos);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {    }
        });

        btnLugar=(Button)vista.findViewById(R.id.btnLugar);
        btnLugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                Intent intent;
                try {
                    intent= builder.build(getActivity());
                 startActivityForResult(intent,PLACE_PICKER_REQUEST );

                }catch (GooglePlayServicesRepairableException e){
                    e.getStackTrace();
                }catch (GooglePlayServicesNotAvailableException e){
                    e.getStackTrace();
                }

                /*FragmentManager fragmentManager= getFragmentManager();
                DialogMaps dm= new DialogMaps();
                dm.show(fragmentManager, "Direccion");*/

            }
        });



        btnbtnguardardatos=(Button)vista.findViewById(R.id.btnguardardatos);

        btnbtnguardardatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=  sharprefs.edit();
                editor.putString("fecha", fechap);
                editor.putString("hora", horap);
                editor.putString("nombre", txtnompartido.getText().toString());
                editor.putString("lugar", lugarp);
                editor.putString("deporte", deportep);
                editor.putString("descripcion", txtdescripcion.getText().toString());

                editor.commit();
                Fragment selectedfra= new PartidoEquiposFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedfra).commit();



            }


        });


        String fechav = sharprefs.getString("fecha", "  ");
        String horav = sharprefs.getString("hora", "  ");
        String nombrev = sharprefs.getString("nombre", " ");
        String lugarv = sharprefs.getString("lugar", " ");
        // String deportev = sharpref.getString("deporte", "no hay ");
        String descripcionv = sharprefs.getString("descripcion", " ");
        txtfecha.setText(fechav);
        txthora.setText(horav);
        txtdescripcion.setText(descripcionv);
        txtDireccion.setText(lugarv);
        txtnompartido.setText(nombrev);




        return vista;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
    super.onActivityResult(requestCode, resultCode, data);
    if(requestCode==PLACE_PICKER_REQUEST){
    if(resultCode== getActivity().RESULT_OK){
        final Place place = PlacePicker.getPlace(data, getActivity());
        //Toast.makeText(getActivity(), "ligar es " + place.getAddress(),Toast.LENGTH_SHORT).show();
        lugarp=place.getAddress().toString();
        txtDireccion.setText(place.getAddress());


       }
      }
    }

    @Override

    public void onStop () {

        super.onStop();

    }




}

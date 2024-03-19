package com.example.carnet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;


public class activity_mostrar extends AppCompatActivity {

    ImageView imageViewMostrar;
    TextView nombreMostrar, apellidoMostrar;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar);

        imageViewMostrar = findViewById(R.id.imgMostrarQr);
        nombreMostrar = findViewById(R.id.txtMostrarV2);
        apellidoMostrar = findViewById(R.id.txtMostrarV3);

        Intent intent = getIntent();
        byte[] byteArray = intent.getByteArrayExtra("imagenQR");

        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        imageViewMostrar.setImageBitmap(bitmap);

        String nombre = intent.getStringExtra("nombre");
        String apellido = intent.getStringExtra("apellido");

        int indiceEspacio = nombre.indexOf(' ');
        if (indiceEspacio != -1 && indiceEspacio + 1 < nombre.length()) {
            // Obtener la primera letra después del espacio
            char primeraLetraDespuesDelEspacio = nombre.charAt(indiceEspacio + 1);

            id=nombre.charAt(0)+apellido+primeraLetraDespuesDelEspacio;
        } else {
            id=nombre.charAt(0)+apellido;
        }
        nombreMostrar.setText("Nombre: " + nombre + " " + apellido);
        apellidoMostrar.setText("ID: " + id);
    }
}
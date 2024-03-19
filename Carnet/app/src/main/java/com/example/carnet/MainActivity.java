package com.example.carnet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;

import android.widget.Toast;

import com.example.carnet.Clases.Usuario;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Usuario estudiante;
    EditText nombre, apellido;
    Spinner genero;
    Button mostrar;

    ImageView imageViewQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.activity_main));
        nombre=findViewById(R.id.txtNombre);
        apellido=findViewById(R.id.txtApellido);
        genero=findViewById(R.id.spnGenero);
        mostrar=findViewById(R.id.btnMostrar);

        mostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                escribir(nombre.getText().toString(),apellido.getText().toString(), "ESTUDIANTE");
                try {

                    estudiante = new Usuario(nombre.getText().toString(), apellido.getText().toString());
                    BarcodeEncoder qR = new BarcodeEncoder();
                    Bitmap ima = qR.encodeBitmap(estudiante.mostrar(), BarcodeFormat.QR_CODE, 400, 400);

                    Intent intent = new Intent(MainActivity.this, activity_mostrar.class);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    ima.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("imagenQR", byteArray);

                    intent.putExtra("nombre", nombre.getText().toString());
                    intent.putExtra("apellido", apellido.getText().toString());

                    startActivity(intent);

                } catch (Exception e) {
                    e.getMessage();
                }
            }
        });
    }
    public void escribir(final String nombre, final String apellido, final String cargo) {
        String url = "http://192.168.1.9/Conexiones/almacenarBD.php"; // Agrega "http://" al principio de la URL

        StringRequest respuesta = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, "Se almacenó correctamente", Toast.LENGTH_LONG).show(); // Corrige el método "show()"
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error al almacenar", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nombre_estudiante", nombre);
                params.put("apellido_estudiante", apellido);
                params.put("cargo_estudiante", cargo);

                return params;
            }
        };

        Volley.newRequestQueue(this).add(respuesta);
    }
    public void leer() {
        Toast.makeText(MainActivity.this,"Estoy dentro leer",Toast.LENGTH_LONG).show();
        String url = "http://192.168.1.9/Conexiones/Leer.php"; // Agrega "http://" al principio de la URL
    Toast.makeText(MainActivity.this,"conectar",Toast.LENGTH_LONG).show();
        StringRequest respuesta = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
Estudiante estudiante=null;
                ArrayList <Estudiante> dato=new ArrayList();
                try {
                    JSONArray datos=new JSONArray(response);
                    String valores="";

                    for(int i=0;i<datos.length();i++){
                        JSONObject lectura=datos.getJSONObject(i);
                        estudiante=new Estudiante(Integer.parseInt(lectura.getString("id_estudiante")),lectura.getString("nombre_estudiante"), lectura.getString("apellido_estudiante"),lectura.getString("cargo_estudiante") );
                        dato.add(estudiante);
                        //valores=valores+lectura.get("id_estudiante")+lectura.get("nombre_estudiante")+lectura.get("cargo_estudiante");
                    }
                    Toast.makeText(MainActivity.this,"cantidad"+dato.get(0).getId(), Toast.LENGTH_LONG).show();
                }catch(JSONException e){
                    throw new RuntimeException(e);
                }
                }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error al almacenar", Toast.LENGTH_LONG).show();
            }
        }) ;

        Volley.newRequestQueue(this).add(respuesta);

};
}

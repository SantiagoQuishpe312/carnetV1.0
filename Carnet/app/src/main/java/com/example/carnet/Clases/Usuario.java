package com.example.carnet.Clases;

import java.util.Date;

public class Usuario{
    private String nombre, apellido, id;
    public Usuario(String nombre, String apel){
        this.nombre=nombre;
        apellido=apel;
        int indiceEspacio = nombre.indexOf(' ');

        if (indiceEspacio != -1 && indiceEspacio + 1 < nombre.length()) {
            // Obtener la primera letra después del espacio
            char primeraLetraDespuesDelEspacio = nombre.charAt(indiceEspacio + 1);

            id=nombre.charAt(0)+apellido+primeraLetraDespuesDelEspacio;
        } else {
            id=nombre.charAt(0)+apellido;
        }
    }
    public String mostrar(){

        return "CARNET DIGITAL - "+apellido+", "+nombre+" - "+id+" - ESTUDIANTE - ESPE MATRIZ SANGOLQUI - [PRES] SOFTWARE (J)";

    }
}

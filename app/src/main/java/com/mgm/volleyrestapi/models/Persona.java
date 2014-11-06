package com.mgm.volleyrestapi.models;

/**
 * Created by miguel on 05/11/2014.
 */
public class Persona {
    private String nombre;
    private String salarioDiario;
    private String diasLaborados;
    private String sueldo;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSalarioDiario() {
        return salarioDiario;
    }

    public void setSalarioDiario(String salarioDiario) {
        this.salarioDiario = "$" + salarioDiario + " por día";
    }

    public String getDiasLaborados() {
        return diasLaborados;
    }

    public void setDiasLaborados(String diasLaborados) {
        this.diasLaborados = diasLaborados + " días trabajados";
    }

    public String getSueldo() {
        return sueldo;
    }

    public void setSueldo(String sueldo) {
        this.sueldo = "Sueldo Total $" + sueldo;
    }


}

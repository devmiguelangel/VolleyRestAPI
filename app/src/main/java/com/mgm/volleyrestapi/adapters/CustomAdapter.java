package com.mgm.volleyrestapi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mgm.volleyrestapi.R;
import com.mgm.volleyrestapi.models.Persona;

import java.util.ArrayList;

/**
 * Created by miguel on 05/11/2014.
 */
public class CustomAdapter extends ArrayAdapter<Persona> {
    ArrayList<Persona> data;
    LayoutInflater inflater;


    public CustomAdapter(Context context, ArrayList<Persona> objects) {
        super(context, -1, objects);

        this.data = objects;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View view, ViewGroup group) {
        ViewHolder holder;
        Persona persona = data.get(position);

        int layout = R.layout.list_row;

        if (view == null) {
            view = inflater.inflate(layout, null);
            holder = new ViewHolder();
            holder.nombre = (TextView) view.findViewById(R.id.nombre);
            holder.salarioDiario = (TextView) view.findViewById(R.id.salario_diario);
            holder.diasLaborados = (TextView) view.findViewById(R.id.dias_laborados);
            holder.sueldo = (TextView) view.findViewById(R.id.sueldo);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.nombre.setText(persona.getNombre());
        holder.sueldo.setText(persona.getSueldo());
        holder.salarioDiario.setText(persona.getSalarioDiario());
        holder.diasLaborados.setText(persona.getDiasLaborados());

        return view;
    }

    public static class ViewHolder{
        public TextView nombre;
        public TextView salarioDiario;
        public TextView diasLaborados;
        public TextView sueldo;
    }

}

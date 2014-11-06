package com.mgm.volleyrestapi;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.mgm.volleyrestapi.adapters.CustomAdapter;
import com.mgm.volleyrestapi.models.Persona;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Main extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Action Bar de color negro solo por estetica
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#000000"));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(colorDrawable);

        // Instancia de RequestQueue que recibe el contexto
        RequestQueue queue = Volley.newRequestQueue(this);

        // URL del SpreadSheet
        String URL = "https://script.google.com/macros/s/AKfycbwIJ95SzCARwac_Op3g3i9VE5VNWhQjOj-H6KParv2_H88mlj0/exec";

        // Creamos una instancia de un ProgressDialog para que el usuario observe que los datos se estan obteniendo
        final ProgressDialog progressDialog = ProgressDialog.show(this, "Please wait... ",
                "Downloading data...", true);

        //Configuramos nuestro request, al intanciar JsonArrayRequest recibe la URL y un Listener de JSON Array, el cual
        //contiene una serie de metodos a implelentar, practicamente si resulto bien o existio un error, ademas
        // sirve como un singleton para mandarlo a llamar cuando sea necesario hacer de nuevo el request.
        // Recordar que este solo construye el llamado HTTP, se ejecuta mas adelante cuando lo agregamos como parametro del metodo add
        //de RequestQueue

        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Un TextView antes de la vista nos permite ver los datos crudos dentro de la app
                TextView textView = (TextView) findViewById(R.id.datos_crudos);
                textView.setText(response.toString());

                // Dejamos en el Log un pequeño mensaje con el response como string para ver un
                // preview de lo que recibimos
                Log.e("Respuesta: ", response.toString());

                // Buscamos el ListView del main_activity
                ListView listView = (ListView) findViewById(R.id.list_view);

                // Si vemos el CustomAdapter debemos pasar un contexto y un ArrayList de objetos Persona
                // el ArrayList es parseado de la respuesta por medio de la funcion parser
                CustomAdapter adapter = new CustomAdapter(getApplicationContext(), parser(response));

                // Pasamos el adapter para que la lista se muestre el UI
                listView.setAdapter(adapter);
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                progressDialog.dismiss();
            }
        });

        // Ejecutamos Volley, solo aqui se manda a realizar
        queue.add(req);

    }

    public ArrayList<Persona> parser(JSONArray respuesta) {
        ArrayList<Persona> personasAux = new ArrayList<Persona>();

        for (int i = 0; i < respuesta.length(); i++) {
            JSONObject jsonObject;
            Persona persona = new Persona();

            try {
                jsonObject = (JSONObject) respuesta.get(i);
                persona.setNombre(jsonObject.getString("nombre"));
                persona.setSalarioDiario(jsonObject.getString("salarioDiario"));
                persona.setDiasLaborados(jsonObject.getString("diasLaborados"));
                persona.setSueldo(jsonObject.getString("sueldo"));
                personasAux.add(persona);

            } catch (JSONException e) {
                Log.e("Peticion: ", "Error al parsear");
            }
        }

        return personasAux;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

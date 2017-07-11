package com.example.theodhor.facebookIntegration;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.theodhor.facebookIntegration.volleyControl.appController;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Presentacion extends Fragment {
    RequestQueue requestQueue;
    private ListView listView;
    private ArrayList<Remedios> lr;
    private ItemAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    public SearchView sV;
    public String url="http://daemmolina.cl/selectproductos.php";


    public Presentacion() {
        // Required empty public constructor
        //cargarDatos();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        View v= inflater.inflate(R.layout.fragment_presentacion,null);
        lr=new ArrayList<>();
        cargarDatos();
        //Remedios reme=new Remedios("remedio","sirve pal resfrio","sgdfgdfgdf","500");
        //lr.add(reme);
        adapter=new ItemAdapter(getActivity(),lr);
        listView=(ListView)v.findViewById(R.id.lvproductos);
        listView.setAdapter(adapter);
        swipeRefreshLayout=(SwipeRefreshLayout)v.findViewById(R.id.refreshlayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
              refresh();

            }
        });
        return v;
    }
    public void refresh(){
        lr.clear();
        adapter.notifyDataSetChanged();
        cargarDatos();
        Toast.makeText(getActivity(), "Medicamentos Cargados",Toast.LENGTH_SHORT).show();
        swipeRefreshLayout.setRefreshing(false);
    }



    void cargarDatos(){


        StringRequest request =new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //en caso de exito
                try {
                    JSONArray jsonArray=new JSONArray(response);

                    for(int i=0 ;i< jsonArray.length();i++) {
                        String  id = jsonArray.getJSONObject(i).getString("id");
                        String nombre = jsonArray.getJSONObject(i).getString("nombre");
                        String cantidad = jsonArray.getJSONObject(i).getString("cantidad");
                        String categoria =jsonArray.getJSONObject(i).getString("categoria");
                        String des =jsonArray.getJSONObject(i).getString("des");
                        String precio=jsonArray.getJSONObject(i).getString("precio");
                        String image =jsonArray.getJSONObject(i).getString("imagen");
                        //Toast.makeText(presentacion.this, precio, Toast.LENGTH_SHORT).show();


                        lr.add(new Remedios(nombre,des,image,precio));
                        // adapter.notifyDataSetChanged();
                        // Toast.makeText(presentacion.this, image, Toast.LENGTH_SHORT).show();

                    }
                    adapter.notifyDataSetChanged();
                    //actualia el adaptador
                    //adapter.notifyDataSetChanged();
                    //Toast.makeText(presentacion.this, id + nombre, Toast.LENGTH_SHORT).show();
                    //Toast.makeText(presentacion.this, ""+Arrays.toString(nombre), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        appController.getInstance().addToRequestQueue(request);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        sV = (SearchView) item.getActionView();
        sV.setQueryHint("Buscar Remedio");

       sV.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                //  if(query.length()>0){
                adapter.getFilter().filter(query);
                //adapter.notifyDataSetChanged();
                //}else {
                //Toast.makeText(presentacion.this, "Porfavor Ingresa Medicamento a buscar",Toast.LENGTH_SHORT).show();
                //}


                return false;
            }
        });

    }
}


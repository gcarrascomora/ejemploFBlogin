package com.example.theodhor.facebookIntegration;


import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home_menu extends Fragment {
    View v;
    LinearLayout layoutmedicamentos;
    private FragmentManager FM;
    private FragmentTransaction FT;

    public Home_menu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home_menu, container, false);
        layoutmedicamentos = (LinearLayout) v.findViewById(R.id.boton_medicamentos);
        layoutmedicamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("pal que lee");
                Toast.makeText(getActivity(),"pal que lee",Toast.LENGTH_LONG).show();
                FM= getActivity().getSupportFragmentManager();
                FT= FM.beginTransaction();
                FT.replace(R.id.frame_content, new Presentacion()).commit();
            }
        });

        // layoutmedicamentos.setClickable(true);
        //layoutmedicamentos.setOnClickListener(this);
        return v;

    }
}
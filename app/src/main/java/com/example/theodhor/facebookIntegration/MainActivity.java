package com.example.theodhor.facebookIntegration;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.theodhor.facebookIntegration.Presentacion;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import android.support.v7.app.*;
import android.widget.Toast;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ShareDialog shareDialog;
    private Button logout;
    private FragmentManager FM;
    private FragmentTransaction FT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();

        //ab.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        //ab.setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        shareDialog = new ShareDialog(this);

        //information Facebook
        Bundle inBundle = getIntent().getExtras();
        String name = inBundle.get("name").toString();
        String surname = inBundle.get("surname").toString();
        String imageUrl = inBundle.get("imageUrl").toString();
        //camputar imagen
        View headerView = navigationView.getHeaderView(0);
        //ImageView imagen = (ImageView) headerView.findViewById(R.id.imagenuser);
        TextView drawerUsername = (TextView) headerView.findViewById(R.id.nombre);
        drawerUsername.setText(name+" "+surname);
        //imprimir imagen
/*        FM= getSupportFragmentManager();
        FT= FM.beginTransaction();
        FT.replace(R.id.frame_content, new Home()).commit();*/
        //imprime la url de la imagen
        // Toast.makeText(getApplicationContext(), imageUrl, Toast.LENGTH_LONG).show();
        new MainActivity.DownloadImage((ImageView) findViewById(R.id.profileImage)).execute(imageUrl);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
//carga la imagen cicular
        //new MainActivity.DownloadImage((ImageView) findViewById(R.id.imagenuser)).execute(imageUrl);


    }
    public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id =item.getItemId();
        //creating fragment object
        if (id == R.id.medicamentos) {

            FM= getSupportFragmentManager();
            FT= FM.beginTransaction();
            FT.replace(R.id.frame_content, new Presentacion()).commit();
            // Handle the camera action
        } else if (id == R.id.home) {

        }else if (id == R.id.home) {

        } else if (id == R.id.salir) {
            Intent login = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(login);
            LoginManager.getInstance().logOut();
            finish();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //MEnu Setting toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    }






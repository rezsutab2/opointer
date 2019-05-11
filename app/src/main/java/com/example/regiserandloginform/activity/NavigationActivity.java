package com.example.regiserandloginform.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.InputType;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.regiserandloginform.fragment.MapFragment;
import com.example.regiserandloginform.R;
import com.example.regiserandloginform.pojo.User;
import com.google.android.gms.maps.model.LatLng;
import java.util.HashMap;
import java.util.Map;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    User yourself;
    RequestQueue queue;
    String messageForEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        yourself= (User) getIntent().getSerializableExtra("yourself");
        queue= Volley.newRequestQueue(this);

        MapFragment mapFragment=new MapFragment();
        FragmentManager manager = getSupportFragmentManager();

        manager.beginTransaction().replace(R.id.mainLayout,mapFragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_addpointer) {

            LatLng latLng=MapFragment.placePointer(yourself);
            double latitude=latLng.latitude;
            double longitude=latLng.longitude;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Esemény létrehozása.");
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
            builder.setView(input);
            builder.setPositiveButton("OK", (dialog, which) -> {
                messageForEvent = input.getText().toString();
                postEventData(latitude,longitude,messageForEvent,yourself.getUser_id());
                Toast.makeText(this,"Esemény létrehozva!",Toast.LENGTH_LONG).show();
            });
            builder.setNegativeButton("Mégse", (dialog, which) -> dialog.cancel());
            builder.show();


        } else if (id == R.id.nav_personaldata) {

            new AlertDialog.Builder(this)
                    .setTitle("Személyes adatok")
                    .setMessage(yourself.toString())
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    })
                    .show();

        } else if (id == R.id.nav_friendlist) {

            Intent intent=new Intent(this, FriendListActivity.class);
            intent.putExtra("yourself",yourself);
            startActivity(intent);

        } else if (id == R.id.nav_events) {
            Intent intent=new Intent(this, EventListActivity.class);
            intent.putExtra("yourself",yourself);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void postEventData(double latitude,double longitude,String message,long user_id){
        String urlSendRequest="https://o-pointer.000webhostapp.com/placepointer.php";

        StringRequest postRequest = new StringRequest(Request.Method.POST, urlSendRequest,
                response -> {
                }
                ,
                error -> {
                }) {

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("latitude", String.valueOf(latitude));
                params.put("longitude", String.valueOf(longitude));
                params.put("message",message);
                params.put("user_id", String.valueOf(user_id));
                return params;
            }
        };
        queue.add(postRequest);
    }
}

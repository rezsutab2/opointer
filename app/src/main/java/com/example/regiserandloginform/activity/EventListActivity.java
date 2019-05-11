package com.example.regiserandloginform.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.regiserandloginform.R;
import com.example.regiserandloginform.fragment.MapFragment;
import com.example.regiserandloginform.pojo.Event;
import com.example.regiserandloginform.pojo.EventInfo;
import com.example.regiserandloginform.pojo.PointerLocation;
import com.example.regiserandloginform.pojo.User;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EventListActivity extends AppCompatActivity {

    private User yourself;
    private long your_id;
    private RequestQueue queue;
    private LinearLayout layoutEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        yourself= (User) getIntent().getSerializableExtra("yourself");
        your_id=yourself.getUser_id();
        layoutEvents=findViewById(R.id.layoutEvents);

        eventlist();
    }

    private void eventlist(){
        String urlFriendship="https://o-pointer.000webhostapp.com/friendlyevents.php?user_id="+your_id;

        queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, urlFriendship,
                null, response -> {
            for (int i = 0; i < response.length(); i++) {
                try{
                JSONObject jsonObject = response.getJSONObject(i);
                long user2_id = jsonObject.getLong("user_id");
                String username = jsonObject.getString("username");
                String date = jsonObject.getString("date");
                String message = jsonObject.getString("message");
                double latitude=jsonObject.getDouble("latitude");
                double longitude=jsonObject.getDouble("longitude");
                User user=new User(user2_id,username);
                Event event=new Event(date,message);
                PointerLocation location=new PointerLocation(latitude,longitude);
                EventInfo info=new EventInfo(user,event,location);
                TextView textView = new TextView(this);
                layoutEvents.addView(textView);
                textView.setText(info.toString());
                textView.setPadding(0,40,0,40);
                textView.setTextColor(Color.parseColor("#ffffff"));
                textView.setBackgroundResource(R.drawable.rainbow_blue);
                textView.setGravity(Gravity.CENTER|Gravity.CENTER);
                LatLng latLng=new LatLng(latitude,longitude);
                MapFragment.friendPointer(latLng,username,message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(error.getMessage())
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
        });
        queue.add(jsonArrayRequest);
    }
}
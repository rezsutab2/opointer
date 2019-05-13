package com.example.regiserandloginform.activity;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
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

import java.io.IOException;
import java.util.List;
import java.util.Locale;

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
                textviewAndPointer(info,latitude,longitude,username,message);
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }
        }, error -> {
        });
        queue.add(jsonArrayRequest);
    }

    private void textviewAndPointer(EventInfo info,double latitude,double longitude,String username,String message) throws IOException {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses  = geocoder.getFromLocation(latitude,longitude, 1);

        String city = addresses.get(0).getLocality();
        String country = addresses.get(0).getCountryName();

        TextView textView = new TextView(this);
        layoutEvents.addView(textView);
        textView.setText(info.toString()+"\n"+city+"\n"+country);
        textView.setPadding(0,40,0,40);
        textView.setTextColor(Color.parseColor("#ffffff"));
        textView.setBackgroundResource(R.drawable.rainbow_blue);
        textView.setGravity(Gravity.CENTER|Gravity.CENTER);
        LatLng latLng=new LatLng(latitude,longitude);
        MapFragment.friendPointer(latLng,username,message);
    }
}
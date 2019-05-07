package com.example.regiserandloginform.activity;

import android.app.AlertDialog;
import android.graphics.Color;
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
import com.example.regiserandloginform.pojo.User;

import org.json.JSONException;
import org.json.JSONObject;

public class FriendListActivity extends AppCompatActivity {

    LinearLayout layoutFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        layoutFriends=findViewById(R.id.layoutFriends);

        showFriendList();
    }

    private void showFriendList(){
        long your_id=getIntent().getLongExtra("user_id",29);

        String URL_FRIENDSHIP="https://o-pointer.000webhostapp.com/friendship.php?user_id="+your_id;

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, URL_FRIENDSHIP,
                null, response -> {
            for (int i=0;i<response.length();i++){
                try {
                    JSONObject jsonObject=response.getJSONObject(i);
                    long user_id=jsonObject.getLong("user_id");
                    String username=jsonObject.getString("username");
                    String real_name=jsonObject.getString("name");
                    String birthdate=jsonObject.getString("birthdate");
                    User user=new User(user_id,username,real_name,birthdate);
                    TextView textView=new TextView(this);
                    if (jsonObject.getInt("isapproved")==1){
                        layoutFriends.addView(textView);
                        textView.setText(user.toString());
                        textView.setPadding(0,40,0,40);
                        textView.setTextColor(Color.parseColor("#ffffff"));
                        textView.setBackgroundResource(R.drawable.rainbow_blue);
                        textView.setGravity(Gravity.CENTER|Gravity.CENTER);
                    }
                    else {
                        layoutFriends.addView(textView);
                        textView.setText(user.toString()+"\n\n Ez a felhasználó barátnak jelölt. " +
                                "Elfogadáshoz húzza a címkét jobbra, elutasításhoz balra!");
                        textView.setPadding(0,40,0,40);
                        textView.setTextColor(Color.parseColor("#000000"));
                        textView.setBackgroundResource(R.drawable.xflareone);
                        textView.setGravity(Gravity.CENTER|Gravity.CENTER);
                        //Textview hátterek a https://uigradients.com -ról
                    }
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
        requestQueue.add(jsonArrayRequest);
    }

}

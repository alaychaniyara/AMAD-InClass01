package inclass1.group3.group3_inclass01;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

//import inclass1.group3.group3_inclass01.data.Person;
import inclass1.group3.group3_inclass01.data.Users;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static inclass1.group3.group3_inclass01.MainActivity.api_ip;

public class WelcomeActivity extends AppCompatActivity {
    EditText name,email,pwd,age,address,dateofbirth;
    Users.User userinfo;
    String message ;

    TextView ageField, weightField,nameField;
    private final OkHttpClient client = new OkHttpClient();
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        email = findViewById(R.id.txtname);
        name = findViewById(R.id.txtuserid);
        pwd = findViewById(R.id.txtpwd);
        age = findViewById(R.id.txtage);
        dateofbirth = findViewById(R.id.txtDOB);
        address=findViewById(R.id.editTextAddress);
        email.setFocusable(false);
        age.setFocusable(false);
        address.setFocusable(false);
        dateofbirth.setFocusable(false);
        name.setFocusable(false);
        findViewById(R.id.btnUpdate).setVisibility(View.INVISIBLE);
        findViewById(R.id.buttonEditData).setVisibility(View.VISIBLE);
        if(getIntent().getExtras()!=null){
            if(getIntent().getExtras().getSerializable("token")!=null){
            token =getIntent().getExtras().getString("token");
            Log.d("1233445",token);
            }
        }
        getUserInfo();

        findViewById(R.id.buttonEditData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                age.setFocusable(true);
                address.setFocusable(true);

                email.setFocusable(false);
                email.setFocusableInTouchMode(false);
                address.setFocusableInTouchMode(true);
                age.setFocusableInTouchMode(true);
                dateofbirth.setFocusable(false);
                dateofbirth.setFocusableInTouchMode(false);
                name.setFocusable(false);
                name.setFocusableInTouchMode(false);
                findViewById(R.id.btnUpdate).setVisibility(View.VISIBLE);
                findViewById(R.id.buttonEditData).setVisibility(View.INVISIBLE);

            }
        });
        findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateUserInfo(userinfo.get_id());

            }
        });
    }

    private void updateUserInfo(String userid){

        JSONObject resultObject1 = new JSONObject();
        JSONObject resultObject2 = new JSONObject();
        try{
            resultObject1.put("propName","age");
            resultObject1.put("value",age.getText().toString());

            resultObject2.put("propName","address");
            resultObject2.put("value",address.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray jsonArray = new JSONArray();

        jsonArray.put(resultObject1);
        jsonArray.put(resultObject2);
        Log.d("demo", "onClick: " + jsonArray.toString());
       // JsonObject jsonObject= new JsonObject();
/*        jsonObject.addProperty("email",email.getText().toString());
        jsonObject.addProperty("name",name.getText().toString());
        jsonObject.addProperty("age",age.getText().toString());
        jsonObject.addProperty("dateofbirth",dateofbirth.getText().toString());
        jsonObject.addProperty("address",address.getText().toString());
        jsonArray.put(jsonObject);

*/
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
     /* RequestBody formBody= new FormBody.Builder()
              .add("age",age.getText().toString())
              .add("address",address.getText().toString()).build();
       */
     RequestBody formBody = RequestBody.create(JSON, jsonArray.toString());
        Log.d("demo", "updateUserInfo" +formBody.toString());
              //  RequestBody.create(JSON,jsonArray.toString());
        Request request = new Request.Builder()
                .url("http://"+api_ip+":3000/users/updateuser")
                .header("Authorization", "BEARER " +token)
                .patch(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(WelcomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("demo", "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String returnResponse =response.body().string();
                try {
                    JSONObject jsonObject= new JSONObject(returnResponse);
                    Log.d("demo", "onResponse: "+ jsonObject.toString());
                    message=jsonObject.getString("message");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getUserInfo();
                        email.setFocusable(false);
                        dateofbirth.setFocusable(false);
                        name.setFocusable(false);
                        age.setFocusable(false);
                        address.setFocusable(false);

                        findViewById(R.id.btnUpdate).setVisibility(View.INVISIBLE);
                        findViewById(R.id.buttonEditData).setVisibility(View.VISIBLE);

                    }
                });
                Log.d("demo", "onResponse: " + message);

            }
        });


    }
    private void getUserInfo(){
        Request request = new Request.Builder()
                .url("http://"+api_ip+":3000/users/userinfo")
                .header("Authorization", "BEARER " +token)
                .build();


       /* JsonObject jsonObject= new JsonObject();
        jsonObject.addProperty("email",userId);
        jsonObject.addProperty("password",password);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody formBody = RequestBody.create(JSON,jsonObject.toString());

                 RequestBody formBody = new FormBody.Builder()
                  .add("email", userId)
                  .add("password", password)
                  .build();
        Request request = new Request.Builder()
                .url("http://52.23.253.255:3000/users/login")

                          url("ec2-18-205-114-207.compute-1.amazonaws.com/api/signUp")
                .post(formBody)
                .build();
        */
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("failure", "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String returnResponse =response.body().string();
                Log.d("demo", "onResponse: Login Success " + returnResponse);
//                  Toast.makeText(MainActivity.this,"Welcome TO APP",Toast.LENGTH_SHORT).show();

                 Gson gson = new Gson();
                  Users tokenResponse = gson.fromJson(returnResponse,Users.class);
                 userinfo=tokenResponse.user;
            //    final ArrayList<Users.User> userinfo = tokenResponse.user;
                runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                      email.setText(userinfo.getEmail());
                      name.setText(userinfo.getName());
                      age.setText(userinfo.getAge());
                      dateofbirth.setText(userinfo.getDateofbirth());
                      address.setText(userinfo.getAddress());

                  }
                });
                //  runOnUiThread(new);
                //Log.d("demo", "onResponse: " +tokenResponse.toString());
                //  Users.User user = tokenResponse.user;
                 // user.set_id();
                /*
                  user.set_id(password);
                  user.setUserName(tokenResponse.getUserName());
                  user.setAge(Integer.parseInt(tokenResponse.getAge()));
                  user.setWeight(Double.parseDouble(tokenResponse.getWeight()));
                  Intent mainPage = new Intent(MainActivity.this,WelcomeActivity.class);
                 mainPage.putExtra("userInfo",user);
                startActivity(mainPage);
                finish();*/
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout_menu_item:
                Toast.makeText(getApplicationContext(),"You pressed logout",Toast.LENGTH_SHORT).show();
                token=null;
                Intent intent= new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

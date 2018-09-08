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

import java.io.IOException;
import java.util.ArrayList;

//import inclass1.group3.group3_inclass01.data.Person;
import inclass1.group3.group3_inclass01.data.Users;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WelcomeActivity extends AppCompatActivity {
    EditText name,email,pwd,age,address,dateofbirth;


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
       /* age = findViewById(R.id.editAge);
        weight = findViewById(R.id.editWeight);
        nameField = findViewById(R.id.textView8);
        ageField = findViewById(R.id.textView10);
        weightField = findViewById(R.id.textView11);*/
       getUserInfo();
        if(getIntent().getExtras()!=null){
            if(getIntent().getExtras().getSerializable("token")!=null){
            token =getIntent().getExtras().getString("token");
          //      Person loggedInUser = new Person();
            //    loggedInUser = (Person)getIntent().getExtras().getSerializable("userInfo");
              //  age.setText(loggedInUser.getAge());
                //weight.setText(String.valueOf(loggedInUser.getWeight()));
                //nameField.setText("Welcome "+ loggedInUser.getUserName());
            }
        }

        findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   ageField.setVisibility(View.VISIBLE);
                weightField.setVisibility(View.VISIBLE);
                age.setVisibility(View.VISIBLE);
                weight.setVisibility(View.VISIBLE);
                findViewById(R.id.btnUpdate).setVisibility(View.VISIBLE);*/
            }
        });
    }

    private void getUserInfo(){
        Request request = new Request.Builder()
                .url("http://52.23.253.255:3000/users/userinfo")
                .header("Authorization", "BEARER " + token)
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
                final ArrayList<Users.User> userinfo = tokenResponse.user;
                email.setText(userinfo.get(0).getEmail());

                name.setText(userinfo.get(0).getName());
                age.setText(userinfo.get(0).getAge());
                dateofbirth.setText(userinfo.get(0).getDateofbirth());
                address.setText(userinfo.get(0).getAddress());
                //  runOnUiThread(new);
                Log.d("demo", "onResponse: " +tokenResponse.toString());
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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

package inclass1.group3.group3_inclass01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

//import inclass1.group3.group3_inclass01.data.Person;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

  public class MainActivity extends AppCompatActivity {

    EditText userLogin,userPwd;
    private final OkHttpClient client = new OkHttpClient();
      JSONObject tokenobject;
      String token;

      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userLogin = findViewById(R.id.txtEmail);
        userPwd = findViewById(R.id.txtPwd);

        findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(registerIntent);
                finish();
            }
        });

        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userLogin.getText().toString().isEmpty() && userPwd.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"User credentials Empty",Toast.LENGTH_SHORT).show();

                }else{
                    performLogin(userLogin.getText().toString(),userPwd.getText().toString());
                }
            }
        });

        findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
                finish();
            }
        });
    }

      private void performLogin(final String userId, final String password) {
  JsonObject jsonObject= new JsonObject();
        jsonObject.addProperty("email",userId);
          jsonObject.addProperty("password",password);
          MediaType JSON = MediaType.parse("application/json; charset=utf-8");

          RequestBody formBody = RequestBody.create(JSON,jsonObject.toString());

                 /*RequestBody formBody = new FormBody.Builder()
                  .add("email", userId)
                  .add("password", password)
                  .build();*/
          final Request request = new Request.Builder()
                  .url("http://52.23.253.255:3000/users/login")

//                          url("ec2-18-205-114-207.compute-1.amazonaws.com/api/signUp")
                  .post(formBody)
                  .build();
          client.newCall(request).enqueue(new Callback() {
              @Override
              public void onFailure(Call call, IOException e) {
                  Log.d("failure", "onFailure: " + e.getMessage());
              }
              @Override
              public void onResponse(Call call, Response response) throws IOException {
                 // String returnResponse =response.body().get("");
                  try {
                    tokenobject  = new JSONObject(String.valueOf(response));
                      token  = tokenobject.getString("token");

                  } catch (JSONException e) {
                      e.printStackTrace();
                  }

             //     String token=  returnResponse.split("token:",1).toString();
                  Log.d("demo", "onResponse: Login Success " + response.body().string());
//                  Toast.makeText(MainActivity.this,"Welcome TO APP",Toast.LENGTH_SHORT).show();
               //   Gson gson = new Gson();
                 //   gson.fromJson(returnResponse,);
                 /* Gson gson = new Gson();
                  TokenResponse tokenResponse = gson.fromJson(returnResponse,TokenResponse.class);
                  Log.d("demo", "onResponse: " +tokenResponse.toString());
                  Person user = new Person();
                  user.setUserId(userId);
                  user.setPassword(password);
                  user.setUserName(tokenResponse.getUserName());
                  user.setAge(Integer.parseInt(tokenResponse.getAge()));
                  user.setWeight(Double.parseDouble(tokenResponse.getWeight()));
                  */Intent mainPage = new Intent(MainActivity.this,WelcomeActivity.class);
                 mainPage.putExtra("token",token);
                  startActivity(mainPage);
                  finish();
              }
          });
      }
  }

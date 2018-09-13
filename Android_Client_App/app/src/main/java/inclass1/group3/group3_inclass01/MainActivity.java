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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

  public class MainActivity extends AppCompatActivity {

      static String api_ip="52.204.170.1";
    EditText userLogin,userPwd;
    private final OkHttpClient client = new OkHttpClient();
      JSONObject tokenobject;
      String message ;
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

          final Request request = new Request.Builder()
                  .url("http://"+api_ip+":3000/users/login")

                  .post(formBody)
                  .build();
          client.newCall(request).enqueue(new Callback() {
              @Override
              public void onFailure(Call call, IOException e) {
                  Log.d("failure", "onFailure: " + e.getMessage());
              }
              @Override
              public void onResponse(Call call, Response response) throws IOException {
                 int status_code =response.code();
                  String s=response.body().string();
                  if(status_code==200)
                 {
                  try {
                    tokenobject  = new JSONObject(s);
                      token  = tokenobject.getString("token");
                      Log.d("testtest",token);

                  } catch (JSONException e) {
                      e.printStackTrace();
                  }

                  Intent mainPage = new Intent(MainActivity.this,WelcomeActivity.class);
                 mainPage.putExtra("token",token);
                  startActivity(mainPage);
                  finish();
              }

              else
              {   try {
                      tokenobject= new JSONObject(s);

                     message=tokenobject.getString("message");
                  } catch (JSONException e) {
                      e.printStackTrace();
                  }

                  runOnUiThread(new Runnable() {
                      @Override
                      public void run() {
                          Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                      }
                  });
              }
              }
          });
      }
  }

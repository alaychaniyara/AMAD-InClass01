package inclass1.group3.group3_inclass01;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class RegisterActivity extends AppCompatActivity {

    public static final int REGISTER_USER = 100;
  String message ;

    private final OkHttpClient client = new OkHttpClient();

    public ProgressDialog progressDialog;
    String token;

    EditText name,email,pwd,age,address,dateofbirth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.txtname);
        name = findViewById(R.id.txtuserid);
        pwd = findViewById(R.id.txtpwd);
        age = findViewById(R.id.txtage);
        dateofbirth = findViewById(R.id.txtDOB);
        address=findViewById(R.id.editTextAddress);

        Toast.makeText(getApplicationContext(),"Registration form",Toast.LENGTH_SHORT).show();

        findViewById(R.id.btnCreateUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              TokenResponse newPerson = new TokenResponse();
               newPerson.setEmail(email.getText().toString());
               newPerson.setPassword(pwd.getText().toString());
                newPerson.setName(name.getText().toString());
                newPerson.setAge(age.getText().toString());
                newPerson.setDateofbirth(dateofbirth.getText().toString());
                newPerson.setAddress(address.getText().toString());
               //make the api call here...
                progressDialog = new ProgressDialog(RegisterActivity.this);
                progressDialog.setTitle("Registering user... please wait");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                Log.d("123456",newPerson.toString());
                performRegister(newPerson);
            }
        });

    }

    private void performRegister(final TokenResponse newPerson) {

        JsonObject jsonObject= new JsonObject();
        jsonObject.addProperty("email",newPerson.getEmail());
        jsonObject.addProperty("password",newPerson.getPassword());
        jsonObject.addProperty("name",newPerson.getName());
        jsonObject.addProperty("age",newPerson.getAge());
        jsonObject.addProperty("dateofbirth",newPerson.getDateofbirth());
        jsonObject.addProperty("address",newPerson.getAddress());
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody formBody = RequestBody.create(JSON,jsonObject.toString());

        Request request = new Request.Builder()
                .url("http://"+api_ip+":3000/users/signup")
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("demo", "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String returnResponse =response.body().string();
                try {
                    JSONObject jsonObject= new JSONObject(returnResponse);
                    message=jsonObject.getString("message");
                    token  = jsonObject.getString("token");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("demo", "onResponse: " + returnResponse);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this,message, Toast.LENGTH_SHORT).show();

                        Intent welcomePage = new Intent(RegisterActivity.this,WelcomeActivity.class);
                        welcomePage.putExtra("token",token);
                        startActivity(welcomePage);
                        finish();
                    }
                });
            }
        });
    }

}

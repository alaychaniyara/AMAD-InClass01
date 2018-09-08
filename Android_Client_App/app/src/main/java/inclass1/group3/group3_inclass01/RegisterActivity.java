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

public class RegisterActivity extends AppCompatActivity {

    public static final int REGISTER_USER = 100;

    private final OkHttpClient client = new OkHttpClient();

    public ProgressDialog progressDialog;

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
               Users.User newPerson = new Users.User();
               newPerson.setEmail(email.toString());
               newPerson.setPassword(pwd.toString());
                newPerson.setName(name.toString());
                newPerson.setAge(age.toString());
                newPerson.setDateofbirth(dateofbirth.toString());
                newPerson.setAddress(address.toString());
              //  newPerson.(name.getText().toString());
               //make the api call here...
                progressDialog = new ProgressDialog(RegisterActivity.this);
                progressDialog.setTitle("Registering user... please wait");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                performRegister(newPerson);
            }
        });

    }

    private void performRegister(final Users.User newPerson) {

        JsonObject jsonObject= new JsonObject();
        jsonObject.addProperty("email",newPerson.getEmail());
        jsonObject.addProperty("password",newPerson.getPassword());
        jsonObject.addProperty("name",newPerson.getName());
        jsonObject.addProperty("age",newPerson.getAge());
        jsonObject.addProperty("dateofbirth",newPerson.getDateofbirth());
        jsonObject.addProperty("address",newPerson.getAddress());
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody formBody = RequestBody.create(JSON,jsonObject.toString());

     /*   RequestBody formBody = new FormBody.Builder()
                .add("userName", newPerson.getUserName())
                .add("userId", newPerson.getUserId())
                .add("password", newPerson.getPassword())
                .add("age", String.valueOf(newPerson.getAge()))
                .add("weight", String.valueOf(newPerson.getWeight()))
                .build();*/
        Request request = new Request.Builder()
                .url("http://52.23.253.255:3000/users/signup")
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
                String returnResponse =response.body().string();
                Log.d("demo", "onResponse: User Created Success " + returnResponse);
                Gson gson = new Gson();
                TokenResponse tokenResponse = gson.fromJson(returnResponse,TokenResponse.class);
                Log.d("demo", "onResponse: " +tokenResponse.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "Signup Successfull please Login", Toast.LENGTH_SHORT).show();
                    }
                });
                Intent welcomePage = new Intent(RegisterActivity.this,MainActivity.class);
             //   welcomePage.putExtra("userInfo",newPerson);
                startActivity(welcomePage);
                finish();
            }
        });
    }

}

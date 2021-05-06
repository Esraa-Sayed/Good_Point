package com.helloworld.goodpoint.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.helloworld.goodpoint.R;
import com.helloworld.goodpoint.pojo.RegUser;
import com.helloworld.goodpoint.pojo.Token;
import com.helloworld.goodpoint.pojo.User;
import com.helloworld.goodpoint.retrofit.ApiClient;
import com.helloworld.goodpoint.retrofit.ApiInterface;
import com.helloworld.goodpoint.retrofit.Decode;
import com.helloworld.goodpoint.retrofit.TokenManager;
import com.helloworld.goodpoint.ui.forgetPasswordScreens.MakeSelection;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener {
    TokenManager tokenManager;
    private EditText Email, Pass;
    private TextView ForgetPass;
    private CheckBox RememberMe;
    private Button Sigin, CreateNewAccount;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    // "(?=.*[a-zA-Z])" +      //any letter
                    // "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    // "(?=\\S+$)" +           //no white spaces
                    ".{8,}" +               //at least 8 characters
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        //getActionBar().hide();
        setContentView(R.layout.activity_signin);
        inti();
        String f;

    }

    protected void inti() {
        Email = findViewById(R.id.email);
        Pass = findViewById(R.id.pass);
        ForgetPass = findViewById(R.id.forgetPass);
        Sigin = findViewById(R.id.signin);
        CreateNewAccount = findViewById(R.id.NewAccount);
        RememberMe = findViewById(R.id.checkbox);
        Sigin.setOnClickListener(this);
        CreateNewAccount.setOnClickListener(this);
        ForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), MakeSelection.class);
                startActivityForResult(myIntent, 0);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signin:
                if (validAccount() && validatePassword()) {
                        //loginUser(RememberMe.isChecked());
                        startActivity(new Intent(SigninActivity.this, HomeActivity.class));
                } else
                    Toast.makeText(this, "Invalid account", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SigninActivity.this, HomeActivity.class));
                break;


            case R.id.NewAccount:
                startActivity(new Intent(this, SignupActivity.class));
                break;
        }
    }

    private boolean validatePassword() {
        String passwordInput = Pass.getText().toString().trim();
        if (passwordInput.isEmpty()) {
            Pass.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            Pass.setError("password to weak!");
            /*if(!passwordInput.matches("[0-9]+"))
                Pass.setError("must contain at least 1 digit");
            else if(!passwordInput.matches("[a-z]+"))
                Pass.setError("must contain at least 1 lower case letter");
            else if(!passwordInput.matches("[A-Z]+"))
                Pass.setError("must contain at least 1 upper case letter");
            else
                Pass.setError("must contain at least 8 characters");*/
            return false;
        } else {
            Pass.setError(null);
            return true;
        }
    }

    private boolean validateEmail() {
        String emailInput = Email.getText().toString().trim();
        if (emailInput.isEmpty()) {
            Email.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            Email.setError("Please enter a valid email address");
            return false;
        } else {
            Email.setError(null);
            return true;
        }
    }

    private boolean validAccount() {
        //check validation
        if (validateEmail()) return true;
        else return false;
    }


    public void loginUser(boolean Remember) {
        String emailInput = Email.getText().toString().trim();
        String passwordInput = Pass.getText().toString().trim();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Token> call = apiInterface.getToken(emailInput, passwordInput);

        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                String token = response.body().getAccess();
                if(Remember)
                {
                    new PrefManager(getApplicationContext()).setLogin(response.body().getRefresh());
                }

                Call<JsonObject> call2 = apiInterface.getData("Bearer " + token);
                call2.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response.body().toString()).getJSONObject("user");
                            String id = jsonObject.getString("id");
                            String name = jsonObject.getString("username");
                            String email = jsonObject.getString("email");
                            String phone = jsonObject.getString("phone");
                            String city = jsonObject.getString("city");
                            String birthdate = jsonObject.getString("birthdate");

                            Intent intent = new Intent(SigninActivity.this, HomeActivity.class);
                            User.getUser().setId(id);
                            User.getUser().setUsername(name);
                            User.getUser().setEmail(email);
                            User.getUser().setPhone(phone);
                            User.getUser().setCity(city);
                            User.getUser().setBirthdate(birthdate);

                            startActivity(intent);
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(SigninActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }


                @Override
            public void onFailure(Call<Token> call, Throwable t) {
                    Toast.makeText(SigninActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }






}
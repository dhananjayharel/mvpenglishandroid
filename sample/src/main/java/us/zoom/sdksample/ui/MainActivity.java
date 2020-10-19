package us.zoom.sdksample.ui;

import android.app.Activity;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import us.zoom.sdksample.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import org.json.JSONObject;

import java.io.IOException;

import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

public class MainActivity extends Activity {

    private Button btnLogin,btnReg;
    private TextView textView;
    private ProgressBar spinner;
    private static EditText email;
    private static EditText password;
    private static StringBuilder jsonBuilder;
    private AwesomeValidation awesomeValidation;
    public String postUrl= "http://www.skillstack.com/openenglishapi/Users/login";
    public String userUrl="http://www.skillstack.com/openenglishapi/Users/";
    public String postBody="{\n" +
            "    \"email\": \"harel.dhananjay+411@gmail.com\",\n" +
            "    \"password\": \"123456\"\n" +
            "}";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCenter.start(getApplication(), "11cc0f9e-94fa-4fe3-a1fe-790eb5d87d5b",
                Analytics.class, Crashes.class);
        //Check if user is logged in
        SharedPreferences prfs = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        String isLoggedIn = prfs.getString("isLoggedIn", "");

        if (isLoggedIn.equals("true")) {
               //finish();
               startActivity(new Intent(this, HomeActivity.class));
        }
        else{
                awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
                setContentView(R.layout.login_layout);

                btnLogin = (Button) findViewById(R.id.btnLogin);
                btnReg = (Button) findViewById(R.id.btnRegisterUser);
                textView = (TextView) findViewById(R.id.programmr);
                spinner = (ProgressBar) findViewById(R.id.loginprogressBar);
                // spinner.setVisibility(View.GONE);

                btnReg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        register();
                    }
                });

                btnLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loginSuccess();
                    }
                });
                Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.movetop);
                textView.startAnimation(myAnim);
                email = (EditText) findViewById(R.id.userEmail);
                password = (EditText) findViewById(R.id.userPassword);
                //  awesomeValidation.addValidation(this, R.id.userPassword, "^[A-Za-z\\s1]$", R.string.nameerror);
                awesomeValidation.addValidation(this, R.id.userEmail, Patterns.EMAIL_ADDRESS, R.string.emailerror);

        }


    }
    private void register(){
        Intent intent = new Intent(this, RegisterUserActivity.class);
        startActivity(intent);
    }
    private void loginSuccess() {
        // TODO: go to home screen
        if (email.getText().equals("demo")) {
            Log.d("BYPASS", "Bypass login for demo");
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        } else{

            try {

                spinner.setVisibility(View.VISIBLE);
                System.out.println("REQ");
                if (awesomeValidation.validate()) {
                    jsonBuilder = new StringBuilder();
                    jsonBuilder.append("{");
                    jsonBuilder.append("\"email\":\"" + email.getText() + "\"");
                    jsonBuilder.append(",\"password\":\"" + password.getText() + "\"}");
                    Log.d("LOGIN REQ=", jsonBuilder.toString());

                    postRequest(postUrl, jsonBuilder.toString());
                    //process the data further
                } else {
                    hideProgress();
                }

            } catch (Exception e) {
                e.printStackTrace();
                hideProgress();
            }
        // Intent intent = new Intent(this, InitAuthSDKActivity.class);
        //  startActivity(intent);
    }
    }

    //Login
    void postRequest(String postUrl,String postBody) throws IOException {
      Log.d("Login","in postreq"+postBody);
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, postBody);

        Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                System.out.println("LOGIN SECCESS!!!"+res);
                System.out.println("LOGIN SECCESS!!!" + response.code());

                System.out.println("LOGIN SECCESS res!!!"+res);

                try {
                if (response.code() == 200){

                        JSONObject obj = new JSONObject(res);

                    SharedPreferences preferences = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    String id = obj.getString("id");
                    String userId= obj.get("userId").toString();
                    editor.putString("id",id);
                    editor.putString("userId",userId);
                    editor.putString("isLoggedIn","true");
                    editor.commit();
                    userFetchRequest(id,userId);
                    //hideProgress();
                   // Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                   // startActivity(intent);

            }
                else{
                    System.out.println("LOGIN SECCESS res!!!"+res);
                    JSONObject obj = new JSONObject(res);
                    System.out.println("LOGIN SECCESS resobj!!!"+obj);
                    String err = obj.getJSONObject("error").getString("message");
                    System.out.println("LOGIN SECCESS!!!"+err);

                    showDialog("Oops! "+err);

                }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
    public String toJsonString(){
    return "";
    }

    void createDialog(final String message){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        //
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }

    public void showDialog(final String message){

        runOnUiThread(new Runnable() {
            public void run() {

                spinner.setVisibility(View.GONE);
                createDialog(message);
            }
        });


    }
    public void hideProgress(){
        runOnUiThread(new Runnable() {
            public void run() {

                spinner.setVisibility(View.GONE);
            }
        });

    }

    public void saveUserFullname(String token,String userId){




    }

    void userFetchRequest(String token,String userId) throws IOException {
        userUrl = userUrl+userId;
        Log.d("Login","in postreq");
        OkHttpClient client = new OkHttpClient();



        Request request = new Request.Builder()
                .url(userUrl)
                .addHeader("Authorization",token)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                System.out.println("LOGIN SECCESS!!!"+res);
                System.out.println("LOGIN SECCESS!!!" + response.code());

                System.out.println("LOGIN SECCESS res!!!"+res);

                try {
                    if (response.code() == 200){

                        JSONObject obj = new JSONObject(res);

                        SharedPreferences preferences = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        String fullName= obj.getString("fullname");
                        editor.putString("fullname",fullName);
                        editor.commit();

                        hideProgress();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);

                    }
                    else{
                        System.out.println("LOGIN SECCESS res!!!"+res);
                        JSONObject obj = new JSONObject(res);
                        System.out.println("LOGIN SECCESS resobj!!!"+obj);
                        String err = obj.getJSONObject("error").getString("message");
                        System.out.println("LOGIN SECCESS!!!"+err);

                        showDialog("Oops! "+err);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }



}

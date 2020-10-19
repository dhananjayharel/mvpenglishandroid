package us.zoom.sdksample.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import us.zoom.sdksample.R;

public class RegisterUserActivity extends Activity {

    private Button btnLogin,btnBack;
    private ProgressBar progress;
  //  private TextView textView;
    private static EditText email;
    private AwesomeValidation awesomeValidation;
    private static EditText password,confirmPassword;
    private static EditText retypePassword;
    private static EditText fullName;
    private static StringBuilder jsonBuilder;
    public String postUrl= "http://www.skillstack.com/openenglishapi/Users";
    public String postBody="{\n" +
            "    \"email\": \"harel.dhananjay+411@gmail.com\",\n" +
            "    \"password\": \"123456\"\n" +
            "}";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        setContentView(R.layout.user_register_layout);

        btnLogin = (Button) findViewById(R.id.btnUserRegisterUser);
        btnBack = (Button) findViewById(R.id.btnUserBack);
      //  textView = (TextView) findViewById(R.id.programmr);
        progress = (ProgressBar) findViewById(R.id.regprogressBar);
         email = (EditText) findViewById(R.id.regEmail);
         password = (EditText) findViewById(R.id.regPassword);
         confirmPassword = (EditText) findViewById(R.id.confirmRegPassword);
         fullName = (EditText) findViewById(R.id.regFullName);

        awesomeValidation.addValidation(this, R.id.regEmail, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.regFullName, "[a-zA-Z\\s]+", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.regPassword, "[a-zA-Z\\s\\S]+", R.string.pwderror);
        awesomeValidation.addValidation(this, R.id.confirmRegPassword, "[a-zA-Z\\s\\S]+", R.string.nameerror);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginSuccess();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLogin();
            }
        });
        //Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.movetop);
      //  textView.startAnimation(myAnim);

    }

    private void loginSuccess() {
        // TODO: go to home screen
        try {
            System.out.println("REQ");
            progress.setVisibility(View.VISIBLE);
            /*
            companyname: "sffafdff"
companysize: "100-200"
confirmPassword: "123456"
country: "AM"
email: "dj+332@programmr.com"
fullname: "dhafa asf"
password: "123456"
            *
            * */
            jsonBuilder = new StringBuilder();

            String pwd = password.getText().toString();
            String confirmPwd= confirmPassword.getText().toString();

            if (!pwd.equals(confirmPwd)) {
                showDialog("Passwords doesnt match.");
                progress.setVisibility(View.GONE);
            } else{

                if (awesomeValidation.validate()) {
                    jsonBuilder.append("{");
                    jsonBuilder.append("\"email\":\"" + email.getText() + "\"");
                    jsonBuilder.append(",\"password\":\"" + password.getText() + "\"");
                    jsonBuilder.append(",\"confirmPassword\":\"" + password.getText() + "\"");
                    jsonBuilder.append(",\"companysize\":\"100-200\"");
                    jsonBuilder.append(",\"companyname\":\"notrequired\"");
                    jsonBuilder.append(",\"fullname\":\"" + fullName.getText() + "\"}");
                    Log.d("REG REQ=", jsonBuilder.toString());
                    postRequest(postUrl, jsonBuilder.toString());
                }else{
                    hideProgress();
                }


        }
        }catch (Exception e){
            hideProgress();
            e.printStackTrace();
        }
      // Intent intent = new Intent(this, InitAuthSDKActivity.class);
      //  startActivity(intent);
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
                e.printStackTrace();
                hideProgress();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try{
                String res = response.body().string();
                System.out.println("REG SECCESS!!!" + res);
                System.out.println("REG SECCESS!!!" + (response.code() == 200));
                if (response.code() == 200) {
                    showDialog("Registration was successfull, Please login.");
                } else {
                    System.out.println("REG SECCESS res!!!" + res);
                    JSONObject obj = new JSONObject(res);
                    System.out.println("REG SECCESS resobj!!!" + obj);
                    String err = obj.getJSONObject("error").getString("message");
                    System.out.println("REG SECCESS!!!" + err);
                    String filteredMessage = err.substring(err.indexOf("Details:")+8);
                    showDialog("Oops, "+filteredMessage);
                }

            }catch(Exception e){
                e.printStackTrace();
            }
            }
        });
    }
    public String toJsonString(){
    return "";
    }

    public void showDialog(final String message){

        runOnUiThread(new Runnable() {
            public void run() {

                progress.setVisibility(View.GONE);
                createDialog(message);
            }
        });


    }

    public void hideProgress(){
        runOnUiThread(new Runnable() {
            public void run() {

                progress.setVisibility(View.GONE);
            }
        });

    }

    public void goToLogin(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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




}

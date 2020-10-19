package us.zoom.sdksample.quiz2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Normalizer;

import us.zoom.sdksample.R;

public class FormActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz2_form);
        String imageTag="123";
        //Getting intent values
        Intent intent = getIntent();
        imageTag = intent.getStringExtra("mImageTag");
        Log.d("INFO",imageTag);
        //Prevent keyboard from popping up when starting
        //Setting view var
        ImageView mQuizImage;
        mQuizImage = (ImageView) findViewById(R.id.quizimage);
        TextView quizCaption = (TextView) findViewById(R.id.quizcaption);
        String caption="NA";
        if(imageTag.equals("howmuchpasta")){

            caption="How much pasta?";
        }
        else
        if(imageTag.equals("howmanypounds")){
            caption="How many pounds?";

        }
        else
        if(imageTag.equals("afewgrapes")){
            caption="A Few Grapes";
        }

        quizCaption.setText(caption);

        //Update image view
        mQuizImage.setImageResource(getResources().getIdentifier(imageTag, "drawable", getPackageName()));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }
    //Start Quiz Button
    public void startBtn(View view){
        EditText mNameView = (EditText)findViewById(R.id.name_edittext);
        String mName = "Guest";

        if (mName.equals("")){
            //If empty string, make a toast message
            String enter_name = getString(R.string.txt_pls_enter_ur_name);
            Toast.makeText(FormActivity.this,
                    enter_name,
                    Toast.LENGTH_SHORT).show();
        }else{
            //Go to quiz
            goToQuiz(mName);
        }
    }

    //Go to Quiz Intent
    private void goToQuiz(String mname) {
        Intent intent_main = new Intent(FormActivity.this, MainActivity.class);
        intent_main.putExtra("mName", mname);
        startActivity(intent_main);
    }

    //Prevent Back Button
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            //do nothing
        }
        return false;
    }
}

package us.zoom.sdksample.quiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import us.zoom.sdksample.R;

public class DeveloperActivity extends Activity {
    Button btnRestart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);

        btnRestart = (Button) findViewById(R.id.button4);


        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in2 = new Intent(getApplicationContext(),QuizActivity.class);
                startActivity(in2);
            }
        });
    }
}

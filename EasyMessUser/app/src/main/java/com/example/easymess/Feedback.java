package com.example.easymess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Feedback extends AppCompatActivity {

    String curruser, meal;
    EditText Subject, Body;
    Button SubmitFB;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference Feedback = db.getReference("Feedback");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Intent intent = getIntent();
        curruser = intent.getStringExtra("currUser");
        meal = intent.getStringExtra("Meal");
        Subject = (EditText)findViewById(R.id.fbsubject);
        Body = (EditText)findViewById(R.id.fbbody);
        SubmitFB = (Button)findViewById(R.id.submitfb);

        SubmitFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = Subject.getText().toString();
                String b = Body.getText().toString();

                if (b.isEmpty())
                {
                    Body.setError("Body is Required!");
                    Body.requestFocus();
                    return;
                }
                if (s.isEmpty())
                {
                    Subject.setError("Subject is Required!");
                    Subject.requestFocus();
                    return;
                }

                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String date = df.format(Calendar.getInstance().getTime());
                Feedback.child(date).child(curruser).child(meal).child("Body").setValue(b);
                Feedback.child(date).child(curruser).child(meal).child("Subject").setValue(s);
                Toast.makeText(Feedback.this, "Feedback Registered Successfully", Toast.LENGTH_LONG).show();
                new CountDownTimer(1000, 1000) {
                    public void onFinish() {
                        Intent intent1 = new Intent(Feedback.this, MainActivity.class);
                        startActivity(intent1);
                    }

                    public void onTick(long millisUntilFinished) {
                        // millisUntilFinished    The amount of time until finished.
                    }
                }.start();


            }
        });



    }
}

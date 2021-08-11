package com.example.easymess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button bmenu, Complain, Logout, BFB, LFB, DFB;
    TextView Today, CurrMenu, Meal, EggC, ChickC;
    String regnumber = "0";
    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseDatabase db;
    DatabaseReference menu, status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EggC = (TextView)findViewById(R.id.eggc);
        ChickC = (TextView)findViewById(R.id.chickc);
        BFB = findViewById(R.id.bfb);
        LFB = findViewById(R.id.lfb);
        DFB = findViewById(R.id.dfb);
        bmenu = (Button)findViewById(R.id.menu);
        Complain = (Button)findViewById(R.id.complain);
        Logout = (Button)findViewById(R.id.logoutbtn);
        Today = (TextView)findViewById(R.id.today);
        CurrMenu = (TextView)findViewById(R.id.curr_menu);
        Meal = (TextView)findViewById(R.id.tvmeal);
        mAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = mAuth.getCurrentUser();

                if (user == null)
                {
                    Intent intent = new Intent(MainActivity.this, Login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

            }
        };

        mAuth.addAuthStateListener(authStateListener);

        db = FirebaseDatabase.getInstance();
        DatabaseReference UserRef = db.getReference("Users").child(mAuth.getUid().toString());


        menu = db.getReference("Menu");
        Calendar currentTime = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("EEEE, dd-MMM-yyyy");
        String date = df.format(currentTime.getTime());
        Today.setText(date);
        updateMenu();



        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                regnumber = dataSnapshot.getValue(String.class);
                //Toast.makeText(MainActivity.this, regnumber, Toast.LENGTH_LONG).show();

                DatabaseReference UserInfo = db.getReference("User Details").child(regnumber);
                UserInfo.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                       if (dataSnapshot1.exists()) {

                           status = db.getReference("Today Status").child(regnumber);
                           TextView tv = (TextView)findViewById(R.id.user);
                           tv.setText("Hi "+ dataSnapshot1.child("name").getValue().toString());

                           status.addValueEventListener(new ValueEventListener() {
                               @Override
                               public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                                   if (dataSnapshot2.child("Breakfast").child("Status").getValue().toString().equals("Yes"))
                                   {
                                       BFB.setClickable(true);
                                       BFB.setBackgroundColor(Color.GREEN);

                                   }
                                   else
                                   {
                                       BFB.setClickable(false);
                                       BFB.setBackgroundColor(Color.LTGRAY);
                                   }
                                   if (dataSnapshot2.child("Lunch").child("Status").getValue().toString().equals("Yes"))
                                   {
                                       LFB.setClickable(true);
                                       LFB.setBackgroundColor(Color.GREEN);
                                   }
                                   else
                                   {
                                       LFB.setClickable(false);
                                       LFB.setBackgroundColor(Color.LTGRAY);
                                   }
                                   if (dataSnapshot2.child("Dinner").child("Status").getValue().toString().equals("Yes"))
                                   {
                                       DFB.setClickable(true);
                                       DFB.setBackgroundColor(Color.GREEN);
                                   }
                                   else
                                   {
                                       DFB.setClickable(false);
                                       DFB.setBackgroundColor(Color.LTGRAY);
                                   }

                                   Calendar time = Calendar.getInstance();
                                   int hour = time.get(Calendar.HOUR_OF_DAY);
                                   if (hour >= 0 && hour < 10)
                                   {
                                       EggC.setText(dataSnapshot2.child("Breakfast").child("Extra").child("Egg").getValue().toString());
                                       ChickC.setText(dataSnapshot2.child("Breakfast").child("Extra").child("Chicken").getValue().toString());
                                   }
                                   else if (hour >= 10 && hour < 4)
                                   {
                                       EggC.setText(dataSnapshot2.child("Lunch").child("Extra").child("Egg").getValue().toString());
                                       ChickC.setText(dataSnapshot2.child("Lunch").child("Extra").child("Chicken").getValue().toString());
                                   }
                                   else
                                   {
                                       EggC.setText(dataSnapshot2.child("Dinner").child("Extra").child("Egg").getValue().toString());
                                       ChickC.setText(dataSnapshot2.child("Dinner").child("Extra").child("Chicken").getValue().toString());
                                   }
                               }

                               @Override
                               public void onCancelled(@NonNull DatabaseError databaseError) {

                               }
                           });

                       }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        bmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, view_menu.class);
                startActivity(intent);

            }
        });

        Complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Complain.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("currUser", regnumber);
                startActivity(intent);
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(MainActivity.this, Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        BFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Feedback.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("currUser", regnumber);
                intent.putExtra("Meal", "Breakfast");
                startActivity(intent);
            }
        });

        DFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Feedback.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("currUser", regnumber);
                intent.putExtra("Meal", "Dinner");
                startActivity(intent);
            }
        });

        LFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Feedback.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("currUser", regnumber);
                intent.putExtra("Meal", "Lunch");
                startActivity(intent);
            }
        });

    }

    private void updateMenu()
    {
        Calendar currentTime = Calendar.getInstance();
        SimpleDateFormat df1 = new SimpleDateFormat("EEEE");
        final String day = df1.format(currentTime.getTime());
        final int timeOfDay = currentTime.get(Calendar.HOUR_OF_DAY);
        menu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(timeOfDay >= 0 && timeOfDay <= 10){
                    Meal.setText("Breakfast Menu");
                    CurrMenu.setText(dataSnapshot.child(day).child("Breakfast").getValue().toString());
                }else if(timeOfDay > 10 && timeOfDay < 16){

                    Meal.setText("Lunch Menu");
                    CurrMenu.setText(dataSnapshot.child(day).child("Lunch").getValue().toString());
                }else if(timeOfDay >= 16 && timeOfDay < 24){
                    Meal.setText("Dinner Menu");
                    CurrMenu.setText(dataSnapshot.child(day).child("Dinner").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        mAuth.removeAuthStateListener(authStateListener);
    }
}

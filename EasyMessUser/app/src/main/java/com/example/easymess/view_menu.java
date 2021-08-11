package com.example.easymess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class view_menu extends AppCompatActivity{

    Spinner spinner;
    String currday;
    TextView Bmenu, Lmenu, Dmenu;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference menu = db.getReference("Menu");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_menu);

        Bmenu = (TextView)findViewById(R.id.bmenu);
        Lmenu = (TextView)findViewById(R.id.lmenu);
        Dmenu = (TextView)findViewById(R.id.dmenu);
        spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.day, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currday = parent.getItemAtPosition(position).toString();
                menu.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(currday))
                        {
                            Bmenu.setText(dataSnapshot.child(currday).child("Breakfast").getValue().toString());
                            Lmenu.setText(dataSnapshot.child(currday).child("Lunch").getValue().toString());
                            Dmenu.setText(dataSnapshot.child(currday).child("Dinner").getValue().toString());
                        }
                        else
                        {
                            Bmenu.setText(""); Lmenu.setText(""); Dmenu.setText("");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}

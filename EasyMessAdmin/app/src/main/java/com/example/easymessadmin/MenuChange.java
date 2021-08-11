package com.example.easymessadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuChange extends AppCompatActivity {

    Spinner spinner;
    EditText Bmenu, Lmenu, Dmenu;
    Button Changemenu;
    String currday;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference menu = db.getReference("Menu");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_change);

        Bmenu = (EditText)findViewById(R.id.bmenu);
        Lmenu = (EditText)findViewById(R.id.lmenu);
        Dmenu = (EditText)findViewById(R.id.dmenu);
        Changemenu = (Button)findViewById(R.id.change);
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
                            Bmenu.setText("");
                            Lmenu.setText("");
                            Dmenu.setText("");
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

        Changemenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.child(currday).child("Breakfast").setValue(Bmenu.getText().toString());
                menu.child(currday).child("Lunch").setValue(Lmenu.getText().toString());
                menu.child(currday).child("Dinner").setValue(Dmenu.getText().toString());
                Toast.makeText(MenuChange.this, "Menu Changed", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MenuChange.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}

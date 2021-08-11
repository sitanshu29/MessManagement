package com.example.easymessadmin;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button regstud, Changemenu, Reset, Backup, BStatus, LStatus, DStatus, RstCurr;
    TextView SearchName, SearchReg, SearchMobile, EggB, ChickB, EggL, ChickL, EggD, ChickD;
    Button EggBU, EggBD, EggLU, EggLD, EggDU, EggDD, CBU, CBD, CLU, CLD, CDU, CDD;
    Button ViewComp, ViewFB;
    ImageView Searchbtn;
    String number = "0";
    EditText searchtv;
    FirebaseDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseDatabase.getInstance();
        final DatabaseReference users = db.getReference("User Details");
        final DatabaseReference dbref = db.getReference();
        BStatus = findViewById(R.id.bstatus); LStatus = findViewById(R.id.lstatus); DStatus = findViewById(R.id.dstatus);
        EggB = (TextView)findViewById(R.id.eggbcount); EggL = (TextView)findViewById(R.id.egglcount); EggD = (TextView)findViewById(R.id.eggdcount);
        ChickB = (TextView)findViewById(R.id.chickbcount); ChickL = (TextView)findViewById(R.id.chicklcount);  ChickD = (TextView)findViewById(R.id.chickdcount);
        RstCurr = findViewById(R.id.rstcurr);
        SearchName = (TextView)findViewById(R.id.searchname);
        SearchMobile = (TextView)findViewById(R.id.searchmobile);
        SearchReg = (TextView)findViewById(R.id.searchreg);
        searchtv = (EditText)findViewById(R.id.searchip);
        Searchbtn = (ImageView)findViewById(R.id.search_btn);
        Changemenu = findViewById(R.id.changemenu);
        regstud = findViewById(R.id.regstudent);
        Reset = findViewById(R.id.reset);
        Backup = findViewById(R.id.backup);
        EggBU = findViewById(R.id.eggbup); EggBD = findViewById(R.id.eggbdown); CBU = findViewById(R.id.chickbup); CBD = findViewById(R.id.chickbdown);
        EggLU = findViewById(R.id.egglup); EggLD = findViewById(R.id.eggldown); CLU = findViewById(R.id.chicklup); CLD = findViewById(R.id.chickldown);
        EggDU = findViewById(R.id.eggdup); EggDD = findViewById(R.id.eggddown); CDU = findViewById(R.id.chickdup); CDD = findViewById(R.id.chickddown);
        ViewComp = findViewById(R.id.viewcomplain);
        ViewFB = findViewById(R.id.viewfb);
        Searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                number = searchtv.getText().toString();
                if (number.isEmpty())
                {
                    number = "0";
                    searchtv.setError("Input Required!");
                    searchtv.requestFocus();
                    SearchMobile.setText("xyz"); SearchReg.setText("xyz"); SearchName.setText("xyz");
                    RstCurr.setVisibility(View.INVISIBLE);
                    BStatus.setBackgroundColor(Color.BLUE); LStatus.setBackgroundColor(Color.BLUE); DStatus.setBackgroundColor(Color.BLUE);
                    EggB.setText("0"); EggL.setText("0"); EggL.setText("0");
                    ChickB.setText("0"); ChickL.setText("0"); ChickD.setText("0");
                    return;
                }
                users.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.hasChild(number))
                        {
                            SearchName.setText(dataSnapshot.child(number).child("name").getValue().toString());
                            SearchReg.setText(number);
                            SearchMobile.setText(dataSnapshot.child(number).child("mobile").getValue().toString());
                            RstCurr.setVisibility(View.VISIBLE);

                            dbref.child("Today Status").child(number).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {

                                    if (dataSnapshot1.child("Breakfast").child("Status").getValue().toString().equals("Yes"))
                                    {
                                        BStatus.setBackgroundColor(Color.GREEN);
                                        EggB.setText(dataSnapshot1.child("Breakfast").child("Extra").child("Egg").getValue().toString());
                                        ChickB.setText(dataSnapshot1.child("Breakfast").child("Extra").child("Chicken").getValue().toString());
                                    }
                                    else
                                    {
                                        BStatus.setBackgroundColor(Color.BLUE);
                                        EggB.setText("0");
                                        ChickB.setText("0");
                                    }
                                    if (dataSnapshot1.child("Lunch").child("Status").getValue().toString().equals("Yes"))
                                    {
                                        LStatus.setBackgroundColor(Color.GREEN);
                                        EggL.setText(dataSnapshot1.child("Lunch").child("Extra").child("Egg").getValue().toString());
                                        ChickL.setText(dataSnapshot1.child("Lunch").child("Extra").child("Chicken").getValue().toString());
                                    }
                                    else
                                    {
                                        LStatus.setBackgroundColor(Color.BLUE);
                                        EggL.setText("0");
                                        ChickL.setText("0");
                                    }
                                    if (dataSnapshot1.child("Dinner").child("Status").getValue().toString().equals("Yes"))
                                    {
                                        DStatus.setBackgroundColor(Color.GREEN);
                                        EggD.setText(dataSnapshot1.child("Dinner").child("Extra").child("Egg").getValue().toString());
                                        ChickD.setText(dataSnapshot1.child("Dinner").child("Extra").child("Chicken").getValue().toString());
                                    }
                                    else
                                    {
                                        DStatus.setBackgroundColor(Color.BLUE);
                                        EggD.setText("0");
                                        ChickD.setText("0");;
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        else
                        {
                            number = "0";
                            Toast.makeText(MainActivity.this, "No records Found", Toast.LENGTH_LONG).show();
                            SearchMobile.setText("xyz"); SearchReg.setText("xyz"); SearchName.setText("xyz");
                            RstCurr.setVisibility(View.INVISIBLE);
                            BStatus.setBackgroundColor(Color.BLUE); LStatus.setBackgroundColor(Color.BLUE); DStatus.setBackgroundColor(Color.BLUE);
                            EggB.setText("0"); EggL.setText("0"); EggL.setText("0");
                            ChickB.setText("0"); ChickL.setText("0"); ChickD.setText("0");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        BStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!number.equals("0"))
                {
                    dbref.child("Today Status").child(number).child("Breakfast").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (!number.equals("0") && dataSnapshot.child("Status").getValue().toString().equals("No"))
                            {
                                dbref.child("Today Status").child(number).child("Breakfast").child("Status").setValue("Yes");
                                BStatus.setBackgroundColor(Color.GREEN);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
            }
            }
        });

        LStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!number.equals("0"))
                {
                    dbref.child("Today Status").child(number).child("Lunch").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (!number.equals("0") && dataSnapshot.child("Status").getValue().toString().equals("No"))
                            {
                                dbref.child("Today Status").child(number).child("Lunch").child("Status").setValue("Yes");
                                LStatus.setBackgroundColor(Color.GREEN);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        DStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!number.equals("0"))
                {
                    dbref.child("Today Status").child(number).child("Dinner").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (!number.equals("0") && dataSnapshot.child("Status").getValue().toString().equals("No"))
                            {
                                dbref.child("Today Status").child(number).child("Dinner").child("Status").setValue("Yes");
                                DStatus.setBackgroundColor(Color.GREEN);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        EggBU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!number.equals("0"))
                {
                    dbref.child("Today Status").child(number).child("Breakfast").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (!number.equals("0") && dataSnapshot.child("Status").getValue().toString().equals("Yes"))
                            {
                                int i = Integer.parseInt(dataSnapshot.child("Extra").child("Egg").getValue().toString());
                                dbref.child("Today Status").child(number).child("Breakfast").child("Extra").child("Egg").setValue(String.valueOf(i+1));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        EggBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!number.equals("0"))
                {
                    dbref.child("Today Status").child(number).child("Breakfast").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (!number.equals("0") && dataSnapshot.child("Status").getValue().toString().equals("Yes"))
                            {
                                int i = Integer.parseInt(dataSnapshot.child("Extra").child("Egg").getValue().toString());
                                if (i > 0)
                                dbref.child("Today Status").child(number).child("Breakfast").child("Extra").child("Egg").setValue(String.valueOf(i-1));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        CBU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!number.equals("0"))
                {
                    dbref.child("Today Status").child(number).child("Breakfast").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (!number.equals("0") && dataSnapshot.child("Status").getValue().toString().equals("Yes"))
                            {
                                int i = Integer.parseInt(dataSnapshot.child("Extra").child("Chicken").getValue().toString());
                                dbref.child("Today Status").child(number).child("Breakfast").child("Extra").child("Chicken").setValue(String.valueOf(i+1));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        CBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!number.equals("0"))
                {
                    dbref.child("Today Status").child(number).child("Breakfast").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (!number.equals("0") && dataSnapshot.child("Status").getValue().toString().equals("Yes"))
                            {
                                int i = Integer.parseInt(dataSnapshot.child("Extra").child("Chicken").getValue().toString());
                                if (i > 0)
                                    dbref.child("Today Status").child(number).child("Breakfast").child("Extra").child("Chicken").setValue(String.valueOf(i-1));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        EggLU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!number.equals("0"))
                {
                    dbref.child("Today Status").child(number).child("Lunch").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (!number.equals("0") && dataSnapshot.child("Status").getValue().toString().equals("Yes"))
                            {
                                int i = Integer.parseInt(dataSnapshot.child("Extra").child("Egg").getValue().toString());
                                dbref.child("Today Status").child(number).child("Lunch").child("Extra").child("Egg").setValue(String.valueOf(i+1));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        EggLD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!number.equals("0"))
                {
                    dbref.child("Today Status").child(number).child("Lunch").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (!number.equals("0") && dataSnapshot.child("Status").getValue().toString().equals("Yes"))
                            {
                                int i = Integer.parseInt(dataSnapshot.child("Extra").child("Egg").getValue().toString());
                                if (i > 0)
                                    dbref.child("Today Status").child(number).child("Lunch").child("Extra").child("Egg").setValue(String.valueOf(i-1));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        CLU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!number.equals("0"))
                {
                    dbref.child("Today Status").child(number).child("Lunch").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (!number.equals("0") && dataSnapshot.child("Status").getValue().toString().equals("Yes"))
                            {
                                int i = Integer.parseInt(dataSnapshot.child("Extra").child("Chicken").getValue().toString());
                                dbref.child("Today Status").child(number).child("Lunch").child("Extra").child("Chicken").setValue(String.valueOf(i+1));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        CLD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!number.equals("0"))
                {
                    dbref.child("Today Status").child(number).child("Lunch").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (!number.equals("0") && dataSnapshot.child("Status").getValue().toString().equals("Yes"))
                            {
                                int i = Integer.parseInt(dataSnapshot.child("Extra").child("Chicken").getValue().toString());
                                if (i > 0)
                                    dbref.child("Today Status").child(number).child("Lunch").child("Extra").child("Chicken").setValue(String.valueOf(i-1));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        EggDU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!number.equals("0"))
                {
                    dbref.child("Today Status").child(number).child("Dinner").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (!number.equals("0") && dataSnapshot.child("Status").getValue().toString().equals("Yes"))
                            {
                                int i = Integer.parseInt(dataSnapshot.child("Extra").child("Egg").getValue().toString());
                                dbref.child("Today Status").child(number).child("Dinner").child("Extra").child("Egg").setValue(String.valueOf(i+1));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        EggDD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!number.equals("0"))
                {
                    dbref.child("Today Status").child(number).child("Dinner").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (!number.equals("0") && dataSnapshot.child("Status").getValue().toString().equals("Yes"))
                            {
                                int i = Integer.parseInt(dataSnapshot.child("Extra").child("Egg").getValue().toString());
                                if (i > 0)
                                    dbref.child("Today Status").child(number).child("Dinner").child("Extra").child("Egg").setValue(String.valueOf(i-1));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        CDU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!number.equals("0"))
                {
                    dbref.child("Today Status").child(number).child("Dinner").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (!number.equals("0") && dataSnapshot.child("Status").getValue().toString().equals("Yes"))
                            {
                                int i = Integer.parseInt(dataSnapshot.child("Extra").child("Chicken").getValue().toString());
                                dbref.child("Today Status").child(number).child("Dinner").child("Extra").child("Chicken").setValue(String.valueOf(i+1));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        CDD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!number.equals("0"))
                {
                    dbref.child("Today Status").child(number).child("Dinner").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (!number.equals("0") && dataSnapshot.child("Status").getValue().toString().equals("Yes"))
                            {
                                int i = Integer.parseInt(dataSnapshot.child("Extra").child("Chicken").getValue().toString());
                                if (i > 0)
                                    dbref.child("Today Status").child(number).child("Dinner").child("Extra").child("Chicken").setValue(String.valueOf(i-1));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });


        regstud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterStudent.class);
                startActivity(intent);
            }
        });

        Changemenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MenuChange.class);
                startActivity(intent);
            }
        });

        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                users.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        number = "0";
                        searchtv.setText("");
                        SearchMobile.setText("xyz"); SearchReg.setText("xyz"); SearchName.setText("xyz");
                        RstCurr.setVisibility(View.INVISIBLE);
                        for(DataSnapshot reg : dataSnapshot.getChildren())
                        {
                            String regnumber = reg.getKey();
                            dbref.child("Today Status").child(regnumber).child("Dinner").child("Extra").child("Egg").setValue("0");
                            dbref.child("Today Status").child(regnumber).child("Dinner").child("Extra").child("Chicken").setValue("0");
                            dbref.child("Today Status").child(regnumber).child("Dinner").child("Status").setValue("No");

                            dbref.child("Today Status").child(regnumber).child("Lunch").child("Extra").child("Egg").setValue("0");
                            dbref.child("Today Status").child(regnumber).child("Lunch").child("Extra").child("Chicken").setValue("0");
                            dbref.child("Today Status").child(regnumber).child("Lunch").child("Status").setValue("No");

                            dbref.child("Today Status").child(regnumber).child("Breakfast").child("Extra").child("Chicken").setValue("0");
                            dbref.child("Today Status").child(regnumber).child("Breakfast").child("Extra").child("Egg").setValue("0");
                            dbref.child("Today Status").child(regnumber).child("Breakfast").child("Status").setValue("No");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        Backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                final String date = df.format(Calendar.getInstance().getTime());
                dbref.child("Today Status").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                        {
                            dbref.child("Previous Record").child(date).child(dataSnapshot1.getKey()).setValue(dataSnapshot1.getValue());
                        }

                        Toast.makeText(MainActivity.this, "Backup Successful", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(MainActivity.this, "Backup Failed", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        RstCurr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!number.equals("0"))
                {
                    String num = number;
                    number = "0";
                    dbref.child("Today Status").child(num).child("Dinner").child("Extra").child("Egg").setValue("0");
                    dbref.child("Today Status").child(num).child("Dinner").child("Extra").child("Chicken").setValue("0");
                    dbref.child("Today Status").child(num).child("Dinner").child("Status").setValue("No");

                    dbref.child("Today Status").child(num).child("Lunch").child("Extra").child("Egg").setValue("0");
                    dbref.child("Today Status").child(num).child("Lunch").child("Extra").child("Chicken").setValue("0");
                    dbref.child("Today Status").child(num).child("Lunch").child("Status").setValue("No");

                    dbref.child("Today Status").child(num).child("Breakfast").child("Extra").child("Chicken").setValue("0");
                    dbref.child("Today Status").child(num).child("Breakfast").child("Extra").child("Egg").setValue("0");
                    dbref.child("Today Status").child(num).child("Breakfast").child("Status").setValue("No");

                    SearchMobile.setText("xyz"); SearchReg.setText("xyz"); SearchName.setText("xyz");
                    RstCurr.setVisibility(View.INVISIBLE);
                    BStatus.setBackgroundColor(Color.BLUE); LStatus.setBackgroundColor(Color.BLUE); DStatus.setBackgroundColor(Color.BLUE);
                    EggB.setText("0"); EggL.setText("0"); EggL.setText("0");
                    ChickB.setText("0"); ChickL.setText("0"); ChickD.setText("0");
                    searchtv.setText("");
                }
            }
        });

        ViewComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewComplain.class);
                startActivity(intent);
            }
        });



    }
}

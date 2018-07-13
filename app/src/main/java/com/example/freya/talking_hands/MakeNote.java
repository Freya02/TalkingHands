package com.example.freya.talking_hands;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MakeNote extends AppCompatActivity {
    Toolbar toolbar;
    EditText title,desc;
    Button note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_note);
        Intent i=getIntent();

        String tit=i.getStringExtra("title");
        String des=i.getStringExtra("desc");
        final String notid=i.getStringExtra("noteid");

        title=findViewById(R.id.title);
        desc=findViewById(R.id.desc);
        note=findViewById(R.id.makenote);

        if(tit!=null){
            title.setText(tit);
            desc.setText(des);
            note.setText("SAVE CHANGES");
        }

        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database=FirebaseDatabase.getInstance();
                DatabaseReference ref=database.getReference("Notes").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                if(notid!=null){
                    ref=ref.child(notid);
                }
                else{
                    ref=ref.push();
                }
                ref.child("title").setValue(title.getText().toString());
                ref.child("desc").setValue(desc.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MakeNote.this, "Note saved", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MakeNote.this,ViewNotes.class));
                        }
                        else{
                            Toast.makeText(MakeNote.this, "Error saving note", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }



}

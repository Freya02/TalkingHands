package com.example.freya.talking_hands;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class HomeActivity extends AppCompatActivity {



    Button learn,quiz,notes;
    ImageView imageview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        imageview=findViewById(R.id.imageview);
        learn=findViewById(R.id.learn);
        quiz=findViewById(R.id.quiz);
        notes=findViewById(R.id.notes);
        Glide.with(HomeActivity.this).load(R.drawable.logo1).into(imageview);
        learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(HomeActivity.this,RecyclerActivity.class);
                startActivity(i);
            }
        });

        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(HomeActivity.this,FunFacts.class);
                startActivity(i);
            }
        });

        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(HomeActivity.this,ViewNotes.class);
                startActivity(i);
            }
        });
    }
}

package com.example.freya.talking_hands;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class FunFacts extends AppCompatActivity {
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fun_facts);
        txt=findViewById(R.id.textView);
        txt.setText("*Half of hearing loss incidents and primers are viable through AIDS cochlear implants and other assistive devices, captioning and signs and other forms of education\n" +
                "        and social support hearing aids require hearing less than 10% of the AIDS Globe.\n" +
                "        \n" +
                "*360 billion people around the world have disabling hearing loss.\n" +
                "*hearing loss due to genetic causes complications at birth,certain infectious diseases,chronic ear infections, ageing and excessive noise.\n" +
                "*People with Hearing loss can benefit from cochlear implants and other system devices and supports sign language and other forms of education and social support.");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.make_note, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if(android.R.id.home==id){
            startActivity(new Intent(FunFacts.this,HomeActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

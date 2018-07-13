package com.example.freya.talking_hands;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewNotes extends AppCompatActivity {
    static Toolbar toolbar;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView=findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewNotes.this,MakeNote.class));
            }
        });

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.edit) {
            Toast.makeText(ViewNotes.this, "Edit clicked", Toast.LENGTH_LONG).show();
            return true;
        }
        if(id==R.id.delete){
            Toast.makeText(ViewNotes.this, "Delete clicked", Toast.LENGTH_LONG).show();
            return true;
        }
        if(android.R.id.home==id){
            startActivity(new Intent(ViewNotes.this,HomeActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();

        final DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Notes").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref.keepSynced(true);
        FirebaseRecyclerAdapter<SingleNote,SingleNoteHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<SingleNote, SingleNoteHolder>(SingleNote.class,R.layout.list_item,SingleNoteHolder.class,ref) {
            @Override
            protected void populateViewHolder(SingleNoteHolder viewHolder, final SingleNote model, int position) {
                viewHolder.setDesc(model.getDesc());
                viewHolder.setTitle(model.getTitle());
                final String note_key=getRef(position).getKey();
                viewHolder.edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(ViewNotes.this,MakeNote.class);
                        i.putExtra("title",model.getTitle().toString());
                        i.putExtra("desc",model.getDesc().toString());
                        i.putExtra("noteid",note_key);
                        startActivity(i);
                    }
                });

               viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Toast.makeText(ViewNotes.this,"delete clicked!",Toast.LENGTH_SHORT).show();
                       ref.child(note_key).removeValue();
                   }
               });

            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);


    }

    public static class SingleNoteHolder extends RecyclerView.ViewHolder{

        View view;//view,itemView is object of single row
        String str;
        ImageView edit,delete;
        boolean flag=false;
        public SingleNoteHolder(View itemView) {
            super(itemView);
            view=itemView;
            edit=view.findViewById(R.id.edit);
            delete=view.findViewById(R.id.delete);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView y=view.findViewById(R.id.des);
                    if(flag){
                        y.setMaxLines(3);
                        flag=false;
                    }
                    else{
                        y.setMaxLines(1000);
                        flag=true;
                    }

                }
            });
        }

        public void setTitle(String title){
            TextView t=view.findViewById(R.id.tit);
            t.setText(title);
        }
        public void setDesc(String desc){
            TextView t=view.findViewById(R.id.des);
            //str=desc;
            //String s=desc.substring(0,desc.length()-2);
            t.setMaxLines(3);
            t.setText(desc);
        }

    }
}

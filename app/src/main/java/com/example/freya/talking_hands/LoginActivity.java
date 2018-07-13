package com.example.freya.talking_hands;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;


public class LoginActivity extends AppCompatActivity {

    SignInButton Gsign;
    Button signin,signup;
    private static final int RC_SIGN_IN=1;
    FirebaseAuth mAuth;
    GoogleApiClient mGoogleApiClient;
    String TAG="Authentication";
    ProgressDialog mprog;
    TextView forgotpass;
    FirebaseAuth.AuthStateListener LoginListener;
    FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();
        mUser= FirebaseAuth.getInstance().getCurrentUser();
        if(mUser!=null){
            Intent i=new Intent(LoginActivity.this,ViewNotes.class);
            i.putExtra("lat",getIntent().getStringExtra("latitude"));
            i.putExtra("long",getIntent().getStringExtra("longitude"));
            startActivity(i);
        }
        mprog=new ProgressDialog(this);
        LoginListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    Intent i=new Intent(LoginActivity.this,HomeActivity.class);
                    i.putExtra("lat",getIntent().getStringExtra("latitude"));
                    i.putExtra("long",getIntent().getStringExtra("longitude"));
                    startActivity(i);
                }
            }
        };

        mAuth.addAuthStateListener(LoginListener);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient=new GoogleApiClient.Builder(getApplicationContext()).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Toast.makeText(LoginActivity.this, "Error Signing In!!!", Toast.LENGTH_SHORT).show();
            }
        }).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

        Gsign=findViewById(R.id.googlebutton);
        Gsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        signin= findViewById(R.id.button3);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username= findViewById(R.id.editText5);
                final EditText pass= findViewById(R.id.editText6);
                if (TextUtils.isEmpty(username.getText().toString())||TextUtils.isEmpty(pass.getText().toString())){
                    Toast.makeText(LoginActivity.this, "Incomplete credentials!!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                mprog.setMessage("Logging In");
                mprog.setCanceledOnTouchOutside(false);
                mprog.show();
                FirebaseAuth mAuth=FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(username.getText().toString(),pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            mprog.dismiss();
                            Intent i=new Intent(LoginActivity.this,ViewNotes.class);
                            i.putExtra("lat",getIntent().getStringExtra("latitude"));
                            i.putExtra("long",getIntent().getStringExtra("longitude"));
                            startActivity(i);
                        }
                        else{
                            mprog.dismiss();
                            Toast.makeText(LoginActivity.this, "Invalid Credentials !!!", Toast.LENGTH_SHORT).show();
                            pass.setText("");
                        }
                    }
                });
            }
        });
        signup= findViewById(R.id.button5);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignUp.class));
            }
        });
    }
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){

        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mUser=FirebaseAuth.getInstance().getCurrentUser();
                            if(mUser!=null){
                                Intent i=new Intent(LoginActivity.this,ViewNotes.class);
                                i.putExtra("lat",getIntent().getStringExtra("latitude"));
                                i.putExtra("long",getIntent().getStringExtra("longitude"));
                                startActivity(i);
                            }
                        } else {

                        }
                    }
                });
    }
}



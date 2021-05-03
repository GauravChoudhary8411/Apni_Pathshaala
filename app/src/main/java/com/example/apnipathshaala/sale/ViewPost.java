package com.example.apnipathshaala.sale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apnipathshaala.R;
import com.example.apnipathshaala.models.Post;
import com.example.apnipathshaala.utils.SquareImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;
import com.squareup.picasso.Picasso;

public class ViewPost extends AppCompatActivity {

    private static final String TAG = "ViewPostFragment";

    private TextView mContactSeller, mTitle, mDescription, mPrice, mLocation, mSavePost,memail,mcity,mstate,mcountry;
    SquareImageView imageView;


    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);
        imageView=findViewById(R.id.post_image);
        mContactSeller = (TextView) findViewById(R.id.post_contact);
        mTitle = (TextView) findViewById(R.id.post_title);
        mDescription = (TextView) findViewById(R.id.post_description);
        mPrice = (TextView) findViewById(R.id.post_price);
        mcity = (TextView) findViewById(R.id.post_city);
        mstate = (TextView) findViewById(R.id.post_state);
        mcountry = (TextView) findViewById(R.id.post_country);

        mSavePost = (TextView) findViewById(R.id.save_post);
        memail=(TextView)findViewById(R.id.contact_email);
        reference= FirebaseDatabase.getInstance().getReference().child("posts");

        String postkey=getIntent().getStringExtra("postkey");

        reference.child(postkey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String title=snapshot.child("title").getValue().toString();
                    String Imageurl=snapshot.child("image").getValue().toString();
                    String desc=snapshot.child("description").getValue().toString();
                    String price=snapshot.child("price").getValue().toString();
                    String city=snapshot.child("city").getValue().toString();
                    String state=snapshot.child("state_province").getValue().toString();
                    String country=snapshot.child("country").getValue().toString();

                    String email=snapshot.child("contact_email").getValue().toString();

                    Picasso.get().load(Imageurl).into(imageView);
                    mTitle.setText(title);
                    memail.setText(email);
                    mDescription.setText(desc);
                    mcity.setText(city);
                    mstate.setText(", "+state);
                    mcountry.setText(", "+country);
                    mPrice.setText("Rs."+price);
                    mContactSeller.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent emailIntent = new Intent(Intent.ACTION_SEND);
                            emailIntent.setType("plain/text");
                            emailIntent.putExtra(Intent.EXTRA_EMAIL, email);
                            startActivity(emailIntent);
                            }
                        });
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
package com.example.apnipathshaala.sale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apnipathshaala.R;
import com.example.apnipathshaala.models.Post;
import com.example.apnipathshaala.utils.SquareImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ViewPost extends AppCompatActivity {

    private static final String TAG = "ViewPostFragment";
    private TextView mContactSeller, mTitle, mDescription, mPrice,mcity,mstate,mcountry;
    SquareImageView imageView;
    Post mPost;


    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);


        imageView = findViewById(R.id.post_image);
        mContactSeller = findViewById(R.id.post_contact);
        mTitle = findViewById(R.id.post_title);
        mDescription = findViewById(R.id.post_description);
        mPrice = findViewById(R.id.post_price);
        mcity = findViewById(R.id.post_city);
        mstate = findViewById(R.id.post_state);
        mcountry = findViewById(R.id.post_country);


        reference = FirebaseDatabase.getInstance().getReference().child("posts");

        String postkey = getIntent().getStringExtra("postkey");

        reference.child(postkey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    mPost = snapshot.getValue(Post.class);

                    String title = snapshot.child("title").getValue().toString();
                    String Imageurl = snapshot.child("image").getValue().toString();
                    String desc = snapshot.child("description").getValue().toString();
                    String price = snapshot.child("price").getValue().toString();
                    String city = snapshot.child("city").getValue().toString();
                    String state = snapshot.child("state_province").getValue().toString();
                    String country = snapshot.child("country").getValue().toString();


                    Picasso.get().load(Imageurl).into(imageView);
                    mTitle.setText(title);
                    mDescription.setText(desc);
                    mcity.setText(city);
                    mstate.setText(", " + state);
                    mcountry.setText(", " + country);
                    mPrice.setText("Rs." + price);
                    mContactSeller.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (FirebaseAuth.getInstance().getCurrentUser().toString() != mPost.getContact_email()) {
                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.setType("Hello. I would like to ");
                                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{mPost.getContact_email()});
                                intent.putExtra(Intent.EXTRA_SUBJECT, "I would like to ");
                                intent.setPackage("com.google.android.gm");
                                    startActivity(intent);
                            }
                            else{
                                Log.d(TAG,"user contact itself");
                                Toast.makeText(getApplicationContext(), "You yourself have uploaded the product", Toast.LENGTH_LONG).show();
                                }

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
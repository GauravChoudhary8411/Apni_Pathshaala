package com.example.apnipathshaala.tutor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

public class tViewPost extends AppCompatActivity {

    private static final String TAG = "ViewPostFragment";

    private TextView mContactSeller, mTitle, mDescription, mPrice, mcity, mstate, mcountry;
    SquareImageView imageView;

    private Post mPost;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);
        imageView = findViewById(R.id.post_image);
        mContactSeller = (TextView) findViewById(R.id.post_contact);
        mTitle = (TextView) findViewById(R.id.post_title);
        mDescription = (TextView) findViewById(R.id.post_description);
        mPrice = (TextView) findViewById(R.id.post_price);
        mcity = (TextView) findViewById(R.id.post_city);
        mstate = (TextView) findViewById(R.id.post_state);
        mcountry = (TextView) findViewById(R.id.post_country);
        reference = FirebaseDatabase.getInstance().getReference().child("tutor");
        String postkey = getIntent().getStringExtra("postkey");

        reference.child(postkey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mPost=snapshot.getValue(Post.class);

                if (snapshot.exists()) {

                    String title = snapshot.child("title").getValue().toString();
                    String Imageurl = snapshot.child("image").getValue().toString();
                    String desc = snapshot.child("description").getValue().toString();
                    String price = snapshot.child("price").getValue().toString();
                    String city = snapshot.child("city").getValue().toString();
                    String state = snapshot.child("state_province").getValue().toString();
                    String country = snapshot.child("country").getValue().toString();
                    String email = snapshot.child("contact_email").getValue().toString();
                    Picasso.get().load(Imageurl).into(imageView);
                    mTitle.setText(title);
                    mDescription.setText(desc);
                    mcity.setText("Lives in " + city);
                    mstate.setText(", Will teach in " + state);
                    mcountry.setText(", After, " + country);
                    mPrice.setText("Rs." + price + "/hr");
                    mContactSeller.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent (Intent.ACTION_SEND);
                            intent.setType("Hello. I would like to learn");
                            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{mPost.getContact_email()});
                            intent.putExtra(Intent.EXTRA_SUBJECT, "I would like to learn about ");
                            intent.setPackage("com.google.android.gm");
                            if (intent.resolveActivity(getPackageManager())!=null)
                                startActivity(intent);
                            else
                                Toast.makeText(getApplicationContext(),"Gmail App is not installed", Toast.LENGTH_SHORT).show();
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
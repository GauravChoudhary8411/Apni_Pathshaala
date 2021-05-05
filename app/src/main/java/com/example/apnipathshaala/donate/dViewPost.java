package com.example.apnipathshaala.donate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.apnipathshaala.R;
import com.example.apnipathshaala.models.Post;
import com.example.apnipathshaala.utils.SquareImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

public class dViewPost extends AppCompatActivity {

    private static final String TAG = "ViewPostFragment";
    ImageView imageView;
    Post mPost;
    DatabaseReference reference;
    private TextView mContactSeller, mTitle, mDescription, mPrice, mcity, mstate, mcountry;

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


        reference = FirebaseDatabase.getInstance().getReference().child("donate");

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

                    String email = snapshot.child("contact_email").getValue().toString();

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
                                intent.setType("Hello. I would like to learn");
                                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{mPost.getContact_email()});
                                intent.putExtra(Intent.EXTRA_SUBJECT, "I would like to learn about ");
                                intent.setPackage("com.google.android.gm");
                                startActivity(intent);
                            } else {
                                Log.d(TAG, "user contact itself");
                                Toast.makeText(getApplicationContext(), "You yourself have uploaded the product", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }
                         /*   Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("plain/text");
                            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{mPost.getContact_email()});
                            intent.putExtra(Intent.EXTRA_SUBJECT, "Emailing link");
                            intent.putExtra(Intent.EXTRA_TEXT, "Link is \n" +
                                    "This is the body of the message");
                            startActivity(Intent.createChooser(intent, ""));
                        }
                    });
                }*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
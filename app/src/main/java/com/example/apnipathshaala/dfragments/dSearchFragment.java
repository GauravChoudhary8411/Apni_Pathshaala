package com.example.apnipathshaala.dfragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apnipathshaala.R;
import com.example.apnipathshaala.models.Post;
import com.example.apnipathshaala.tutor.tViewPost;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class dSearchFragment extends Fragment {
    public EditText inputsearch;
    public RecyclerView recyclerView;
    String TAG = "View Post";
    FirebaseRecyclerOptions<Post> options;
    FirebaseRecyclerAdapter<Post, dMyViewHolder> adapter;
    DatabaseReference databaseReference;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("donate");
        inputsearch = view.findViewById(R.id.input_search);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        LoadData("");
        inputsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString() != null) {
                    LoadData(editable.toString());
                } else {
                    LoadData("");
                }
            }
        });
        return view;
    }

    private void LoadData(String data) {
        Query query = databaseReference.orderByChild("title").startAt(data).endAt(data + "\uf8ff");
        options = new FirebaseRecyclerOptions.Builder<Post>().setQuery(query, Post.class).build();
        adapter = new FirebaseRecyclerAdapter<Post, dMyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull dMyViewHolder holder, int position, @NonNull Post model) {
                holder.textView.setText(model.getTitle());
                Picasso.get().load(model.getImage()).into(holder.imageView);

                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), com.example.apnipathshaala.donate.dViewPost.class);
                        intent.putExtra("postkey", getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public dMyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view, parent, false);
                return new dMyViewHolder(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}


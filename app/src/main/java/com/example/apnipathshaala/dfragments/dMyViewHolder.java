package com.example.apnipathshaala.dfragments;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apnipathshaala.R;
import com.example.apnipathshaala.utils.SquareImageView;

class dMyViewHolder extends RecyclerView.ViewHolder {

    SquareImageView imageView;
    TextView textView;
    View v;

    public dMyViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.image_single_view);
        textView = itemView.findViewById(R.id.textView_single_view);
        v = itemView;
    }
}
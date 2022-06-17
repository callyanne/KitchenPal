package com.example.kitchenpal.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitchenpal.R;
import com.example.kitchenpal.models.ProfileFavModel;

import java.util.List;

public class ProfileMyRecipesAdapter extends RecyclerView.Adapter<ProfileMyRecipesAdapter.ViewHolder> {

    Context context;
    List<ProfileFavModel> list;

    public ProfileMyRecipesAdapter(Context context, List<ProfileFavModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ProfileMyRecipesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProfileMyRecipesAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_favourites_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileMyRecipesAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.imageView.setImageResource(list.get(position).getImage());
        holder.name.setText(list.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, list.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.ver_img);
            name = itemView.findViewById(R.id.ver_text);
        }
    }
}
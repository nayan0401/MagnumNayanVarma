package com.magnumnayan.app;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    Context context;
    List<DataModel> list;

    public Adapter(Context context, List<DataModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataModel model = list.get(position);
        holder.userName.setText(model.getUserName());
        Glide.with(holder.userImage.getContext()).load(model.getUserImageUrl()).placeholder(R.drawable.placeholder).into(holder.userImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView userImage;
        CardView cardView;
        TextView userName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.item_userImage);
            userName = itemView.findViewById(R.id.item_userName);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}

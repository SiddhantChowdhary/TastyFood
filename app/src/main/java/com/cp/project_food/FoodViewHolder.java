package com.cp.project_food;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class FoodViewHolder extends RecyclerView.ViewHolder {

     private final TextView Food_Text_View;
    private final ImageView image_view;
    private final TextView TimeStamp_view;
    Context context;
    public FoodViewHolder(@NonNull View Food_itemView, Context context)
    {
        super(Food_itemView);
        Food_Text_View = Food_itemView.findViewById(R.id.name_view);
        image_view = Food_itemView.findViewById(R.id.image);
        TimeStamp_view = Food_itemView.findViewById(R.id.date_view);
        this.context=context;
    }

    public void set_Food_Image_Url(String Image_url) {

              // use glide to load the image
        Glide.with(context).load(Image_url).centerCrop().into(image_view);
    }

    public void set_Food_Item_name(String food_name) {
        //Inserting Food Name
        this.Food_Text_View.setText(food_name);
    }

    public void set_TimeStamp(String time) {

        //Inserting Time Stamp
        String a=context.getString(R.string.cr) +" "+ time;
        this.TimeStamp_view.setText(a);
    }
}

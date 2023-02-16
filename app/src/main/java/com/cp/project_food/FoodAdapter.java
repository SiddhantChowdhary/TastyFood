package com.cp.project_food;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;

public class FoodAdapter extends RecyclerView.Adapter<FoodViewHolder>
 {
     private final Context context;
    private final ArrayList<Food_Item> List;

    public FoodAdapter(Context context, ArrayList<Food_Item> List)
    {
        this.context = context;
        this.List = List;
    }
    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_article, parent, false);


        return new FoodViewHolder(view,context);
    }

     @Override
     public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {


         Food_Item item = List.get(position);
         holder.set_Food_Item_name(item.getName());
         holder.set_Food_Image_Url(item.getUrl());

         int date= Integer.parseInt(item.getDate());
         java.util.Date time=new Date((long)date*1000);
         String f=time.toString();
         holder.set_TimeStamp(f);

     }



    @Override
    public int getItemCount() {
        return List == null ? 0 : List.size();
    }
}
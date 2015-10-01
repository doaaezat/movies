package com.example.dododo.finalproject;

import android.content.Context;
import android.renderscript.Int2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DoDo DO on 25/08/2015.
 */
public class customeAdapter extends BaseAdapter {

    private List<gson.ResultsEntity> item;
    private Context context ;
    private LayoutInflater inflater ;

    public customeAdapter(Context context, List<gson.ResultsEntity> item) {
        this.context = context;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {

        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }




    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowview = inflater.inflate(R.layout.grid_item ,viewGroup ,false);
        gson.ResultsEntity opject = (gson.ResultsEntity) getItem(position);
        ImageView img = (ImageView) rowview.findViewById(R.id.idofimageView);
        String imageurl = opject.getPoster_path();
      // int popular = (int) opject.getPopularity();

        Picasso.with(context).load("https://image.tmdb.org/t/p/w185" + imageurl).into(img);
        
        return rowview ;
    }
}

package com.pashkobohdan.scheduler.library.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pashkobohdan.scheduler.MainActivity;
import com.pashkobohdan.scheduler.R;
import com.pashkobohdan.scheduler.library.timeWorker.Lecture;

import java.util.List;

/**
 * Created by Bohdan Pashko on 17.05.2016.
 */
public class GridAdapter extends BaseAdapter {

    List<Lecture> lectureList;
    Context context;
    private static LayoutInflater inflater = null;

    private int oneWidth, oneHeight;

    public GridAdapter(Context context, List<Lecture> lectureList, int oneWidth, int oneHeight) {
        this.lectureList = lectureList;
        this.oneWidth = oneWidth;
        this.oneHeight = oneHeight;

        context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setSizes(int oneWidth, int oneHeight) {
        this.oneWidth = oneWidth;
        this.oneHeight = oneHeight;
    }

    @Override
    public int getCount() {
        return lectureList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder {
        Button button;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView = inflater.inflate(R.layout.grid_layout_item, null);

        holder.button = (Button) rowView.findViewById(R.id.button2);
        holder.button.setText(lectureList.get(position).getSubject().getName());


        rowView.setMinimumWidth(oneWidth);
        rowView.setMinimumHeight(oneHeight);
//        holder.button.setWidth(oneWidth);
//        holder.button.setHeight(oneHeight);
        //holder.button.setLayoutParams(new ViewGroup.LayoutParams(oneWidth, oneHeight));

        return rowView;
    }



}

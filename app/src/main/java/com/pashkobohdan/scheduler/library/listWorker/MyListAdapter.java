package com.pashkobohdan.scheduler.library.listWorker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pashkobohdan.scheduler.R;
import com.pashkobohdan.scheduler.library.Event;
import com.pashkobohdan.scheduler.library.activityHalper.MyRun;
import com.pashkobohdan.scheduler.library.dateBaseHelper.ReadData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import layout.Events;

/**
 * Created by Bohdan Pashko on 05.05.2016.
 */

class ContextMenuAdapter extends BaseAdapter {

    Context context;
    String[] listContextMenuItems;
    LayoutInflater inflater;

    public ContextMenuAdapter(Context context, String[] listContextMenuItems) {
        super();
        this.context = context;
        this.listContextMenuItems = listContextMenuItems;
    }

    static class ViewHolder {
        protected TextView textView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.simple_context_menu_item, parent,
                    false);
            viewHolder.textView = (TextView) convertView
                    .findViewById(R.id.simpleText);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textView.setText(listContextMenuItems[position]);
        return convertView;
    }

    @Override
    public int getCount() {
        return listContextMenuItems.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}

public class MyListAdapter extends ArrayAdapter<Event> {
    private final Activity context;
    private final List<Event> values;


    public MyListAdapter(Activity context, List<Event> eventList) {
        super(context, R.layout.row_list_layout, eventList);
        this.context = context;
        this.values = eventList;
    }

    static class ViewHolder {
        protected ImageView eventType;
        protected TextView text;
        protected CheckBox isComplete;
        protected TextView startTime;
        protected TextView duration;
    }

    private Event curEvent;

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Event getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.row_list_layout, null);

            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.eventType = (ImageView) view.findViewById(R.id.type);
            viewHolder.text = (TextView) view.findViewById(R.id.text);
            viewHolder.isComplete = (CheckBox) view.findViewById(R.id.checkBox);
            viewHolder.startTime = (TextView)view.findViewById(R.id.textView16);
            viewHolder.duration = (TextView)view.findViewById(R.id.textView17);

            curEvent = values.get(position);
            final Event eventForRun = curEvent;

            viewHolder.isComplete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Event element = (Event) viewHolder.isComplete.getTag();
                    element.setComplete(buttonView.isChecked());

                    if (isChecked == true) {
                        viewHolder.text.setPaintFlags(viewHolder.text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        viewHolder.text.setTextColor(Color.parseColor("#A5A5A5"));
                    } else {
                        viewHolder.text.setPaintFlags(viewHolder.text.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        viewHolder.text.setTextColor(Color.parseColor("#000000"));
                    }

                    curEvent.setComplete(element.isComplete());
                    ReadData.editEvent(curEvent);
                }
            });

            viewHolder.isComplete.setTag(values.get(position));
            switch (values.get(position).getType()) {
                case 0:
                    viewHolder.eventType.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.action_my));
                    break;
                case 1:
                    viewHolder.eventType.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.learn_my));
                    break;
                case 2:
                    viewHolder.eventType.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.sheets_my));
                    break;
                default:
                    viewHolder.eventType.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.settings_my));
            }

            viewHolder.startTime.setText(new SimpleDateFormat("dd.MM.yyyy    HH:mm").format(values.get(position).getStartTime()));

            viewHolder.duration.setText(values.get(position).getLength().toString());

            //viewHolder.startTime.setText(values.get(position).getStartTime().toString());


//            LayoutInflater inflater = LayoutInflater.from(getContext());
//            View child = inflater.inflate(R.layout.listview_context_menu, null);
//
//            ListView listView = (ListView) child.findViewById(R.id.listView_context_menu);
//            ContextMenuAdapter adapter = new ContextMenuAdapter(getContext(), new String[]{"Edit", "Delete", "Open"});
//            listView.setAdapter(adapter);
//
//            final Dialog dialogEventType = new Dialog(getContext());
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//                    dialogEventType.dismiss();
//
//                    switch (position) {
//                        case 0:
//                            ReadData.editEvent(eventForRun);
//                            break;
//                        case 1:
//                            AlertDialog.Builder ad1 = new AlertDialog.Builder(getContext());
//                            ad1.setMessage("Do you want to delete this event ?");
//                            ad1.setCancelable(false);
//
//                            ad1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface arg0, int arg1) {
//                                }
//                            });
//                            ad1.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface arg0, int arg1) {
//                                    ReadData.deleteEvent(eventForRun);
//                                }
//                            });
//                            AlertDialog alert = ad1.create();
//                            alert.show();
//                            break;
//                        case 2:
//                            ReadData.editEvent(eventForRun);
//                            break;
//                        default:
//                            break;
//                    }
//                }
//            });
//
//            dialogEventType.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialogEventType.setContentView(child);


//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(getContext(), "call view click", Toast.LENGTH_LONG).show();
//                    dialogEventType.show();
//                }
//            });
//
//            viewHolder.settings.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.settings_my));
//            viewHolder.settings.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (myRun != null) {
//                        myRun.change(eventForRun);
//                    } else {
//                        Toast.makeText(context, "myRun is null !", Toast.LENGTH_LONG).show();
//                    }
//                }
//            });

            view.setTag(viewHolder);
        } else {
            view = convertView;
            ((ViewHolder) view.getTag()).isComplete.setTag(values.get(position));
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.text.setText(values.get(position).getName());
        holder.isComplete.setChecked(values.get(position).isComplete());
        return view;
    }


}

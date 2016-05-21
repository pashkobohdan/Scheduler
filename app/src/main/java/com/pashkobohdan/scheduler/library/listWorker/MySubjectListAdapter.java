package com.pashkobohdan.scheduler.library.listWorker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pashkobohdan.scheduler.R;
import com.pashkobohdan.scheduler.library.Event;
import com.pashkobohdan.scheduler.library.activityHalper.MyRun;
import com.pashkobohdan.scheduler.library.activityHalper.MySubjectRun;
import com.pashkobohdan.scheduler.library.timeWorker.Subject;

import java.util.List;

/**
 * Created by Bohdan Pashko on 11.05.2016.
 */
public class MySubjectListAdapter extends ArrayAdapter<Subject> {
    private final Activity context;
    private final List<Subject> values;


    public MySubjectListAdapter(Activity context, List<Subject> eventList) {
        super(context, R.layout.row_list_layout, eventList);
        this.context = context;
        this.values = eventList;
    }

    static class ViewSubjectHolder {
        protected ImageView subjectType;
        protected TextView subjectName;
        protected TextView teacherName;
    }

    private Subject curSubject;

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Subject getItem(int position) {
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
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.row_subject_list, null);

            final ViewSubjectHolder viewHolder = new ViewSubjectHolder();
            viewHolder.subjectType = (ImageView) view.findViewById(R.id.subject_type);
            viewHolder.subjectName = (TextView) view.findViewById(R.id.subject_name);
            viewHolder.teacherName = (TextView) view.findViewById(R.id.teacher_name);

            curSubject = values.get(position);
            final Subject subjectForRun = curSubject;


            switch (values.get(position).getSubjectType()){
                case 0:viewHolder.subjectType.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.technical));break;
                case 1:viewHolder.subjectType.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.humanitarian));break;
                case 2:viewHolder.subjectType.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.sport));break;
                default:viewHolder.subjectType.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.technical));
            }

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
//                            if (myRun != null) {
//                                myRun.change(subjectForRun);
//                            } else {
//                                Toast.makeText(context, "mySubjectRun is null !", Toast.LENGTH_LONG).show();
//                            }
//                            break;
//                        case 1:
//                            if (myRun != null) {
//                                AlertDialog.Builder ad1 = new AlertDialog.Builder(getContext());
//                                ad1.setMessage("Do you want to delete this subject ?");
//                                ad1.setCancelable(false);
//
//                                ad1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface arg0, int arg1) {
//                                    }
//                                });
//                                ad1.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface arg0, int arg1) {
//                                        myRun.delete(subjectForRun);
//                                    }
//                                });
//                                AlertDialog alert = ad1.create();
//                                alert.show();
//                            } else {
//                                Toast.makeText(context, "mySubjectRun is null !", Toast.LENGTH_LONG).show();
//                            }
//                            break;
//                        case 2:
//                            if (myRun != null) {
//                                myRun.change(subjectForRun);
//                            } else {
//                                Toast.makeText(context, "mySubjectRun is null !", Toast.LENGTH_LONG).show();
//                            }
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
//                    dialogEventType.show();
//                }
//            });


            view.setTag(viewHolder);
        } else {
            view = convertView;
        }

        ViewSubjectHolder holder = (ViewSubjectHolder) view.getTag();
        holder.subjectName.setText(values.get(position).getName());
        holder.teacherName.setText(values.get(position).getTeacher());
        //holder.hours.setText(values.get(position).getHours());

        return view;
    }
}

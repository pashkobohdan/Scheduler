package com.pashkobohdan.scheduler;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.pashkobohdan.scheduler.library.Event;
import com.pashkobohdan.scheduler.library.timeWorker.Subject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import layout.Events;
import layout.Subjects;

public class Editing_subject extends AppCompatActivity {

    protected int res_code = -1;

    private Subject currentSubject = null;

    LayoutInflater inflater;

    Dialog dialogEventType, dialogHoursType;
    View childType, childHours;

    EditText subjectName;
    EditText teacherName;
    TextView subjectType;
    TextView hours;
    Button buttonApply;

    private void createContextMenus() {
        ListView listView = (ListView) childType.findViewById(R.id.listView_context_menu);

        List<ContextMenuItem> contextMenuItems = new ArrayList<ContextMenuItem>();
        contextMenuItems.add(new ContextMenuItem(getResources().getDrawable(R.mipmap.technical), "Technical"));
        contextMenuItems.add(new ContextMenuItem(getResources().getDrawable(R.mipmap.humanitarian), "Humanitarian"));
        contextMenuItems.add(new ContextMenuItem(getResources().getDrawable(R.mipmap.sport), "Sport"));

        ContextMenuAdapter adapter = new ContextMenuAdapter(this, contextMenuItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                dialogEventType.dismiss();

                switch (position) {
                    case 0:
                        currentSubject.setSubjectType(Subject.TECHNICAL);
                        subjectType.setText("Technical");
                        break;
                    case 1:
                        currentSubject.setSubjectType(Subject.HUMANITARIAN);
                        subjectType.setText("Humanitarian");
                        break;
                    case 2:
                        currentSubject.setSubjectType(Subject.SPORT);
                        subjectType.setText("Sport");
                        break;
                    default:
                        break;
                }
            }
        });

        dialogEventType = new Dialog(this);
        dialogEventType.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEventType.setContentView(childType);


        ///

        ListView listView1 = (ListView) childHours.findViewById(R.id.listView_context_menu);
        List<ContextMenuItem> contextMenuItems1 = new ArrayList<ContextMenuItem>();
        contextMenuItems1.add(new ContextMenuItem(getResources().getDrawable(R.mipmap.easy), "8"));
        contextMenuItems1.add(new ContextMenuItem(getResources().getDrawable(R.mipmap.medium), "16"));
        contextMenuItems1.add(new ContextMenuItem(getResources().getDrawable(R.mipmap.hard), "32"));

        ContextMenuAdapter adapter1 = new ContextMenuAdapter(this, contextMenuItems1);
        listView1.setAdapter(adapter1);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                dialogHoursType.dismiss();

                switch (position) {
                    case 0:
                        currentSubject.setHours(Subject.HOURS_8);
                        hours.setText(Subject.HOURS_8+"");
                        break;
                    case 1:
                        currentSubject.setHours(Subject.HOURS_16);
                        hours.setText(Subject.HOURS_16+"");
                        break;
                    case 2:
                        currentSubject.setHours(Subject.HOURS_32);
                        hours.setText(Subject.HOURS_32+"");
                        break;
                    default:
                        break;
                }
            }
        });

        dialogHoursType = new Dialog(this);
        dialogHoursType.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogHoursType.setContentView(childHours);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing_subject);

        currentSubject = Subjects.currentEditingSubject;

        subjectName = (EditText) findViewById(R.id.enter_subject_name);
        teacherName = (EditText) findViewById(R.id.enter_teacher_name);
        subjectType = (TextView) findViewById(R.id.enter_subject_type);
        hours = (TextView) findViewById(R.id.enter_subject_hours);
        buttonApply = (Button) findViewById(R.id.apply_subject);

        subjectName.setImeOptions(EditorInfo.IME_ACTION_DONE);
        teacherName.setImeOptions(EditorInfo.IME_ACTION_DONE);

        inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        childType = inflater.inflate(R.layout.listview_context_menu, null);
        childHours = inflater.inflate(R.layout.listview_context_menu, null);
        createContextMenus();

        subjectType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogEventType.show();
            }
        });
        hours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogHoursType.show();
            }
        });

        buttonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSubject.setName(subjectName.getText().toString());
                currentSubject.setTeacher(teacherName.getText().toString());

                if (currentSubject.getName().toCharArray().toString() != null
                        && currentSubject.getTeacher() != null) {
                    setResult(res_code);
                    finish();
                } else {
                    AlertDialog.Builder ad1 = new AlertDialog.Builder(Editing_subject.this);
                    ad1.setMessage("Something of data are uncorrected. Exit without saving ?");
                    ad1.setCancelable(false);

                    ad1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                        }
                    });
                    ad1.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            setResult(-1);
                            finish();
                        }
                    });
                    AlertDialog alert = ad1.create();
                    alert.show();
                }
            }
        });

        /// init data (if event object not null

        if (currentSubject.getName() == null) {
            res_code = 0;
            subjectType.setText("Technical");
            hours.setText(Subject.HOURS_16+"");
            return;
        }
        res_code = 1;

        subjectName.setText(currentSubject.getName());
        teacherName.setText(currentSubject.getTeacher());

        switch (currentSubject.getSubjectType()) {
            case 0:
                subjectType.setText("Technical");
                break;
            case 1:
                subjectType.setText("Humanitarian");
                break;
            case 2:
                subjectType.setText("Sport");
                break;
            default:
                subjectType.setText("Click here");
        }


        hours.setText(currentSubject.getHours()+"");
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder ad1 = new AlertDialog.Builder(this);
        ad1.setMessage("Are you sure you want to exit? ");
        ad1.setCancelable(false);


        ad1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        ad1.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                setResult(-1);
                finish();
            }
        });
        AlertDialog alert = ad1.create();
        alert.show();
    }
}

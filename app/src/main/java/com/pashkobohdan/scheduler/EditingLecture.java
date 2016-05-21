package com.pashkobohdan.scheduler;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pashkobohdan.scheduler.library.dateBaseHelper.DatabaseHelper;
import com.pashkobohdan.scheduler.library.dateBaseHelper.ReadData;
import com.pashkobohdan.scheduler.library.timeWorker.Day;
import com.pashkobohdan.scheduler.library.timeWorker.Lecture;
import com.pashkobohdan.scheduler.library.timeWorker.LectureNumber;
import com.pashkobohdan.scheduler.library.timeWorker.Subject;
import com.pashkobohdan.scheduler.library.timeWorker.Time;

import java.util.LinkedList;
import java.util.List;

import layout.Lectures;

public class EditingLecture extends AppCompatActivity {

    protected int res_code = -1;
    private Lecture currentLecture = null;

    private TextView lectureName, lecture_day, lecture_time;
    private RadioButton first, second, both;
    private Button apply;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing_lecture);

        currentLecture = Lectures.currentEditingLecture;

        lectureName = (TextView) findViewById(R.id.enter_lecture_subject);
        lecture_day = (TextView) findViewById(R.id.enter_lecture_day);
        lecture_time = (TextView) findViewById(R.id.enter_lecture_time);
        first = (RadioButton) findViewById(R.id.radioButtonFirst);
        second = (RadioButton) findViewById(R.id.radioButtonSecond);
        both = (RadioButton) findViewById(R.id.radioButtonThird);
        apply = (Button) findViewById(R.id.apply_subject);

        lectureName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerForContextMenu(v);
                openContextMenu(v);
                unregisterForContextMenu(v);
            }
        });
        lecture_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerForContextMenu(v);
                openContextMenu(v);
                unregisterForContextMenu(v);
            }
        });
        lecture_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerForContextMenu(v);
                openContextMenu(v);
                unregisterForContextMenu(v);
            }
        });


        first.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                currentLecture.setNumberOfWeek(Lecture.FIRST_WEEK);
            }
        });
        second.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                currentLecture.setNumberOfWeek(Lecture.SECOND_WEEK);
            }
        });
        both.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                currentLecture.setNumberOfWeek(Lecture.BOTH_WEEK);
            }
        });


        ReadData.refreshSubjects();


        apply.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         setResult(res_code);
                                         finish();
                                     }
                                 }
        );


        if (currentLecture.getSubject() == null) {
            res_code = 0;
            return;
        }
        res_code = 1;

        lectureName.setText(currentLecture.getSubject().getName());

        if (currentLecture.getDay() != null) {
            lecture_day.setText(Day.getStringDay(currentLecture.getDay()));
        }


    }


    final int MENU_SUBJECT = 1;
    final int MENU_DAY = 2;
    final int MENU_TIME = 3;

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v.getId() == R.id.enter_lecture_subject) {
            for (int i = 0; i < ReadData.getSubjectList().size(); i++)
                menu.add(MENU_SUBJECT, i, 0, ReadData.getSubjectList().get(i).getName());

        } else if (v.getId() == R.id.enter_lecture_day) {
            for (int i = 0; i < 7; i++) {
                menu.add(MENU_DAY, i, 0, Day.getStringDay(i));
            }
        } else if (v.getId() == R.id.enter_lecture_time) {
            for (int i = 1; i < 7; i++) {
                menu.add(MENU_TIME, i, 0, LectureNumber.getLectureNumber(i).toString());
            }
        }
    }

    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getGroupId()) {
            case MENU_SUBJECT:
                currentLecture.setSubject(ReadData.getSubjectList().get(item.getItemId()));
                lectureName.setText(currentLecture.getSubject().getName());
                break;
            case MENU_DAY:
                currentLecture.setDay(Day.getDay(item.getItemId()));
                lecture_day.setText(Day.getStringDay(currentLecture.getDay()));
                break;
            case MENU_TIME:
                currentLecture.setStartTime(LectureNumber.getLectureNumber(item.getItemId() ).getStart());
                currentLecture.setEndTime(LectureNumber.getLectureNumber(item.getItemId() ).getEnd());
                lecture_time.setText(LectureNumber.getLectureNumber(item.getItemId()).toString());
                break;
//            case MENU_COLOR_BLUE:
//                tvColor.setTextColor(Color.BLUE);
//                tvColor.setText("Text color = blue");
//                break;
//            // menu items for tvSize
//            case MENU_SIZE_22:
//                tvSize.setTextSize(22);
//                tvSize.setText("Text size = 22");
//                break;
//            case MENU_SIZE_26:
//                tvSize.setTextSize(26);
//                tvSize.setText("Text size = 26");
//                break;
//            case MENU_SIZE_30:
//                tvSize.setTextSize(30);
//                tvSize.setText("Text size = 30");
//                break;
        }
        return super.onContextItemSelected(item);
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

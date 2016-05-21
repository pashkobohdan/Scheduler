package com.pashkobohdan.scheduler;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.pashkobohdan.scheduler.library.Event;
import com.pashkobohdan.scheduler.library.timeWorker.Time;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import layout.Events;

class ContextMenuItem {

    Drawable drawable;
    String text;

    public ContextMenuItem(Drawable drawable, String text) {
        super();
        this.drawable = drawable;
        this.text = text;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

class ContextMenuAdapter extends BaseAdapter {

    Context context;
    List<ContextMenuItem> listContextMenuItems;
    LayoutInflater inflater;

    public ContextMenuAdapter(Context context, List<ContextMenuItem> listContextMenuItems) {
        super();
        this.context = context;
        this.listContextMenuItems = listContextMenuItems;
    }

    static class ViewHolder {
        protected ImageView imageView;
        protected TextView textView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.context_menu_item, parent,
                    false);
            viewHolder.imageView = (ImageView) convertView
                    .findViewById(R.id.imageView_menu);
            viewHolder.textView = (TextView) convertView
                    .findViewById(R.id.textView_menu);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.imageView.setImageDrawable(listContextMenuItems
                .get(position).getDrawable());
        viewHolder.textView.setText(listContextMenuItems.get(position)
                .getText());
        return convertView;

    }

    @Override
    public int getCount() {
        return listContextMenuItems.size();
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

public class EditingEvent extends AppCompatActivity {
    public static final int RESULT_NEW = 0;
    public static final int RESULT_EDIT = 1;
    protected int res_code = -1;

    private Event currentEvent = null;

    LayoutInflater inflater;

    Dialog dialogEventType, dialogPriorityTypes;
    View childType, childPriority;


    EditText eventName;
    TextView eventType;
    TextView durationTime;
    TextView priority, toDateDate, toDateTime, startDateDate, startDateTime;
    TextView autoSetTime;
    Button buttonApply;

    private void createContextMenus() {
        ListView listView = (ListView) childType.findViewById(R.id.listView_context_menu);

        List<ContextMenuItem> contextMenuItems = new ArrayList<ContextMenuItem>();
        contextMenuItems.add(new ContextMenuItem(getResources().getDrawable(R.mipmap.action_my), "Simple"));
        contextMenuItems.add(new ContextMenuItem(getResources().getDrawable(R.mipmap.learn_my), "Subject"));
        contextMenuItems.add(new ContextMenuItem(getResources().getDrawable(R.mipmap.sheets_my), "Lab"));

        ContextMenuAdapter adapter = new ContextMenuAdapter(this, contextMenuItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                dialogEventType.dismiss();

                switch (position) {
                    case 0:
                        currentEvent.setType(Event.SIMPLE_TYPE);
                        eventType.setText("Simple");
                        break;
                    case 1:
                        currentEvent.setType(Event.SUBJECT_TYPE);
                        eventType.setText("Subject");
                        break;
                    case 2:
                        currentEvent.setType(Event.LAB_TYPE);
                        eventType.setText("Labs");
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

        ListView listView1 = (ListView) childPriority.findViewById(R.id.listView_context_menu);
        List<ContextMenuItem> contextMenuItems1 = new ArrayList<ContextMenuItem>();
        contextMenuItems1.add(new ContextMenuItem(getResources().getDrawable(R.mipmap.min_speed), "Min speed"));
        contextMenuItems1.add(new ContextMenuItem(getResources().getDrawable(R.mipmap.middle_speed), "Middle speed"));
        contextMenuItems1.add(new ContextMenuItem(getResources().getDrawable(R.mipmap.max_speed), "Max speed"));

        ContextMenuAdapter adapter1 = new ContextMenuAdapter(this, contextMenuItems1);
        listView1.setAdapter(adapter1);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                dialogPriorityTypes.dismiss();

                switch (position) {
                    case 0:
                        currentEvent.setPriority(Event.SMALL_PRIORITY);
                        priority.setText("Min speed");
                        break;
                    case 1:
                        currentEvent.setPriority(Event.MIDDLE_PRIORITY);
                        priority.setText("Middle speed");
                        break;
                    case 2:
                        currentEvent.setPriority(Event.HARD_PRIORITY);
                        priority.setText("Max speed");
                        break;
                    default:
                        break;
                }
            }
        });

        dialogPriorityTypes = new Dialog(this);
        dialogPriorityTypes.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogPriorityTypes.setContentView(childPriority);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing_event);

        currentEvent = Events.currentEditingEvent;

        eventName = (EditText) findViewById(R.id.editText3);
        eventType = (TextView) findViewById(R.id.textView14);
        durationTime = (TextView) findViewById(R.id.durationTextView);
        priority = (TextView) findViewById(R.id.textView15);
        toDateDate = (TextView) findViewById(R.id.textView9);
        toDateTime = (TextView) findViewById(R.id.textView10);
        startDateDate = (TextView) findViewById(R.id.textView12);
        startDateTime = (TextView) findViewById(R.id.textView13);
        autoSetTime = (TextView) findViewById(R.id.autoStart);
        buttonApply = (Button) findViewById(R.id.button3);

        eventName.setImeOptions(EditorInfo.IME_ACTION_DONE);

        inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        childType = inflater.inflate(R.layout.listview_context_menu, null);
        childPriority = inflater.inflate(R.layout.listview_context_menu, null);
        createContextMenus();

        eventType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogEventType.show();
            }
        });
        priority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPriorityTypes.show();
            }
        });

        toDateDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(1);
            }
        });
        toDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(2);
            }
        });
        durationTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(3);
            }
        });
        startDateDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(4);
            }
        });
        startDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(5);
            }
        });

        buttonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentEvent.setName(eventName.getText().toString());

                if (currentEvent.getName().toCharArray().toString() != null
                        && currentEvent.getName().toCharArray().length != 0
                        && currentEvent.getLength()!=null
                        && currentEvent.getCompleteDate()!=null
                        && currentEvent.getStartTime()!=null) {
                    setResult(res_code);
                    finish();
                } else {
                    AlertDialog.Builder ad1 = new AlertDialog.Builder(EditingEvent.this);
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

        if (currentEvent.getName() == null) {
            res_code = 0;
            eventType.setText("Simple");
            priority.setText("Min priority");
            return;
        }
        res_code = 1;

        eventName.setText(currentEvent.getName());

        switch (currentEvent.getType()) {
            case 0:
                eventType.setText("Simple");
                break;
            case 1:
                eventType.setText("Subject");
                break;
            case 2:
                eventType.setText("Lab");
                break;
            default:
                eventType.setText("Click here");
        }


        switch (currentEvent.getPriority()) {
            case 0:
                priority.setText("Min priority");
                break;
            case 1:
                priority.setText("Middle  priority");
                break;
            case 2:
                priority.setText("Max  priority");
                break;
            default:
                priority.setText("Click to change");
        }

        toDateDate.setText(new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(currentEvent.getCompleteDate()));
        toDateTime.setText(new SimpleDateFormat("HH:mm", java.util.Locale.getDefault()).format(currentEvent.getCompleteDate()));
        durationTime.setText(currentEvent.getLength().getHour() + ":" + currentEvent.getLength().getMinute());
        startDateDate.setText(new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(currentEvent.getStartTime()));
        startDateTime.setText(new SimpleDateFormat("HH:mm", java.util.Locale.getDefault()).format(currentEvent.getStartTime()));
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

    static Date currentDate = new Date();

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 1) {//end date
            if (res_code == 0) {
                return new DatePickerDialog(this, myDateListener, currentDate.getYear() + 1900, currentDate.getMonth(), currentDate.getDay());
            }
            return new DatePickerDialog(this, myDateListener, currentEvent.getCompleteDate().getYear()+1900, currentEvent.getCompleteDate().getMonth(), currentEvent.getCompleteDate().getDay());
        }
        if (id == 2) {//end time
            if (res_code == 0) {
                return new TimePickerDialog(this, myTimeListener, currentDate.getHours(), currentDate.getMinutes(), true);
            }
            return new TimePickerDialog(this, myTimeListener, currentEvent.getCompleteDate().getHours(), currentEvent.getCompleteDate().getMinutes(), true);
        }
        if (id == 3) {//duration
            if (res_code == 0) {
                return new TimePickerDialog(this, myDurationTimeListener, 1, 0, true);
            }
            return new TimePickerDialog(this, myDurationTimeListener, currentEvent.getLength().getHour(), currentEvent.getLength().getMinute(), true);
        }
        if (id == 4) {//start date
            if (res_code == 0) {
                return new DatePickerDialog(this, myStartDateListener, currentDate.getYear() + 1900, currentDate.getMonth(), currentDate.getDay());
            }
            return new DatePickerDialog(this, myStartDateListener, currentEvent.getStartTime().getYear()+1900, currentEvent.getStartTime().getMonth(), currentEvent.getStartTime().getDay());
        }
        if (id == 5) {//start time
            if (res_code == 0) {
                return new TimePickerDialog(this, myStartTimeListener, currentDate.getHours(), currentDate.getMinutes(), true);
            }
            return new TimePickerDialog(this, myStartTimeListener, currentEvent.getStartTime().getHours(), currentEvent.getStartTime().getMinutes(), true);
        }

        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            if (currentEvent.getCompleteDate() == null) {
                currentEvent.setCompleteDate(new Date());
            } else {
                currentEvent.getCompleteDate().setYear(arg1 - 1900);
                currentEvent.getCompleteDate().setMonth(arg2);
                currentEvent.getCompleteDate().setDate(arg3);
            }
            toDateDate.setText(new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(currentEvent.getCompleteDate()));
        }
    };

    private TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if (currentEvent.getCompleteDate() == null) {
                currentEvent.setCompleteDate(new Date());
            } else {
                currentEvent.getCompleteDate().setHours(hourOfDay);
                currentEvent.getCompleteDate().setMinutes(minute);
            }
            toDateTime.setText(new SimpleDateFormat("HH:mm", java.util.Locale.getDefault()).format(currentEvent.getCompleteDate()));
        }
    };

    private TimePickerDialog.OnTimeSetListener myDurationTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if (currentEvent.getLength() == null) {
                currentEvent.setLength(new Time(1, 0));
            } else {
                currentEvent.getLength().setHour(hourOfDay);
                currentEvent.getLength().setMinute(minute);
            }
            durationTime.setText(currentEvent.getLength().getHour() + ":" + currentEvent.getLength().getMinute());
        }
    };

    private DatePickerDialog.OnDateSetListener myStartDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            if (currentEvent.getStartTime() == null) {
                currentEvent.setStartTime(new Date());
            } else {
                currentEvent.getStartTime().setYear(arg1 - 1900);
                currentEvent.getStartTime().setMonth(arg2);
                currentEvent.getStartTime().setDate(arg3);
            }
            startDateDate.setText(new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(currentEvent.getStartTime()));
        }
    };

    private TimePickerDialog.OnTimeSetListener myStartTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if (currentEvent.getStartTime() == null) {
                currentEvent.setStartTime(new Date());
            } else {
                currentEvent.getStartTime().setHours(hourOfDay);
                currentEvent.getStartTime().setMinutes(minute);
            }
            startDateTime.setText(new SimpleDateFormat("HH:mm", java.util.Locale.getDefault()).format(currentEvent.getStartTime()));
        }
    };

}

package layout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pashkobohdan.scheduler.EditingEvent;
import com.pashkobohdan.scheduler.EditingLecture;
import com.pashkobohdan.scheduler.Editing_subject;
import com.pashkobohdan.scheduler.MainActivity;
import com.pashkobohdan.scheduler.R;
import com.pashkobohdan.scheduler.library.Event;
import com.pashkobohdan.scheduler.library.adapters.GridAdapter;
import com.pashkobohdan.scheduler.library.dateBaseHelper.ReadData;
import com.pashkobohdan.scheduler.library.listWorker.MyListAdapter;
import com.pashkobohdan.scheduler.library.timeWorker.Day;
import com.pashkobohdan.scheduler.library.timeWorker.Lecture;
import com.pashkobohdan.scheduler.library.timeWorker.LectureNumber;
import com.pashkobohdan.scheduler.library.timeWorker.Subject;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import android.view.ViewGroup.LayoutParams;

/**
 *
 */
public class Lectures extends Fragment {

    private OnFragmentInteractionListener mListener;

    String subj = "Subject";
    String teach = "Teacher";
    String hours = "16";

    HorizontalScrollView horizontalScrollView;
    GridLayout gridLayout;
    View rootView;

    List<Button> buttonList;
    List<RelativeLayout> layouts;


    private int oneWidth, oneHeight;

    int today = 2;

    public static Lecture currentEditingLecture = null;

    public Lectures() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    int noOfItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_lectures, container, false);
        getActivity().setTitle("Schedule");

        buttonList = new LinkedList<>();
        layouts = new LinkedList<>();

        gridLayout = (GridLayout) rootView.findViewById(R.id.gridLayout);
        horizontalScrollView = (HorizontalScrollView) rootView.findViewById(R.id.horizontalScrollView2);

        ReadData.refreshLectures();


//        gridLayout.setOrientation(GridLayout.VERTICAL);
//        gridLayout.setColumnCount(3);
//        gridLayout.setRowCount(7);

        TextView[] textViews = new TextView[49];

        oneWidth = horizontalScrollView.getWidth() / 7 < dpToPx(60) ? dpToPx(60) : horizontalScrollView.getWidth() / 7;
        oneHeight = horizontalScrollView.getHeight() / 7;


//        for (int i = 0; i < 49; i++) {
//            textViews[i] = new TextView(getContext());
//            textViews[i].setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
//                    LayoutParams.WRAP_CONTENT));
//            textViews[i].setText(String.valueOf(i));
//            textViews[i].setTextSize(25);
//            textViews[i].setPadding(25, 25, 25, 25);
//            textViews[i].setBackgroundColor(Color.YELLOW);
//            //textViews[i].setBackgroundResource(R.drawable.button_dark_gradient);
//
//            //textViews[i].setMinimumHeight(horizontalScrollView.getHeight()/7);
//
//            textViews[i].setMinimumHeight(oneHeight);
//            textViews[i].setMinimumWidth(oneWidth);
//
//            gridLayout.addView(textViews[i]);
//        }
//
//        //getActivity().setContentView(gridLayout);
//        for (noOfItems = 0; noOfItems < 8; noOfItems++) {
//            textViews[noOfItems].setOnClickListener(new View.OnClickListener() {
//
//                int pos = noOfItems;
//
//                public void onClick(View v) {
//                    Toast.makeText(getContext(), pos + " Clicked",
//                            Toast.LENGTH_SHORT).show();
//                }
//            });
//        }


//        horizontalScrollView.post(new Runnable() {
//            @Override
//            public void run() {
//
//                int one_width = horizontalScrollView.getWidth() / 7 < dpToPx(60) ? dpToPx(60) : horizontalScrollView.getWidth() / 7;
//                int one_height = horizontalScrollView.getMeasuredHeight() / 7;
//                GridAdapter gridAdapter = new GridAdapter(getContext(), ReadData.getLecturesList(), one_width, one_height);
//                gridLayout.setAdapter(gridAdapter);
//            }
//        });
        horizontalScrollView.post(new Runnable() {
            @Override
            public void run() {

                int one_width = horizontalScrollView.getWidth() / 7 < dpToPx(60) ? dpToPx(60) : horizontalScrollView.getWidth() / 7;
                int one_height = horizontalScrollView.getMeasuredHeight() / 7;
                DrawerLayout.LayoutParams l = new DrawerLayout.LayoutParams(one_width, one_height);

                String[] words = {"ПН", "ВТ", "СР", "ЧТ", "ПТ", "Сб", "ВС"};

                for (int i = 0; i < words.length; i++) {


                    Button b = new Button(getContext());
                    RelativeLayout r = new RelativeLayout(getContext());

                    b.setText(words[i]);
                    b.setLayoutParams(l);

                    if (i == today) {
                        b.setBackground(getResources().getDrawable(R.drawable.button_today));
                    } else {
                        b.setBackground(getResources().getDrawable(R.drawable.day_button));
                    }

                    r.addView(b);
                    gridLayout.addView(r);
                }


                Button[][] lectures = new Button[6][7];
                for (int i = 0; i < lectures.length; i++) {
                    for (int j = 0; j < lectures[i].length; j++) {
                        lectures[i][j] = new Button(getContext());
                        lectures[i][j].setBackground(getResources().getDrawable(R.drawable.button_dark_gradient));
                        lectures[i][j].setLayoutParams(l);
                    }
                }

                for (Lecture lecture : ReadData.getLecturesList()) {


                    lectures[LectureNumber.getLectureNumber(lecture.getStartTime(), lecture.getEndTime())-1]
                            [Day.getDayNumber(lecture.getDay())-1]
                            .setText(lecture.getSubject().getName());

//                    Button b = new Button(getContext());
//                    RelativeLayout r = new RelativeLayout(getContext());
//
//                    b.setText(lecture.getSubject().getName());
//                    b.setLayoutParams(l);
//
//                    b.setBackground(getResources().getDrawable(R.drawable.button_dark_gradient));
//
//
//                    r.addView(b);
//                    gridLayout.addView(r);

                }

                for (int i = 0; i < lectures.length; i++) {
                    for (int j = 0; j < lectures[i].length; j++) {

                        RelativeLayout r = new RelativeLayout(getContext());
                        r.addView(lectures[i][j]);

                        if(lectures[i][j].getText().equals("")){
                            r.setVisibility(View.INVISIBLE);
                        }

                        gridLayout.addView(r);
                    }
                }

//                for (int i = 0; i < words.length; i++) {
//
//                    for (int j = 0; j < words[i].length; j++) {
//
//                        Button b = new Button(getContext());
//                        RelativeLayout r = new RelativeLayout(getContext());
//
//                        b.setText(words[i][j]);
//                        b.setLayoutParams(l);
//
//                        if (i == 0) {
//                            if (j == today) {
//                                b.setBackground(getResources().getDrawable(R.drawable.button_today));
//                            } else {
//                                b.setBackground(getResources().getDrawable(R.drawable.day_button));
//                            }
//                        } else {
//                            b.setBackground(getResources().getDrawable(R.drawable.button_dark_gradient));
//                            b.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    new AlertDialog.Builder(getActivity())
//                                            .setTitle("Detail info")
//                                            .setMessage("Subj: " + subj + "\nTeacher: " + teach + "\nHours: " + hours)
//                                            .setCancelable(false)
//                                            .setNeutralButton("Edit", new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialog, int which) {
//
//                                                }
//                                            })
//                                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialog, int which) {
//                                                    Toast.makeText(getContext(), "google", Toast.LENGTH_LONG).show();
//                                                }
//                                            })
//                                            .create()
//                                            .show();
//                                }
//                            });
//                        }
//
//                        if (b.getText().equals("")) {
//                            b.setVisibility(View.GONE);
//                        }
//                        r.addView(b);
//                        gridLayout.addView(r);
//
//                        //buttonList.add(b);
//                        //layouts.add(r);
//                    }
//                }

            }


        });
//
        RelativeLayout canvasLayout = (RelativeLayout) rootView.findViewById(R.id.canvas_layout);
        canvasLayout.addView(new DrawView(getContext()));


        RelativeLayout scrollViewLayout = (RelativeLayout) rootView.findViewById(R.id.relative_with);//relative_with_scroll_view
        scrollViewLayout.addView(new DrawViewLines(getContext()));


        return rootView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 0) {
            ReadData.getLecturesList().add(currentEditingLecture);

            if (!ReadData.addLecture(currentEditingLecture)) {
                Toast.makeText(getContext(), "ERROR ! : ", Toast.LENGTH_LONG).show();
            }

            if (!ReadData.refreshLectures()) {
                Toast.makeText(getContext(), "ERROR ! : ", Toast.LENGTH_LONG).show();
            }

            int one_width = horizontalScrollView.getWidth() / 7 < dpToPx(60) ? dpToPx(60) : horizontalScrollView.getWidth() / 7;
            int one_height = horizontalScrollView.getHeight() / 6;
//            GridAdapter gridAdapter = new GridAdapter(getContext(), ReadData.getLecturesList(), one_width, one_height);
////            gridLayout.setAdapter(null);
//            gridLayout.setAdapter(gridAdapter);


        } else {

        }
    }

    public void clickOptionsMenu(int id) {
        switch (id) {
            case 1:
                currentEditingLecture = new Lecture();
                Intent article_activity = new Intent(getContext(), EditingLecture.class);
                startActivityForResult(article_activity, 1);
                break;
        }
    }

    class DrawView extends View {

        public DrawView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            float one_height = (float) horizontalScrollView.getHeight() / 7;

            Paint fontPaint = new Paint(Color.RED);
            fontPaint.setTextSize(dpToPx(15));
            fontPaint.setStyle(Paint.Style.STROKE);
            canvas.drawText("08:30", dpToPx(15), one_height + 7, fontPaint);
            canvas.drawText("10:25", dpToPx(15), one_height * 2 + 7, fontPaint);
            canvas.drawText("12:35", dpToPx(15), one_height * 3 + 7, fontPaint);
            canvas.drawText("14:30", dpToPx(15), one_height * 4 + 7, fontPaint);
            canvas.drawText("16:25", dpToPx(15), one_height * 5 + 7, fontPaint);
            canvas.drawText("18:10", dpToPx(15), one_height * 6 + 7, fontPaint);
        }

    }

    class DrawViewLines extends View {

        public DrawViewLines(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            float one_height = (float) (horizontalScrollView.getMeasuredHeight() / 7);

            Paint fontPaint = new Paint(Color.RED);
            fontPaint.setTextSize(20);
            fontPaint.setStyle(Paint.Style.STROKE);

            for (int i = 0; i < 7; i++) {
                canvas.drawLine(0, one_height * (i + 1) + (i > 3 ? 0 : 0), oneWidth * 7, one_height * (i + 1) + (i > 3 ? 0 : 0), fontPaint);
            }


            for (int i = 0; i < 7; i++) {
                canvas.drawLine(dpToPx(60) * i, one_height, dpToPx(60) * i, horizontalScrollView.getHeight() - 1, fontPaint);
            }
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int width = oneWidth * 7;
            int height = horizontalScrollView.getHeight();
            setMeasuredDimension(width, height);
        }

    }

    public void resizeAll(int position) {

//        gridLayout.removeAllViews();
//        if (position == Configuration.ORIENTATION_PORTRAIT) {
//            gridLayout.setOrientation(GridLayout.VERTICAL);
//            oneWidth = horizontalScrollView.getMeasuredWidth() / 7 < dpToPx(60) ? dpToPx(60) : horizontalScrollView.getMeasuredWidth() / 7;
//            oneHeight = horizontalScrollView.getMeasuredHeight() / 7;
//        } else {
//            gridLayout.setOrientation(GridLayout.HORIZONTAL);
//            oneWidth = horizontalScrollView.getMeasuredHeight() / 7;
//            oneHeight = horizontalScrollView.getMeasuredWidth() / 7 < dpToPx(60) ? dpToPx(60) : horizontalScrollView.getMeasuredWidth() / 7;
//        }
//
//        Toast.makeText(getContext(), "sizes : w: " + oneWidth+",    h: "+oneHeight, Toast.LENGTH_LONG).show();
//
//        gridLayout.setColumnCount(3);
//        gridLayout.setRowCount(7);
//
//        TextView[] textViews = new TextView[49];
//
//        for (int i = 0; i < 49; i++) {
//            textViews[i] = new TextView(getContext());
//            textViews[i].setLayoutParams(new LayoutParams(oneWidth, oneHeight));
//            textViews[i].setText(String.valueOf(i));
//            textViews[i].setTextSize(25);
//           // textViews[i].setPadding(25, 25, 25, 25);
//            //textViews[i].setBackgroundColor(Color.YELLOW);
//            textViews[i].setBackgroundResource(R.drawable.button_dark_gradient);
//
//
//            textViews[i].setMinimumHeight(oneHeight);
//            textViews[i].setMinimumWidth(oneWidth);
//
//            textViews[i].setMaxHeight(oneHeight);
//            textViews[i].setMaxWidth(oneWidth);
//
//            gridLayout.addView(textViews[i]);
//        }
//
//        //getActivity().setContentView(gridLayout);
//        for (noOfItems = 0; noOfItems < 8; noOfItems++) {
//            textViews[noOfItems].setOnClickListener(new View.OnClickListener() {
//
//                int pos = noOfItems;
//
//                public void onClick(View v) {
//                    Toast.makeText(getContext(), pos + " Clicked",
//                            Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        Fragment f = (Fragment) getFragmentManager().findFragmentById(R.id.lecture_day);
//        if (f != null) getFragmentManager().beginTransaction().remove(f).commit();
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add("Subj: " + subj + "\nTeacher: " + teach + "\nHours: " + hours);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            default:
                return super.onContextItemSelected(item);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

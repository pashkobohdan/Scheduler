package layout;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.pashkobohdan.scheduler.EditingEvent;
import com.pashkobohdan.scheduler.Editing_subject;
import com.pashkobohdan.scheduler.R;
import com.pashkobohdan.scheduler.library.Event;
import com.pashkobohdan.scheduler.library.activityHalper.MyRun;
import com.pashkobohdan.scheduler.library.dateBaseHelper.DatabaseHelper;
import com.pashkobohdan.scheduler.library.dateBaseHelper.ReadData;
import com.pashkobohdan.scheduler.library.listWorker.MyListAdapter;
import com.pashkobohdan.scheduler.library.listWorker.MySubjectListAdapter;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Events.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Events#} factory method to
 * create an instance of this fragment.
 */
public class Events extends Fragment {
    public static Event currentEditingEvent = null;

    public int currentListCheckPosition;

    ListView listView;
    private MyRun myRun;

    private OnFragmentInteractionListener mListener;


    public Events() {

        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        final View rootView = inflater.inflate(R.layout.fragment_events, container, false);
        getActivity().setTitle("Events");


        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_plus);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentEditingEvent = new Event();
                Intent article_activity = new Intent(getContext(), EditingEvent.class);

                startActivityForResult(article_activity, 1);
            }
        });


        listView = (ListView) rootView.findViewById(R.id.listView);

        ReadData.refreshEvents();



        listView.setAdapter(new MyListAdapter(getActivity(), ReadData.getEventList()));


        getActivity().registerForContextMenu(listView);
        listView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentListCheckPosition = position;
                listView.showContextMenuForChild(view);
            }
        });



        Toast.makeText(getContext(), "Database has been read", Toast.LENGTH_LONG).show();

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 0) {
            ReadData.getEventList().add(currentEditingEvent);

            ReadData.addEvent(currentEditingEvent);

            listView.setAdapter(new MyListAdapter(getActivity(), ReadData.getEventList()));
        } else {
            if (resultCode == 1) {
                ReadData.editEvent(currentEditingEvent);

                listView.setAdapter(new MyListAdapter(getActivity(), ReadData.getEventList()));
            } else {
                Toast.makeText(getContext(), "error ! " + resultCode, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void edit() {
        currentEditingEvent = ReadData.getEventList().get(currentListCheckPosition);
        Intent article_activity = new Intent(getContext(), EditingEvent.class);
        startActivityForResult(article_activity, 1);
    }

    public void delete() {
        AlertDialog.Builder ad1 = new AlertDialog.Builder(getContext());
        ad1.setMessage("Do you want to delete this subject ?");
        ad1.setCancelable(false);

        ad1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        ad1.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                ReadData.deleteEvent(ReadData.getEventList().get(currentListCheckPosition));

                ReadData.getEventList().remove(ReadData.getEventList().get(currentListCheckPosition));
                listView.setAdapter(new MyListAdapter(getActivity(), ReadData.getEventList()));
            }
        });
        AlertDialog alert = ad1.create();
        alert.show();
    }

    public void open() {
        currentEditingEvent = ReadData.getEventList().get(currentListCheckPosition);
        Intent article_activity = new Intent(getContext(), EditingEvent.class);
        startActivityForResult(article_activity, 1);
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

    public void clickOptionsMenu(int id) {
        switch (id) {
            case 1:
                Collections.sort(ReadData.getEventList(), new Comparator<Event>() {
                    @Override
                    public int compare(Event lhs, Event rhs) {
                        return 0;
                    }
                });
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

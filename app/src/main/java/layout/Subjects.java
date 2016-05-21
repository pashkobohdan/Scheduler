package layout;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.pashkobohdan.scheduler.Editing_subject;
import com.pashkobohdan.scheduler.R;
import com.pashkobohdan.scheduler.library.activityHalper.MySubjectRun;
import com.pashkobohdan.scheduler.library.dateBaseHelper.DatabaseHelper;
import com.pashkobohdan.scheduler.library.dateBaseHelper.ReadData;
import com.pashkobohdan.scheduler.library.listWorker.MySubjectListAdapter;
import com.pashkobohdan.scheduler.library.timeWorker.Subject;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Subjects.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Subjects#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Subjects extends Fragment {

    public static Subject currentEditingSubject = null;

    public int currentListCheckPosition;


    private OnFragmentInteractionListener mListener;
    private ListView listView;

    public Subjects() {
    }

    public static Subjects newInstance() {
        Subjects fragment = new Subjects();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_subjects, container, false);
        getActivity().setTitle("Subjects");

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_plus_subject);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentEditingSubject = new Subject();
                Intent article_activity = new Intent(getContext(), Editing_subject.class);

                startActivityForResult(article_activity, 1);
            }
        });


        listView = (ListView) rootView.findViewById(R.id.subjectList);


        ReadData.refreshSubjects();


        listView.setAdapter(new MySubjectListAdapter(getActivity(), ReadData.getSubjectList()));


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


        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void edit() {
        currentEditingSubject = ReadData.getSubjectList().get(currentListCheckPosition);
        Intent article_activity = new Intent(getContext(), Editing_subject.class);
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
                ReadData.deleteSubject(ReadData.getSubjectList().get(currentListCheckPosition));

                ReadData.getSubjectList().remove(ReadData.getSubjectList().get(currentListCheckPosition));
                listView.setAdapter(new MySubjectListAdapter(getActivity(), ReadData.getSubjectList()));
            }
        });
        AlertDialog alert = ad1.create();
        alert.show();
    }

    public void open() {
        currentEditingSubject = ReadData.getSubjectList().get(currentListCheckPosition);
        Intent article_activity = new Intent(getContext(), Editing_subject.class);
        startActivityForResult(article_activity, 1);
    }

    public void clickOptionsMenu(int id) {
        switch (id) {
            case 1:
                Collections.sort(ReadData.getSubjectList(), new Comparator<Subject>() {
                    @Override
                    public int compare(Subject lhs, Subject rhs) {
                        return lhs.getName().compareTo(rhs.getName());
                    }
                });
                listView.setAdapter(new MySubjectListAdapter(getActivity(), ReadData.getSubjectList()));
                break;
            case 2:
                Collections.sort(ReadData.getSubjectList(), new Comparator<Subject>() {
                    @Override
                    public int compare(Subject lhs, Subject rhs) {
                        int one = lhs.getHours(), two = rhs.getHours();
                        return two > one ? 1 : one == two ? 0 : -1;
                    }
                });
                listView.setAdapter(new MySubjectListAdapter(getActivity(), ReadData.getSubjectList()));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 0) {
            ReadData.getSubjectList().add(currentEditingSubject);

            ReadData.addSubject(currentEditingSubject);

            listView.setAdapter(new MySubjectListAdapter(getActivity(), ReadData.getSubjectList()));

        } else {
            if (resultCode == 1) {
                ReadData.editSubject(currentEditingSubject);

                listView.setAdapter(new MySubjectListAdapter(getActivity(), ReadData.getSubjectList()));
            } else {
                Toast.makeText(getContext(), "error ! " + resultCode, Toast.LENGTH_LONG).show();
            }
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

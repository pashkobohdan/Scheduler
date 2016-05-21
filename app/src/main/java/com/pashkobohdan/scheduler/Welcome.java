package com.pashkobohdan.scheduler;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class Welcome extends AppCompatActivity {

    private Button buttonApply;
    private EditText editTextUniversity, editTextGroup;

    private SharedPreferences userData;
    private static final String DATA = "user_data";

    private static final String[] mandatoryPreferences = {"university_name", "group_name"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        buttonApply = (Button) findViewById(R.id.button);
        editTextUniversity = (EditText) findViewById(R.id.editText);
        editTextGroup = (EditText) findViewById(R.id.editText2);

        userData = getSharedPreferences(DATA, MODE_PRIVATE);
        List<String> result = new LinkedList<>(userData.getAll().keySet());

        if (checkAllData(result)) {
            Toast.makeText(getApplicationContext(), "Data has been read", Toast.LENGTH_LONG).show();
            goToNextPage();
        }

        buttonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextUniversity.getText().toString().length() != 0 && editTextGroup.getText().toString().length() != 0) {
                    userData.edit().putString(mandatoryPreferences[0], editTextUniversity.getText().toString()).apply();
                    userData.edit().putString(mandatoryPreferences[1], editTextGroup.getText().toString()).apply();
                    Toast.makeText(getApplicationContext(), "Data has been write", Toast.LENGTH_LONG).show();
                    goToNextPage();
                } else {
                    Snackbar.make(v, "Enter the correct data !", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }

    private boolean checkAllData(List<String> data) {

        for (String arg : mandatoryPreferences) {
            if (!data.contains(arg)) {
                return false;
            }
        }
        return true;
    }

    private void goToNextPage() {
        Intent article_activity = new Intent(Welcome.this, MainActivity.class);
        //startActivity(article_activity);

        article_activity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(article_activity);
        finish();
    }
}

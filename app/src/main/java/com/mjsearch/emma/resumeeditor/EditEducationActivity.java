package com.mjsearch.emma.resumeeditor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.mjsearch.emma.resumeeditor.R;
import com.mjsearch.emma.resumeeditor.model.BasicInfo;
import com.mjsearch.emma.resumeeditor.model.Education;
import com.mjsearch.emma.resumeeditor.util.DateUtils;

import java.util.Arrays;

/**
 * Created by emmazhuang on 3/31/17.
 */

public class EditEducationActivity extends AppCompatActivity {

    private Education data;
    //public:::because need to use in the MainActivity
    public static final String KEY_EDUCATION = "education";
    public static final String KEY_EDUCATION_ID = "education_id";

    protected int getLayoutId() {
        return R.layout.edit_education;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        data = getIntent().getParcelableExtra(KEY_EDUCATION);
        if(data!=null)
            setupUIForEdit(data);
        else
            setupUIForCreate();
    }

    private void setupUIForEdit(final Education data) {
        ((EditText) findViewById(R.id.edit_Education_school)).setText(data.school);
        ((EditText) findViewById(R.id.edit_Education_major)).setText(data.major);
        ((EditText) findViewById(R.id.edit_Education_startDate)).setText(DateUtils.dateToString(data.startDate));
        ((EditText) findViewById(R.id.edit_Education_endDate)).setText(DateUtils.dateToString(data.endDate));
        ((EditText) findViewById(R.id.edit_Education_courses)).setText(TextUtils.join("\n", data.courses));
        findViewById(R.id.edit_Education_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent result=new Intent();
                result.putExtra(KEY_EDUCATION_ID, data.id);
                setResult(Activity.RESULT_OK, result);
                finish();
            }
        });
        findViewById(R.id.edit_Education_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAndexit(data);
            }
        });
    }

    private void setupUIForCreate() {
        findViewById(R.id.edit_Education_delete).setVisibility(View.GONE);

        findViewById(R.id.edit_Education_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAndexit(data);
            }
        });
    }

    private void saveAndexit(@Nullable Education data) {
        if(data == null)
                data=new Education();

        data.school = ((EditText) findViewById(R.id.edit_Education_school)).getText().toString();
        data.major = ((EditText) findViewById(R.id.edit_Education_major)).getText().toString();
        data.startDate = DateUtils.stringToDate(
                ((TextView) findViewById(R.id.edit_Education_startDate)).getText().toString());
        data.endDate = DateUtils.stringToDate(
                ((TextView) findViewById(R.id.edit_Education_endDate)).getText().toString());
        data.courses = Arrays.asList(TextUtils.split(
                ((EditText) findViewById(R.id.edit_Education_courses)).getText().toString(), "\n"));

        Intent result = new Intent();
        result.putExtra(KEY_EDUCATION, data);
        setResult(Activity.RESULT_OK, result);
        finish();

    };

}

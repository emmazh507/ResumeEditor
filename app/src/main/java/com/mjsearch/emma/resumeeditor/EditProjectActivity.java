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
import com.mjsearch.emma.resumeeditor.model.Project;



public class EditProjectActivity extends AppCompatActivity {
	
	private Project data;
	public static final String KEY_PROJECT = "project";
	public static final String KEY_PROJECT_ID = "project_id";	

	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_project);

		data = getIntent().getParcelableExtra(KEY_PROJECT);
		if(data!=null)
			setupUIForEdit(data);
		else
			setupUIForCreate();		

	}


	private void setupUIForEdit(final Project data) {
		((EditText) findViewById(R.id.edit_project_name)).setText(data.name);
		((EditText) findViewById(R.id.edit_project_content)).setText(data.content);
		
		findViewById(R.id.edit_project_delete).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//why not Intent result = new Intent(EditProjectActivity.this, MainActivity.class);
				Intent result = new Intent();
				result.putExtra(KEY_PROJECT_ID, data.id);
				setResult(Activity.RESULT_OK, result);
				finish();
			}
		});
		
		findViewById(R.id.edit_project_save).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				saveAndexit(data);
			}
		}); 

	}


	private void setupUIForCreate() {
		findViewById(R.id.edit_project_delete).setVisibility(View.GONE);
		
		findViewById(R.id.edit_project_save).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				saveAndexit(data);
			}
		});
	}
	
	private void saveAndexit(@Nullable Project data) {
		if(data==null)
			data = new Project();

		data.name = ((EditText) findViewById(R.id.edit_project_name)).getText().toString();
		data.content = ((EditText) findViewById(R.id.edit_project_content)).getText().toString();
		
		Intent result = new Intent();
		result.putExtra(KEY_PROJECT, data);
		setResult(Activity.RESULT_OK, result);
		finish();
	}

}

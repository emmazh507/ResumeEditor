package com.mjsearch.emma.resumeeditor;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mjsearch.emma.resumeeditor.R;
import com.mjsearch.emma.resumeeditor.model.Experience;
import com.mjsearch.emma.resumeeditor.util.DateUtils;
import com.mjsearch.emma.resumeeditor.util.ImageUtils;

import java.util.Arrays;

public class EditExperienceActivity extends AppCompatActivity {

	public static final String KEY_EXPERIENCE = "experience";
	public static final String KEY_EXPERIENCE_ID = "experienceId";
	
	public Experience data;
	
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_experience);
		
		data = getIntent().getParcelableExtra(KEY_EXPERIENCE);
		
		if(data!=null)
			setupUIForEdit(data);
		else
			setupUIForCreate();
	}

	public void setupUIForEdit(final Experience data) {
		((EditText) findViewById(R.id.edit_company)).setText(data.company);
		((EditText) findViewById(R.id.edit_startDate)).setText(DateUtils.dateToString(data.startDate));
		((EditText) findViewById(R.id.edit_endDate)).setText(DateUtils.dateToString(data.endDate));
		((EditText) findViewById(R.id.edit_content)).setText(data.content);

		findViewById(R.id.edit_experience_delete).setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent result = new Intent();
				result.putExtra(KEY_EXPERIENCE_ID, data.id);
				setResult(Activity.RESULT_OK, result);
				finish();
			}
		}); 		


	}

	public void setupUIForCreate() {
		((TextView) findViewById(R.id.edit_experience_delete)).setVisibility(View.GONE);
	}

	private void saveAndExit(@Nullable Experience data) {
		if(data == null)
			data = new Experience();
		data.company = ((EditText) findViewById(R.id.edit_company)).getText().toString();
		data.startDate = DateUtils.stringToDate(
				((EditText) findViewById(R.id.edit_startDate)).getText().toString());
		data.endDate = DateUtils.stringToDate(
				((EditText) findViewById(R.id.edit_endDate)).getText().toString());
		data.content = ((EditText) findViewById(R.id.edit_content)).getText().toString();

		Intent result = new Intent();
		result.putExtra(KEY_EXPERIENCE, data);
		setResult(Activity.RESULT_OK, result);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_edit, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		} else if (item.getItemId() == R.id.ic_save) {
			saveAndExit(data);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}



}

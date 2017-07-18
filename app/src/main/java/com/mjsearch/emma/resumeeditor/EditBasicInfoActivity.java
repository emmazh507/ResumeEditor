package com.mjsearch.emma.resumeeditor;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.mjsearch.emma.resumeeditor.R;
import com.mjsearch.emma.resumeeditor.model.BasicInfo;
import com.mjsearch.emma.resumeeditor.util.ImageUtils;
import com.mjsearch.emma.resumeeditor.util.PermissionUtils;

/**
 * Created by emmazhuang on 5/14/17.
 */

public class EditBasicInfoActivity extends AppCompatActivity {

    public static final String KEY_BASIC_INFO = "basicinfo";
    public static final int REQ_CODE_PICK_IMAGE = 100;
    private BasicInfo data;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                showImage(imageUri);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PermissionUtils.REQ_CODE_WRITE_EXTERNAL_STORAGE
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            pickImage();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_basicinfo);

        data = getIntent().getParcelableExtra(KEY_BASIC_INFO);

        setupUIForEdit(data);

    }
///test what happens if change data(final) within the function
    private void setupUIForEdit(final BasicInfo data) {
        ((EditText) findViewById(R.id.user_name)).setText(data.name);
        ((EditText) findViewById(R.id.email)).setText(data.email);

        if (data.imageUri != null) {
            showImage(data.imageUri);
        }

        findViewById(R.id.edit_user_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PermissionUtils.checkPermission(EditBasicInfoActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    PermissionUtils.requestReadExternalStoragePermission(EditBasicInfoActivity.this);
                } else {
                    pickImage();
                }
            }
        });

        findViewById(R.id.edit_basic_info_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAndexit(data);
                //share();
            }
        });
    }

    private void saveAndexit(@Nullable BasicInfo data) {
        data.name = ((EditText) findViewById(R.id.user_name)).getText().toString();
        data.email = ((EditText) findViewById(R.id.email)).getText().toString();
        data.imageUri = (Uri) findViewById(R.id.user_picture).getTag();

	    Intent resultIntent = new Intent();
	    resultIntent.putExtra(KEY_BASIC_INFO, data);
	    setResult(Activity.RESULT_OK, resultIntent);
	    finish();
    }


///test function with/without following codes 

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
            saveAndexit(data);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showImage(@NonNull Uri imageUri) {
        ImageView imageView = (ImageView) findViewById(R.id.user_picture);
        //imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        imageView.setTag(imageUri);
        ImageUtils.loadImage(this, imageUri, imageView);
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Select picture"),
                REQ_CODE_PICK_IMAGE);
    }
/*test share
    public void share() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, data.name + " " + data.imageUri);
        shareIntent.setType("text/emma");
        startActivity(Intent.createChooser(shareIntent, getString(R.string.action_save)));
    }
*/
}

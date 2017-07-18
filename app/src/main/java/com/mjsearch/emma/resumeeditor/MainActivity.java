package com.mjsearch.emma.resumeeditor;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;


import java.util.ArrayList;
import java.util.List;

import com.mjsearch.emma.resumeeditor.model.*;
import com.mjsearch.emma.resumeeditor.util.*;
import com.mjsearch.emma.resumeeditor.adapter.ExpAdapter;
import com.mjsearch.emma.resumeeditor.adapter.ExpAdapter.*;



public class MainActivity extends AppCompatActivity implements MyClickListener {

    private static final int REQ_CODE_EDIT_EDUCATION = 100;
    private static final int REQ_CODE_EDIT_EXPERIENCE = 101;
    private static final int REQ_CODE_EDIT_PROJECT = 102;
    private static final int REQ_CODE_EDIT_BASIC_INFO = 103;

    private static final String MODEL_EDUCATIONS = "educations";
    private static final String MODEL_EXPERIENCES = "experiences";
    private static final String MODEL_PROJECTS = "projects";
    private static final String MODEL_BASIC_INFO = "basic_info";

    private BasicInfo basicInfo;
    private List<Education> edus;
    private List<Project> projs;
    private List<Experience> exps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadData();
        setupUI();
    }

    @Override
    public void ClickListener(View v) {
        // 点击之后的操作在这里写
        Intent intent = new Intent(MainActivity.this, EditExperienceActivity.class);
        intent.putExtra(EditExperienceActivity.KEY_EXPERIENCE, exps.get((Integer) v.getTag()));
        startActivityForResult(intent, REQ_CODE_EDIT_EXPERIENCE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQ_CODE_EDIT_BASIC_INFO:
                    BasicInfo basicInfo = data.getParcelableExtra(EditBasicInfoActivity.KEY_BASIC_INFO);
                    updateBasicInfo(basicInfo);
                    break;
                case REQ_CODE_EDIT_EDUCATION:
                    String educationId = data.getStringExtra(EditEducationActivity.KEY_EDUCATION_ID);
                    if (educationId != null) {
                        deleteEducation(educationId);
                    } else {
                        Education education = data.getParcelableExtra(EditEducationActivity.KEY_EDUCATION);
                        updateEducation(education);
                    }
                    break;
                case REQ_CODE_EDIT_PROJECT:
                    String projectId = data.getStringExtra(EditProjectActivity.KEY_PROJECT_ID);
                    if(projectId != null) {
                        deleteProject(projectId);
                    } else {
                        Project project = data.getParcelableExtra(EditProjectActivity.KEY_PROJECT);
                        updateProject(project);
                    }
                    break;
                case REQ_CODE_EDIT_EXPERIENCE:
                    String experienceId = data.getStringExtra(EditExperienceActivity.KEY_EXPERIENCE_ID);
                    if(experienceId!=null) {
                        deleteExperience(experienceId);
                    } else {
                        Experience experience = data.getParcelableExtra(EditExperienceActivity.KEY_EXPERIENCE);
                        updateExperience(experience);
                    }
                    break;
            }

        }

    }

    private void setupUI() {
        setContentView(R.layout.activity_main);

        ImageButton addEduBtn = (ImageButton) findViewById(R.id.add_education_btn);
        addEduBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditEducationActivity.class);
                startActivityForResult(intent, REQ_CODE_EDIT_EDUCATION);
            }
        });

        ImageButton addProjBtn = (ImageButton) findViewById(R.id.add_project_btn);
        addProjBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditProjectActivity.class);
                startActivityForResult(intent, REQ_CODE_EDIT_PROJECT);
            }
        });

        ImageButton addExpBtn = (ImageButton) findViewById(R.id.add_experience_btn);
        addExpBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditExperienceActivity.class);
                startActivityForResult(intent, REQ_CODE_EDIT_EXPERIENCE);
            }
        });
        setupBasicInfoUI();
        setupEducations();
        setupProjects();
        setupExperiences();
    }

    private void setupProjects() {
        LinearLayout projsLayout = (LinearLayout) findViewById(R.id.project_list);
        projsLayout.removeAllViews();
        for(Project proj : projs) {
            View projView = getLayoutInflater().inflate(R.layout.project_item, null);
            setupProject(projView, proj);
            projsLayout.addView(projView);
        }
    }

    private void setupProject(View projView, final Project proj) {
        ((TextView) projView.findViewById(R.id.project_name)).setText(proj.name);
        ((TextView) projView.findViewById(R.id.project_content)).setText(proj.content);

        ((ImageButton) projView.findViewById(R.id.edit_project_btn)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditProjectActivity.class);
                intent.putExtra(EditProjectActivity.KEY_PROJECT, proj);
                startActivityForResult(intent, REQ_CODE_EDIT_PROJECT);
            }
        });
    }

    private void deleteProject(final String projectId) {
        for(int i=0; i<projs.size(); i++) {
            if(TextUtils.equals(projs.get(i).id, projectId)) {
                projs.remove(i);
                break;
            }
        }

        ModelUtils.save(this, MODEL_PROJECTS, projs);
        setupProjects();
    }

    private void updateProject(final Project project) {
        boolean found = false;
        for(int i=0; i<projs.size(); i++) {
            if(TextUtils.equals(projs.get(i).id, project.id)) {
                projs.set(i, project);
                found = true;
                break;
            }
        }
        if(!found)
            projs.add(project);

        ModelUtils.save(this, MODEL_PROJECTS, projs);
        setupProjects();
    }

    private void setupExperiences() {
        ListView expsListView = (ListView) findViewById(R.id.experience_list);
        expsListView.setAdapter(new ExpAdapter(MainActivity.this, exps, this));
         /*  expsListView.setOnItemClickListener(new OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position,
                                       long id) {
                   Intent intent = new Intent(MainActivity.this, EditExperienceActivity.class);
                   intent.putExtra(EditExperienceActivity.KEY_EXPERIENCE, exps.get(position));
                   startActivityForResult(intent, REQ_CODE_EDIT_EXPERIENCE);
               }
           });*/
    }


    private void deleteExperience(final String experienceId) {
        for(int i=0; i<exps.size(); i++) {
            if(TextUtils.equals(exps.get(i).id, experienceId)) {
                exps.remove(i);
                break;
            }
        }

        ModelUtils.save(this, MODEL_EXPERIENCES, exps);
        setupExperiences();
    }

    private void updateExperience(final Experience experience) {
        boolean found = false;
        for(int i=0; i<exps.size(); i++) {
            if(TextUtils.equals(exps.get(i).id, experience.id)) {
                exps.set(i, experience);
                found = true;
                break;
            }
        }
        if(!found)
            exps.add(experience);

        ModelUtils.save(this, MODEL_EXPERIENCES, exps);
        setupExperiences();
    }

    private void setupBasicInfoUI() {
        ((TextView) findViewById(R.id.user_name)).setText(basicInfo.name);
        ((TextView) findViewById(R.id.email)).setText(basicInfo.email);

        ImageView userPicture = (ImageView) findViewById(R.id.user_picture);


        if (basicInfo.imageUri != null) {
            ImageUtils.loadImage(this, basicInfo.imageUri, userPicture);
        } else {
            userPicture.setImageResource(R.drawable.emma);
        }
        //if (basicInfo.imageUri != null) {
        //    ImageUtils.loadImage(this, basicInfo.imageUri, userPicture);
        //} else {
        //userPicture.setImageResource(R.drawable.emma);
        //}

        findViewById(R.id.edit_basic_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditBasicInfoActivity.class);
                intent.putExtra(EditBasicInfoActivity.KEY_BASIC_INFO, basicInfo);
                startActivityForResult(intent, REQ_CODE_EDIT_BASIC_INFO);
            }
        });

    }

    private void updateBasicInfo(final BasicInfo data) {
        basicInfo = data;
        ModelUtils.save(this, MODEL_BASIC_INFO, basicInfo);
        setupBasicInfoUI();
    }

    private void setupEducations() {
        // TODO 3: Display the education data onto the UI
        // Follow the example in setupBasicInfoUI
        // You will probably find formatItems method useful when displaying the courses
        LinearLayout edusLayout = (LinearLayout) findViewById(R.id.education_list);
        edusLayout.removeAllViews();
        for (Education edu : edus) {
            View eduView = getLayoutInflater().inflate(R.layout.education_item, null);
            setupEducation(eduView, edu);
            edusLayout.addView(eduView);
        }
    }

    private void setupEducation(View eduView, final Education edu) {
        String dateString = DateUtils.dateToString(edu.startDate) + "~" + DateUtils.dateToString(edu.endDate);
        ((TextView) eduView.findViewById(R.id.education_school)).setText(edu.school+" "+edu.major+"("+dateString+")");
        ((TextView) eduView.findViewById(R.id.education_courses)).setText(formatItems(edu.courses));


        ImageButton editEduBtn = (ImageButton) eduView.findViewById(R.id.edit_education_btn);
        //emma--no need to manually change to type xxx, setOnClickListener can set on any view
        editEduBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MainActivity.this means where this activity started. notice that can not use only "this", this means this anoyloumous class
                Intent intent = new Intent(MainActivity.this,EditEducationActivity.class);
                //key--value pair
                intent.putExtra(EditEducationActivity.KEY_EDUCATION, edu);
                startActivityForResult(intent, REQ_CODE_EDIT_EDUCATION);
            }
        });
    }

    private void deleteEducation(@NonNull String educationId) {
        for(int i=0; i<edus.size(); i++) {
            Education e=edus.get(i);
            if(TextUtils.equals(e.id, educationId)) {
                edus.remove(i);
                break;
            }
        }

        ModelUtils.save(this, MODEL_EDUCATIONS, edus);
        setupEducations();
    }

    private void updateEducation(Education edu) {
        boolean found = false;
        for(int i=0; i<edus.size(); i++) {
            Education e = edus.get(i);
            if(TextUtils.equals(e.id, edu.id)) {
                found = true;
                edus.set(i, edu);
                break;
            }
        }

        if(!found) {
            edus.add(edu);
        }

        ModelUtils.save(this, MODEL_EDUCATIONS, edus);
        setupEducations();
    }


    private void loadData() {
        BasicInfo savedBasicInfo = ModelUtils.read(this,
                MODEL_BASIC_INFO,
                new TypeToken<BasicInfo>(){});

        BasicInfo InitBasicInfo = new BasicInfo();
        InitBasicInfo.name = "Emma";
        InitBasicInfo.email = "emmaz@mjsearch.me";
        basicInfo = savedBasicInfo == null ? InitBasicInfo : savedBasicInfo;

        List<Education> savedEducation = ModelUtils.read(this,
                MODEL_EDUCATIONS,
                new TypeToken<List<Education>>(){});
        edus = savedEducation == null ? new ArrayList<Education>() : savedEducation;

        List<Experience> savedExperience = ModelUtils.read(this,
                MODEL_EXPERIENCES,
                new TypeToken<List<Experience>>(){});
        exps = savedExperience == null ? new ArrayList<Experience>() : savedExperience;

        List<Project> savedProjects = ModelUtils.read(this,
                MODEL_PROJECTS,
                new TypeToken<List<Project>>(){});
        projs = savedProjects == null ? new ArrayList<Project>() : savedProjects;

    }

    public static String formatItems(List<String> items) {
        StringBuilder sb = new StringBuilder();
        for (String item: items) {
            sb.append(' ').append('-').append(' ').append(item).append('\n');
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

}

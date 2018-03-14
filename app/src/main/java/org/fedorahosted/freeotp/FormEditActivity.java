package org.fedorahosted.freeotp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.fedorahosted.freeotp.edit.BaseActivity;

public class FormEditActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_edit);

        toolbar = (Toolbar) findViewById(R.id.form_edit_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cargar manualmente");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

}

package org.fedorahosted.freeotp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import org.fedorahosted.freeotp.edit.BaseActivity;

public class FormEditActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button cargar_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_edit);

        toolbar = (Toolbar) findViewById(R.id.form_edit_toolbar);
        cargar_btn=(Button) findViewById(R.id.button_cargar);

        cargar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FormEditActivity.this,Main2Activity.class);
                startActivity(intent);

            }
        });


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cargar manualmente");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

}

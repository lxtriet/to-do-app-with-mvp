package com.hcmute.trietthao.yourtime.mvp.addANote.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcmute.trietthao.yourtime.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lxtri on 24/10/2017.
 */

public class AddANoteActivity extends AppCompatActivity implements View.OnClickListener{

    @Bind(R.id.iv_img_back)
    ImageView ivBack;

    @Bind(R.id.img_tick)
    ImageView ivTick;

    @Bind(R.id.tv_name_work)
    TextView tvNameWork;

    @Bind(R.id.etxt_note)
    TextView etNote;

    String EXTRA_WORK_ID = "";
    String EXTRA_WORK_NAME = "";
    String EXTRA_GROUPWORK_ID = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_a_note);
        ButterKnife.bind(this);

        EXTRA_WORK_ID = getIntent().getStringExtra("EXTRA_WORK_ID");
        EXTRA_GROUPWORK_ID = getIntent().getStringExtra("EXTRA_GROUPWORK_ID");
        EXTRA_WORK_NAME = getIntent().getStringExtra("EXTRA_WORK_NAME");
        if(getIntent().getStringExtra("EXTRA_NOTE")!=null)
            etNote.setText(getIntent().getStringExtra("EXTRA_NOTE"));
        tvNameWork.setText(EXTRA_WORK_NAME);

        ivBack.setOnClickListener(this);
        ivTick.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();

        switch (i){
            case R.id.iv_img_back:
                Intent data = new Intent();
                data.putExtra("EXTRA_WORK_ID",EXTRA_WORK_ID);
                data.putExtra("EXTRA_GROUPWORK_ID",EXTRA_GROUPWORK_ID);
                setResult(RESULT_OK,data);
                finish();
                break;

            case R.id.img_tick:
                Log.e("","------"+etNote.getText().toString());
                Intent data2 = new Intent();
                data2.putExtra("EXTRA_WORK_ID",EXTRA_WORK_ID);
                data2.putExtra("EXTRA_GROUPWORK_ID",EXTRA_GROUPWORK_ID);
                data2.putExtra("EXTRA_NOTE",etNote.getText().toString());
                setResult(RESULT_OK,data2);
                finish();
                break;
        }
    }
}

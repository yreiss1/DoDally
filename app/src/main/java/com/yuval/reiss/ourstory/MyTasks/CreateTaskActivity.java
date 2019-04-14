package com.yuval.reiss.ourstory.MyTasks;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yuval.reiss.ourstory.Objects.TaskObject;
import com.yuval.reiss.ourstory.R;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CreateTaskActivity extends AppCompatActivity {

    private ImageView mCreateImageView;
    private TextView mCreateNameTextView;
    private TextView mCreateDescriptionTextView;
    private EditText mCreateAmountTextView;
    private Button mCreateTaskButton;

    private String charityName;
    private String charityDescription;
    private String charityImage;
    private EditText mCreateTitleTextView;
    private EditText mCreateTimeEditText;
    private DatePickerDialog mCreateDatePickerDialog;
    private long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        Intent intent = getIntent();

        mCreateImageView = findViewById(R.id.create_task_imageview);
        mCreateNameTextView = findViewById(R.id.create_task_name_textview);
        mCreateDescriptionTextView = findViewById(R.id.create_task_description);
        mCreateAmountTextView = findViewById(R.id.create_task_amount_edittext);
        mCreateTaskButton = findViewById(R.id.create_task_button);
        mCreateTitleTextView = findViewById(R.id.create_task_title_edittext);
        mCreateTimeEditText = findViewById(R.id.create_time_edittext);

        charityName = intent.getStringExtra("name");
        charityDescription = intent.getStringExtra("description");
        charityImage = intent.getStringExtra("image");

        mCreateNameTextView.setText(charityName);
        mCreateDescriptionTextView.setText(charityDescription);
        Glide.with(getApplicationContext()).load(charityImage).into(mCreateImageView);

        mCreateTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                mCreateDatePickerDialog = new DatePickerDialog(CreateTaskActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                mCreateTimeEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                                Date date = null;
                                try {
                                    date = dateFormat.parse(mCreateTimeEditText.getText().toString());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                Timestamp timestamp = new Timestamp(date.getTime());

                                time = timestamp.getTime();

                            }
                        }, year, month, day);
                mCreateDatePickerDialog.show();
            }
        });
        mCreateAmountTextView.addTextChangedListener(new MoneyTextWatcher(mCreateAmountTextView));


        mCreateTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                float amount = 0;
                if (!mCreateAmountTextView.getText().toString().isEmpty()) {
                    amount = Float.parseFloat(mCreateAmountTextView.getText().toString().substring(1));
                }



                String title = mCreateTitleTextView.getText().toString();

                if (time == 0) {
                    Toast.makeText(CreateTaskActivity.this, "Please set deadline of task!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mCreateTitleTextView.getText().toString().isEmpty()) {
                    Toast.makeText(CreateTaskActivity.this, "Please give your task a name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (amount == 0) {
                    Toast.makeText(CreateTaskActivity.this, "Please add a donation amount!", Toast.LENGTH_SHORT).show();
                    return;

                }

                TaskObject task = new TaskObject(amount,title, charityName, charityImage, charityDescription, time);
                DatabaseReference userDB = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("tasks");
                final String key = userDB.push().getKey();

                Map<String, Object> map = new HashMap();
                map.put("cause",charityName);
                map.put("title", title);
                map.put("amount", amount);
                map.put("description", charityDescription);
                map.put("image", charityImage);
                map.put("time",time);

                userDB.child(key).setValue(map);

                finish();

            }
        });

    }

    public class MoneyTextWatcher implements TextWatcher {
        private final WeakReference<EditText> editTextWeakReference;

        public MoneyTextWatcher(EditText editText) {
            editTextWeakReference = new WeakReference<EditText>(editText);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            EditText editText = editTextWeakReference.get();
            if (editText == null) return;
            String s = editable.toString();
            if (s.isEmpty()) return;
            editText.removeTextChangedListener(this);
            String cleanString = s.replaceAll("[$,.]", "");
            BigDecimal parsed = new BigDecimal(cleanString).setScale(2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100), BigDecimal.ROUND_FLOOR);
            String formatted = NumberFormat.getCurrencyInstance().format(parsed);
            editText.setText(formatted);
            editText.setSelection(formatted.length());
            editText.addTextChangedListener(this);
        }
    }







}

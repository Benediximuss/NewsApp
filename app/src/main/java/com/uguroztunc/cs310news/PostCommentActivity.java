package com.uguroztunc.cs310news;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class PostCommentActivity extends AppCompatActivity {

    int currentNewsId;

    EditText editTxtName;
    EditText editTxtComment;
    Button btnPost;
    TextView txtView_warning;
    ProgressBar progressBar_Posting;

    Handler postCommentHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {

            int retVal = (int) message.obj;
            
            if (retVal == 1) {
                Toast.makeText(PostCommentActivity.this, "Comment posted successfully!", Toast.LENGTH_SHORT).show();
                finish();
            }
            else {
                progressBar_Posting.setVisibility(View.INVISIBLE);
                editTxtName.setEnabled(true);
                editTxtComment.setEnabled(true);
                btnPost.setEnabled(true);
            }

            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_comment);

        editTxtName = findViewById(R.id.editText_commentName);
        editTxtComment = findViewById(R.id.editText_commentText);
        btnPost = findViewById(R.id.btnPost);
        txtView_warning = findViewById(R.id.txtView_warning);
        progressBar_Posting = findViewById(R.id.progressBar_Posting);

        currentNewsId = (int) getIntent().getSerializableExtra("selectedNewsId");

        getSupportActionBar().setTitle("Post comment");

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = editTxtName.getText().toString();
                String comment = editTxtComment.getText().toString();

                if (username.isEmpty() || comment.isEmpty()) {
                    txtView_warning.setVisibility(View.VISIBLE);
                }
                else {
                    NewsRepository repo = new NewsRepository();
                    progressBar_Posting.setVisibility(View.VISIBLE);
                    editTxtName.setEnabled(false);
                    editTxtComment.setEnabled(false);
                    btnPost.setEnabled(false);
                    repo.postComment(((NewsApplication) getApplication()).srv, postCommentHandler, currentNewsId, username, comment);
                }
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        super.onOptionsItemSelected(item);

        if (item.getItemId() == android.R.id.home)
        {
            finish();
        }

        return true;
    }
}
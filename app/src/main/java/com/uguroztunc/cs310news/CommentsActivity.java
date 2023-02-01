package com.uguroztunc.cs310news;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class CommentsActivity extends AppCompatActivity {

    int currentNewsId;

    RecyclerView recView_CommentsList;

    TextView txt_LoadComments;
    ProgressBar progressBar_LoadComments;

    Handler commentHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            List<CommentItem> data = (List<CommentItem>)message.obj;
            RecViewAdapterComments adp = new RecViewAdapterComments(CommentsActivity.this, data);
            recView_CommentsList.setAdapter(adp);

            txt_LoadComments.setVisibility(View.INVISIBLE);
            progressBar_LoadComments.setVisibility(View.INVISIBLE);

            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        currentNewsId = (int) getIntent().getSerializableExtra("selectedNewsId");

        recView_CommentsList = findViewById(R.id.recView_CommentsList);
        recView_CommentsList.setLayoutManager(new LinearLayoutManager(this));

        txt_LoadComments = findViewById(R.id.textView_LoadComments);
        progressBar_LoadComments = findViewById(R.id.progressBar_LoadComments);

        getSupportActionBar().setTitle("Comments");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        NewsRepository repo = new NewsRepository();
        repo.getCommentById(((NewsApplication) getApplication()).srv, commentHandler, currentNewsId);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.comments_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.mnPostComment)
        {
            Intent i = new Intent(CommentsActivity.this, PostCommentActivity.class);
            i.putExtra("selectedNewsId", currentNewsId);
            startActivity(i);
        }
        else if (item.getItemId() == android.R.id.home)
        {
            finish();
        }

        return true;
    }

}
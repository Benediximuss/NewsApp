package com.uguroztunc.cs310news;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    int currentNewsId;

    ImageView imgView_detailImg;
    TextView txtView_detailTitle;
    TextView txtView_detailDate;
    TextView txtView_detailText;

    ProgressBar progressBar_LoadDetailImg;

    Handler newsHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {

            List<NewsItem> objData = (List<NewsItem>) message.obj;

            if (objData.size() == 1)
            {
                NewsRepository repo = new NewsRepository();
                repo.downloadImage(((NewsApplication) getApplication()).srv, imgHandler, objData.get(0).getNewsImgPath());

                txtView_detailTitle.setText(objData.get(0).getNewsTitle());
                txtView_detailDate.setText(objData.get(0).getNewsDate());
                txtView_detailText.setText(objData.get(0).getNewsText());

                txtView_detailTitle.setVisibility(View.VISIBLE);
                txtView_detailDate.setVisibility(View.VISIBLE);
                txtView_detailText.setVisibility(View.VISIBLE);
            }
            else
            {
                txtView_detailTitle.setText("Error: There are multiple news matching with news ID " + String.valueOf(currentNewsId));
                txtView_detailDate.setVisibility(View.INVISIBLE);
                txtView_detailText.setVisibility(View.INVISIBLE);
            }

            return true;
        }
    });

    Handler imgHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            Bitmap img = (Bitmap) message.obj;
            imgView_detailImg.setImageBitmap(img);
            progressBar_LoadDetailImg.setVisibility(View.INVISIBLE);
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        imgView_detailImg = findViewById(R.id.imgView_detailImg);
        txtView_detailTitle = findViewById(R.id.txtView_detailTitle);
        txtView_detailDate = findViewById(R.id.txtView_detailDate);
        txtView_detailText = findViewById(R.id.txtView_detailText);
        progressBar_LoadDetailImg = findViewById(R.id.progressBar_LoadDetailImg);

        currentNewsId = (int) getIntent().getSerializableExtra("selectedNewsId");

        getSupportActionBar().setTitle((String) getIntent().getSerializableExtra("newsCategory"));

        txtView_detailTitle.setVisibility(View.INVISIBLE);
        txtView_detailDate.setVisibility(View.INVISIBLE);
        txtView_detailText.setVisibility(View.INVISIBLE);

        NewsRepository repo = new NewsRepository();
        repo.getNewsById(((NewsApplication) getApplication()).srv, newsHandler, currentNewsId);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.details_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.mnShowComments)
        {
            Intent i = new Intent(DetailsActivity.this, CommentsActivity.class);
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
package com.uguroztunc.cs310news;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class RecViewAdapterNews extends RecyclerView.Adapter<RecViewAdapterNews.NewsItemViewHolder> {

    Context context;
    List<NewsItem> data;

    public RecViewAdapterNews(Context context, List<NewsItem> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public NewsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View root = LayoutInflater.from(context).inflate(R.layout.row_layout_category,parent,false);

        NewsItemViewHolder holder = new NewsItemViewHolder(root);
        holder.setIsRecyclable(false);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsItemViewHolder holder, int position) {
        holder.txtView_rowDate.setText(data.get(position).getNewsDate());
        holder.txtView_rowTitle.setText(data.get(position).getNewsTitle());

        NewsApplication app = (NewsApplication) ((AppCompatActivity)context).getApplication();

        holder.downloadImage(app.srv, data.get(holder.getAdapterPosition()).getNewsImgPath());

        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailsActivity.class);
                i.putExtra("selectedNewsId", data.get(position).getNewsID());
                i.putExtra("newsCategory", data.get(position).getNewsCategoryName());
                ((AppCompatActivity)context).startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (data == null)
        {
            return 0;
        }
        else
        {
            return data.size();
        }
    }

    class NewsItemViewHolder extends RecyclerView.ViewHolder {

        ImageView imgView_rowImg;
        TextView txtView_rowDate;
        TextView txtView_rowTitle;
        ConstraintLayout row;

        ProgressBar progressBar_LoadRowImg;

        boolean imageDownloaded;

        Handler imgHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                Bitmap img = (Bitmap)msg.obj;
                imgView_rowImg.setImageBitmap(img);
                imageDownloaded = true;
                progressBar_LoadRowImg.setVisibility(View.INVISIBLE);
                return true;
            }
        });

        public NewsItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imgView_rowImg = itemView.findViewById(R.id.imgView_rowImg);
            txtView_rowDate = itemView.findViewById(R.id.txtView_rowDate);
            txtView_rowTitle = itemView.findViewById(R.id.txtView_rowTitle);
            progressBar_LoadRowImg = itemView.findViewById(R.id.progressBar_LoadRowImg);
            row = itemView.findViewById(R.id.catRow);
        }

        public void downloadImage(ExecutorService srv, String path){

            if (!imageDownloaded){
                NewsRepository repo = new NewsRepository();
                repo.downloadImage(srv,imgHandler,path);
            }

        }
    }
}

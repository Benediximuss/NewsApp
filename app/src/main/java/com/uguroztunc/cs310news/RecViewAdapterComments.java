package com.uguroztunc.cs310news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecViewAdapterComments extends RecyclerView.Adapter<RecViewAdapterComments.CommentItemViewHolder> {

    Context context;
    List<CommentItem> data;

    public RecViewAdapterComments(Context context, List<CommentItem> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public CommentItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommentItemViewHolder(LayoutInflater.from(context).inflate(R.layout.row_layout_comment,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentItemViewHolder holder, int position) {

        holder.txtView_commentUsername.setText(data.get(position).getUsername());
        holder.txtView_commentText.setText(data.get(position).getCommentText());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class CommentItemViewHolder extends RecyclerView.ViewHolder {

        TextView txtView_commentUsername;
        TextView txtView_commentText;
        ConstraintLayout row;

        public CommentItemViewHolder(@NonNull View itemView) {
            super(itemView);
            txtView_commentUsername = itemView.findViewById(R.id.txtView_commentUsername);
            txtView_commentText = itemView.findViewById(R.id.txtView_commentText);
            row = itemView.findViewById(R.id.commentRow);

        }
    }

}

package com.uguroztunc.cs310news;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class CategoryFragment extends Fragment {

    private static final String CAT_ID = "categoryID";

    private int categoryID;
    private String categoryName;

    RecyclerView recNews;
    List<NewsItem> data;

    View v;

    // fetch category name
    Handler categoryHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {

            List<String> objData = (List<String>) message.obj;

            categoryName = objData.get(categoryID);

            return true;
        }
    });

    // fetch news of the current category
    Handler newsHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {

            data = (List<NewsItem>) message.obj;

            recNews = v.findViewById(R.id.recView_NewsList);
            recNews.setLayoutManager(new LinearLayoutManager(getContext()));

            RecViewAdapterNews adp = new RecViewAdapterNews(getContext(), data);

            recNews.setAdapter(adp);

            return true;
        }
    });

    public CategoryFragment() {
        // Required empty public constructor
    }

    public static CategoryFragment newInstance(int param1) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putInt(CAT_ID, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryID = getArguments().getInt(CAT_ID);

            NewsRepository repo = new NewsRepository();

            repo.getAllCategories(((NewsApplication) getActivity().getApplication()).srv, categoryHandler);

            repo.getNewsByCatId(((NewsApplication) getActivity().getApplication()).srv, newsHandler, categoryID+1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_category, container, false);

        return v;
    }

}
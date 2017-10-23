package com.berry.blue.reds_teach.results;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.berry.blue.reds_teach.R;
import com.berry.blue.reds_teach.fires.Game;
import com.berry.blue.reds_teach.interfaces.activities.Main;

import java.util.List;

public class ResultsFragment extends Fragment implements Results.Fragment, Results.AdapterView{
    private RecyclerView recyclerView;
    private Main view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recycler_view_frag, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        this.recyclerView = rootView.findViewById(R.id.recyclerView);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation()));

        ResultsControl.instance().getResultsGames(this);

        return rootView;
    }

    public ResultsFragment setView(Main view) {
        this.view = view;
        return this;
    }

    @Override
    public void onDataObtained(List<Game> games) {
        recyclerView.setAdapter(new ResultsAdapter(games, this));
    }

    @Override
    public void showMessage(String errorMessage) {
        this.view.showMessage(errorMessage);
    }

    @Override
    public void showDetail(String key) {
        Log.e(getClass().getSimpleName(), key);
    }

    @Override
    public void getDetail(String key) {
        ResultsControl.instance();
    }
}

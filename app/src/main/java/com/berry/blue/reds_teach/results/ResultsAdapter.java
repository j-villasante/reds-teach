package com.berry.blue.reds_teach.results;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.berry.blue.reds_teach.R;
import com.berry.blue.reds_teach.fires.Game;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ViewHolder>{
    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvi_date_list_item) TextView tviDate;
        @BindView(R.id.tvi_type_list_item) TextView tviType;
        View view;
        ViewHolder(View view){
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }
    }
    private List<Game> games;
    private Results.AdapterView fragment;

    ResultsAdapter(List<Game> games, Results.AdapterView fragment) {
        this.games = games;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.game_list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Game game = games.get(position);
        holder.tviDate.setText(game.getSimpleDate());
        holder.tviType.setText(String.format("Tipo:%s", String.valueOf(game.type)));

        holder.view.setOnClickListener(view -> fragment.getDetail(game.key));
    }

    @Override
    public int getItemCount() {
        return games.size();
    }
}

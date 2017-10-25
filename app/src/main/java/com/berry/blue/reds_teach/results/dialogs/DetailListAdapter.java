package com.berry.blue.reds_teach.results.dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.berry.blue.reds_teach.R;
import com.berry.blue.reds_teach.fires.Guess;

import java.util.List;

public class DetailListAdapter extends BaseAdapter {
    private List<Guess> guesses;
    private Context context;

    DetailListAdapter(List<Guess> guesses, Context context) {
        this.guesses = guesses;
        this.context = context;
    }

    @Override
    public int getCount() {
        return guesses.size();
    }

    @Override
    public Guess getItem(int i) {
        return guesses.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup container) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.game_detail_list_item, container, false);
        }

        ((TextView) view.findViewById(R.id.game_detail_word)).setText(getItem(i).word);
        ((TextView) view.findViewById(R.id.game_detail_times)).setText(getItem(i).stringifyTries());
        return view;
    }
}

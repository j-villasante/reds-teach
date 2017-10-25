package com.berry.blue.reds_teach.results.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.berry.blue.reds_teach.R;
import com.berry.blue.reds_teach.fires.Game;
import com.berry.blue.reds_teach.fires.Guess;

import java.util.List;

public class DetailDialog extends DialogFragment {
    private Game game;
    private List<Guess> guesses;

    public DetailDialog setDetails(Game game, List<Guess> guesses) {
        this.game = game;
        this.guesses = guesses;
        return this;
    }

    private View createDialogView() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.game_detail_dialog, null);
        ListView listView = view.findViewById(R.id.game_detail_lvi);
        listView.setAdapter(new DetailListAdapter(guesses, getContext()));
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(this.createDialogView())
                .setTitle(game.getSimpleDate())
                .setPositiveButton("OK", null);
        return builder.create();
    }
}

package com.berry.blue.reds_teach.words;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.berry.blue.reds_teach.R;
import com.berry.blue.reds_teach.fires.Word;
import com.berry.blue.reds_teach.interfaces.activities.Main;
import com.berry.blue.reds_teach.nfcUtils.TagControl;
import com.berry.blue.reds_teach.words.dialog.ModifyCategoryDialog;
import com.berry.blue.reds_teach.words.dialog.NfcDialog;
import com.berry.blue.reds_teach.words.dialog.WordsDialog;

import java.util.List;

public class WordsFragment extends Fragment implements WordsI.Fragment{
    private Main view;
    private RecyclerView recyclerView;

    private List<String> categories;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recycler_view_frag, container, false);
        this.recyclerView = rootView.findViewById(R.id.recyclerView);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        WordsControl wordsControl = WordsControl.instance().setView(this);
        wordsControl.getWords();
        wordsControl.getCategories();
        return rootView;
    }

    public WordsFragment setView(Main view) {
        this.view = view;
        return this;
    }

    @Override
    public void onDataObtained(List<Word> words) {
        recyclerView.setAdapter(new WordAdapter(words, this));
    }

    @Override
    public void onCategoryData(List<String> categories) {
        this.categories = categories;
    }

    @Override
    public void onEditItemClick(Word word) {
        view.showDialog(WordsDialog.newInstance(WordsDialog.TYPE.MODIFY_WORD, word));
    }

    @Override
    public void onDeleteItemClick(Word word) {
        view.showDialog(WordsDialog.newInstance(WordsDialog.TYPE.DELETE_WORD, word));
    }

    @Override
    public void onNfcItemClick(String key) {
        TagControl.instance().setKey(key);
        view.showDialog(new NfcDialog().setParent(view));
    }

    @Override
    public void onChangeCategoryClick(String key) {
        if (this.categories != null) {
            ModifyCategoryDialog dialog = new ModifyCategoryDialog();
            dialog.initialize(this.categories, key);
            view.showDialog(dialog);
        }
    }

    @Override
    public void showMessage(String errorMessage) {
        this.view.showMessage(errorMessage);
    }
}

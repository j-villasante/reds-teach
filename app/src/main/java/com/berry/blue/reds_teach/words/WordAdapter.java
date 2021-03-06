package com.berry.blue.reds_teach.words;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.berry.blue.reds_teach.R;
import com.berry.blue.reds_teach.fires.Word;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder>{
    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.info_text) TextView tviName;
        @BindView(R.id.words_category) TextView tviCategory;
        @BindView(R.id.words_nfc_but) ImageView nfcBut;
        @BindView(R.id.words_edit_but) ImageView editBut;
        @BindView(R.id.words_delete_but) ImageView deleteBut;
        ViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    private List<Word> words;
    private WordsI.Fragment fragment;

    WordAdapter(List<Word> words, WordsI.Fragment fragment) {
        this.fragment = fragment;
        this.words = words;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.word_list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Word word = words.get(position);
        holder.tviName.setText(word.name);
        if (word.category == null)
            holder.tviCategory.setText("---");
        else
            holder.tviCategory.setText(word.category);
        holder.editBut.setOnClickListener(view -> fragment.onEditItemClick(word));
        holder.deleteBut.setOnClickListener(view -> fragment.onDeleteItemClick(word));
        holder.nfcBut.setOnClickListener(view -> fragment.onNfcItemClick(word.key));
        holder.tviCategory.setOnClickListener(view -> fragment.onChangeCategoryClick(word.key));
    }

    @Override
    public int getItemCount() {
        return words.size();
    }
}

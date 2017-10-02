package com.berry.blue.reds_teach.words;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.berry.blue.reds_teach.R;
import com.berry.blue.reds_teach.fires.Word;

import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder>{
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tviName;
        ViewHolder(View view){
            super(view);
            this.tviName = view.findViewById(R.id.info_text);
        }
    }
    private List<Word> words;

    WordAdapter(List<Word> words) {
        this.words = words;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tviName.setText(words.get(position).name);
    }

    @Override
    public int getItemCount() {
        return words.size();
    }
}

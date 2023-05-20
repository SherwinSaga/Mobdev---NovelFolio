package com.example.novelfolio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder> {
    Context context;
    List<Chapter> chapters;
    ChapterClickInterface chapterClickInterface;
    String novelDocId;

    public ChapterAdapter(Context context, ArrayList<Chapter> chapters, String novelDocId, ChapterClickInterface chapterClickInterface) {
        this.context = context;
        this.chapters = chapters;
        this.chapterClickInterface = chapterClickInterface;
        this.novelDocId = novelDocId;
    }

    @NonNull
    @Override
    public ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.chapter_card, parent, false);
        return new ChapterAdapter.ChapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterViewHolder holder, int position) {
        holder.name.setText("Chapter " + Integer.toString(chapters.get(position).getChapterNumber()) + " " + chapters.get(position).getName());
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chapterClickInterface.onChapterClick(novelDocId, chapters.get(position).getChapterNumber());
            }
        });
    }

    @Override
    public int getItemCount() {
        return chapters.size();
    }

    public static class ChapterViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        public ChapterViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.chapterCardName);

        }
    }

    public interface ChapterClickInterface {
        public void onChapterClick(String NovelDocId, int chapterNum);
    }

}

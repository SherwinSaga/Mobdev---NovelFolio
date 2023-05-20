package com.example.novelfolio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class NovelCardAdapter extends RecyclerView.Adapter<NovelCardAdapter.NovelCardViewHolder> {
    Context context;
    List<Novel> novels;
    NovelCardClickInterface novelCardClickInterface;

    public NovelCardAdapter(Context context, ArrayList<Novel> novels, NovelCardClickInterface novelCardClickInterface) {
        this.context = context;
        this.novels = novels;
        this.novelCardClickInterface = novelCardClickInterface;
    }

    @NonNull
    @Override
    public NovelCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.novel_card, parent, false);
        return new NovelCardAdapter.NovelCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NovelCardViewHolder holder, int position) {
        Novel novel = novels.get(position);
        Glide.with(holder.itemView.getContext()).load(novel.getImgUrl()).into(holder.coverImg);
        holder.title.setText(novel.getTitle());

        holder.novelCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                novelCardClickInterface.onNovelClick(novel.getDocId(), novel.getCurrentChapterNumber());
            }
        });
    }

    @Override
    public int getItemCount() {
        return novels.size();
    }

    public static class NovelCardViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView coverImg;
        CardView novelCard;
        public NovelCardViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.novelCardTitle);
            coverImg = itemView.findViewById(R.id.novelCardCoverImg);
            novelCard = itemView.findViewById(R.id.novelCard);
        }
    }

    public interface NovelCardClickInterface {
        public void onNovelClick(String novelDocId, int chapterNum);
    }

}

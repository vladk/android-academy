package ru.kononov.vlad.exercise2;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import ru.kononov.vlad.exercise2.data.NewsItem;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class ActorRecyclerAdapter extends RecyclerView.Adapter<ActorRecyclerAdapter.ViewHolder> {
    @NonNull
    private final List<NewsItem> actors;
    @NonNull
    private final LayoutInflater inflater;
    @Nullable
    private final OnItemClickListener clickListener;
    @NonNull
    private final RequestManager imageLoader;

    public ActorRecyclerAdapter(@NonNull Context context, @NonNull List<NewsItem> actors,
                                @Nullable OnItemClickListener clickListener) {
        this.actors = actors;
        this.inflater = LayoutInflater.from(context);
        this.clickListener = clickListener;

        RequestOptions imageOption = new RequestOptions()
                .centerCrop();
        this.imageLoader = Glide.with(context).applyDefaultRequestOptions(imageOption);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.news_item, parent, false), clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(actors.get(position));
    }

    @Override
    public int getItemCount() {
        return actors.size();
    }

    public interface OnItemClickListener {
        void onItemClick(NewsItem actor);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView nameView;
        private final TextView catetoryView;
        private final TextView previewTextView;
        private final TextView publishDateView;

        ViewHolder(@NonNull View itemView, @Nullable final OnItemClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(actors.get(position));
                    }
                }
            });

            imageView = itemView.findViewById(R.id.news_image);
            nameView = itemView.findViewById(R.id.news_title);
            catetoryView = itemView.findViewById(R.id.news_category);
            previewTextView = itemView.findViewById(R.id.news_preview_text);
            publishDateView = itemView.findViewById(R.id.news_publish_date);
        }

        void bind(NewsItem newsItem) {
            imageLoader.load(newsItem.getImageUrl()).into(imageView);
            nameView.setText(newsItem.getTitle());
            catetoryView.setText(newsItem.getCategory().getName());
            previewTextView.setText(newsItem.getPreviewText());

            String formattedDate = DateUtils.getRelativeDateTimeString(publishDateView.getContext(),
                    newsItem.getPublishDate().getTime(), DateUtils.MINUTE_IN_MILLIS,
                    DateUtils.DAY_IN_MILLIS, DateUtils.FORMAT_SHOW_TIME).toString();

            publishDateView.setText(formattedDate);
        }
    }
}

package io.elromantico.reader.feed;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.elromantico.reader.R;

public class FeedItemsAdapter extends RecyclerView.Adapter<FeedItemsAdapter.MyViewHolder> {

    private List<FeedItem> feedItems;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            year = (TextView) view.findViewById(R.id.year);
        }
    }

    public FeedItemsAdapter(List<FeedItem> feedItems) {
        this.feedItems = feedItems;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        FeedItem feedItem = feedItems.get(position);
        holder.title.setText(feedItem.getTitle());
        holder.genre.setText("asd");
        holder.year.setText("32");
    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }
}

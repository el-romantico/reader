package io.elromantico.reader.feed;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.elromantico.reader.R;

public class ParsedFeedItemsAdapter extends RecyclerView.Adapter<ParsedFeedItemsAdapter.MyViewHolder> {

    private List<String> feedTitles;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            year = (TextView) view.findViewById(R.id.year);
        }
    }

    public ParsedFeedItemsAdapter(List<String> feedTitles) {
        this.feedTitles = feedTitles;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.title.setText(feedTitles.get(position));
        holder.genre.setText("asd");
        holder.year.setText("32");
    }

    @Override
    public int getItemCount() {
        return feedTitles.size();
    }
}

package ru.kononov.vlad.exercise2;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.kononov.vlad.exercise2.data.DataUtils;
import ru.kononov.vlad.exercise2.data.NewsItem;

public class NewsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity_recycler);

        final Activity activity = this;

        RecyclerView list = findViewById(R.id.recycler);
        list.setAdapter(new ActorRecyclerAdapter(this, DataUtils.generateNews(), new ActorRecyclerAdapter.OnItemClickListener() {
            public void onItemClick(NewsItem item) {
                NewsDetailsActivity.start(activity, item.getId());
            }
        }));
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            list.setLayoutManager(new LinearLayoutManager(this));
        } else {
            list.setLayoutManager(new GridLayoutManager(this, 2));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

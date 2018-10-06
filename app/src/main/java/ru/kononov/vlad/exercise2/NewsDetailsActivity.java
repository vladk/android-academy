package ru.kononov.vlad.exercise2;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.kononov.vlad.exercise2.data.DataUtils;
import ru.kononov.vlad.exercise2.data.NewsItem;

public class NewsDetailsActivity extends AppCompatActivity {
    private static final String KEY_NEWS_ID = "KEY_NEWS_ID";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_item_details);

        final int id = getIntent().getIntExtra(KEY_NEWS_ID, -1);

        NewsItem item = DataUtils.getNewsItem(id);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle(item.getCategory().getName());
        }

        setTextValue(R.id.news_title, item.getTitle());
        setTextValue(R.id.news_full_text, item.getFullText());

        String formattedDate = DateUtils.getRelativeDateTimeString(getBaseContext(),
                item.getPublishDate().getTime(), DateUtils.MINUTE_IN_MILLIS,
                DateUtils.DAY_IN_MILLIS, DateUtils.FORMAT_SHOW_TIME).toString();

        setTextValue(R.id.news_publish_date, formattedDate);

        ImageView imageView = findViewById(R.id.news_image);
        Glide.with(this).load(item.getImageUrl()).into(imageView);
    }

    void setTextValue(int id, String value) {
        TextView view = findViewById(id);
        view.setText(value);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public static void start(Activity activity, int id) {
        Intent intent = new Intent(activity, NewsDetailsActivity.class);
        intent.putExtra(KEY_NEWS_ID, id);
        activity.startActivity(intent);
    }
}

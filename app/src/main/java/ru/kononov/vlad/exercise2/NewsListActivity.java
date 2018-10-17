package ru.kononov.vlad.exercise2;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.kononov.vlad.exercise2.data.DataUtils;
import ru.kononov.vlad.exercise2.data.NewsItem;

public class NewsListActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity_recycler);

        final Activity activity = this;

        disposables.add(
                DataUtils.generateNews()
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(__ -> setState(PAGE_LOADING))
                        .subscribe(news -> {
                            setState(news.size() > 0 ? PAGE_LOADED : PAGE_NODATA);
                            showNews(activity, news);
                        }, __ -> setState(PAGE_ERROR))
        );
    }

    void showNews(Activity activity, List<NewsItem> news){
        RecyclerView list = findViewById(R.id.recycler);
        list.setAdapter(new ActorRecyclerAdapter(this, news, item -> NewsDetailsActivity.start(activity, item.getId())));
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

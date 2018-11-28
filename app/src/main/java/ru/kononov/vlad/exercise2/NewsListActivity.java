package ru.kononov.vlad.exercise2;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.kononov.vlad.exercise2.data.NewsItem;
import ru.kononov.vlad.exercise2.data.NewsItemConverter;
import ru.kononov.vlad.exercise2.data.network.RestApi;
import ru.kononov.vlad.exercise2.data.network.dto.MultimediaDTO;

public class NewsListActivity extends BaseActivity {
    final String defaultCategory = "home";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity_recycler);
        loadNews(defaultCategory);

        findViewById(R.id.retry_btn).setOnClickListener(v -> loadNews(defaultCategory));


        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String category = (String)parent.getSelectedItem();
                loadNews(category.toLowerCase());
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    void loadNews(String category) {
        final Activity activity = this;
        disposables.add(
                RestApi.getInstance()
                        .news()
                        .topStories(category)
                        .flatMapIterable(list -> list.results)
                        .map(item -> NewsItemConverter.fromNetwork(item))
                        .toList()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(__ -> setState(PAGE_LOADING))
                        .subscribe(news -> {
                            setState(news.size() > 0 ? PAGE_LOADED : PAGE_NODATA);
                            showNews(activity, news);
                        }, __ -> setState(PAGE_ERROR))
        );
    }

    void showNews(Activity activity, List<NewsItem> news) {
        RecyclerView list = findViewById(R.id.recycler);
        list.setAdapter(new ActorRecyclerAdapter(this, news, item -> NewsDetailsActivity.start(activity, item.getUrl())));
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

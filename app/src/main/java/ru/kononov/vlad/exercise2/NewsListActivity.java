package ru.kononov.vlad.exercise2;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.kononov.vlad.exercise2.data.DataUtils;

public class NewsListActivity extends AppCompatActivity {
    private Disposable disposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity_recycler);

        final Activity activity = this;

        disposable = Observable.fromCallable(() -> DataUtils.generateNews())
                .delay(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(news -> {
                    ProgressBar progressBar = findViewById(R.id.progress_bar);
                    progressBar.setVisibility(View.GONE);
                    RecyclerView list = findViewById(R.id.recycler);
                    list.setAdapter(new ActorRecyclerAdapter(this, news, item -> NewsDetailsActivity.start(activity, item.getId())));
                    if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                        list.setLayoutManager(new LinearLayoutManager(this));
                    } else {
                        list.setLayoutManager(new GridLayoutManager(this, 2));
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

package ru.kononov.vlad.exercise2;

import android.view.View;
import android.widget.ProgressBar;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.IdRes;
import androidx.annotation.IntDef;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.disposables.Disposable;

public class BaseActivity extends AppCompatActivity {
    protected static final int PAGE_LOADING = 1;
    protected static final int PAGE_LOADED = 2;
    protected static final int PAGE_NODATA = 3;
    protected static final int PAGE_ERROR= 4;

    @IntDef({PAGE_LOADING, PAGE_LOADED, PAGE_NODATA, PAGE_ERROR})
    @Retention(RetentionPolicy.SOURCE)
    protected @interface PageStates{}

    protected List<Disposable> disposables = new ArrayList<>();

    protected void setState(@PageStates int stateId){
        setViewVisibility(R.id.common_progress_bar, stateId == PAGE_LOADING);
        setViewVisibility(R.id.common_error, stateId == PAGE_ERROR);
        setViewVisibility(R.id.common_nodata, stateId == PAGE_NODATA);
    }

    void setViewVisibility(@IdRes int viewId, boolean isVisible){
        findViewById(viewId).setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (Disposable disposable : disposables){
            disposable.dispose();
        }
    }
}

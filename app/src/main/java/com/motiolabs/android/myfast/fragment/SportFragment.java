package com.motiolabs.android.myfast.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.Loader;
import android.util.Log;

import com.motiolabs.android.myfast.News;
import com.motiolabs.android.myfast.NewsLoader;
import com.motiolabs.android.myfast.NewsPreferences;
import com.motiolabs.android.myfast.R;

import java.util.List;

/**
 * The SportFragment is a {@link BaseArticlesFragment} subclass that
 * reuses methods of the parent class by passing the specific type of article to be fetched.
 */
public class SportFragment extends BaseArticlesFragment {

    private static final String LOG_TAG = SportFragment.class.getName();

    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        String sportUrl = NewsPreferences.getPreferredUrl(getContext(), getString(R.string.sport));
        Log.e(LOG_TAG, sportUrl);

        // Create a new loader for the given URL
        return new NewsLoader(getActivity(), sportUrl);
    }
}

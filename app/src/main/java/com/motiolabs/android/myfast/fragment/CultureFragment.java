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
 * The CultureFragment is a {@link BaseArticlesFragment} subclass that
 * reuses methods of the parent class by passing the specific type of article to be fetched.
 */
public class CultureFragment extends BaseArticlesFragment {

    private static final String LOG_TAG = CultureFragment.class.getName();

    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        String cultureUrl = NewsPreferences.getPreferredUrl(getContext(), getString(R.string.culture));
        Log.e(LOG_TAG, cultureUrl);

        // Create a new loader for the given URL
        return new NewsLoader(getActivity(), cultureUrl);
    }
}

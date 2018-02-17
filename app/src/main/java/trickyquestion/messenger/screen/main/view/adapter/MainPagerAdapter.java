package trickyquestion.messenger.screen.main.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private List<Fragment> pages;
    private List<String> titles;

    public MainPagerAdapter(final FragmentManager fm, final Context context) {
        super(fm);
        mContext = context;
        this.pages = new ArrayList<>();
        this.titles = new ArrayList<>();
    }

    /** not use it constructor with addFragment() method **/
    public MainPagerAdapter(final FragmentManager fm, final List<Fragment> pages, final List<String> titles) {
        super(fm);
        this.pages = pages;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return pages.get(position);
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    public void addFragment(final Fragment fragment, final String title) {
        pages.add(fragment);
        titles.add(title);
    }

    public void addFragment(final Fragment fragment, final int titleResource) {
        pages.add(fragment);
        titles.add(mContext.getResources().getString(titleResource));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}

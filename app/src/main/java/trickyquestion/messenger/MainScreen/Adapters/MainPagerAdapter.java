package trickyquestion.messenger.MainScreen.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> pages;
    private List<String> titles;

    public MainPagerAdapter(final FragmentManager fm) {
        super(fm);
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

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}

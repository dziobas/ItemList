package pl.dziobas.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import pl.dziobas.R;
import pl.dziobas.fragment.ItemsFragment;
import pl.dziobas.widget.ButtonBar;
import pl.dziobas.widget.ButtonBar.OnButtonClicked;


/**
 * Main activity
 * @author dziobas
 *
 */
public class MainActivity extends BaseActivity {
    private ViewPager mViewPager;
    private ButtonBar mButtonBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mButtonBar = (ButtonBar) findViewById(R.id.buttons);

        TabPager tabPager = new TabPager(this, mViewPager);
        mViewPager.setOnPageChangeListener(mButtonBar);
        mButtonBar.setOnButtonClicked(new OnButtonClicked() {
                @Override
                public void onButtonClicked(int position) {
                    mViewPager.setCurrentItem(position);
                }
            });
    }

    /**
       * Fragment adapter for ViewPager
       * @author dziobas
       *
       */
    public static class TabPager extends FragmentPagerAdapter {
        private Context mContext;

        // registered fragments in adapter
        private Class[] pages = { ItemsFragment.class, ItemsFragment.class, ItemsFragment.class };

        public TabPager(FragmentActivity activity, ViewPager pager) {
            super(activity.getSupportFragmentManager());
            mContext = activity;
            pager.setAdapter(this);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = Fragment.instantiate(mContext, pages[position].getName());
            Bundle   b        = new Bundle();
            b.putString(ItemsFragment.TITLE, (1 + position) + ". Items");
            fragment.setArguments(b);

            return fragment;
        }

        @Override
        public int getCount() {
            return pages.length;
        }
    }
}

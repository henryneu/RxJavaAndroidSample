package com.bkjk.rxjavasample.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.bkjk.rxandroidsearch.R;
import com.bkjk.rxjavasample.fragment.ElementFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main2Activity extends AppCompatActivity {

    @BindView(R.id.rxjava_tool_bar)
    Toolbar mToolBar;
    @BindView(android.R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.rxjava_view_pager)
    ViewPager mViewPager;
    @BindView(R.id.floating_action_button)
    FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolBar);

        mViewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        ElementFragment elementFragment = new ElementFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("queryKey", "5");
                        elementFragment.setArguments(bundle);
                        return elementFragment;
                    case 1:
                        return new ElementFragment();
                    case 2:
                        return new ElementFragment();
                    case 3:
                        return new ElementFragment();
                    default:
                        return new ElementFragment();
                }
            }

            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return getString(R.string.title_element);
                    case 1:
                        return getString(R.string.title_map);
                    case 2:
                        return getString(R.string.title_zip);
                    case 3:
                        return getString(R.string.title_token_flatmap);
                    default:
                        return getString(R.string.title_element);
                }
            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
    }
}

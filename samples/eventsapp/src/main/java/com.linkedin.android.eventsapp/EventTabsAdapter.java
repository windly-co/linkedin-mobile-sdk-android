package com.linkedin.android.eventsapp;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import java.util.ArrayList;

public class EventTabsAdapter extends FragmentPagerAdapter implements TabListener, OnPageChangeListener {
  private final Context mContext;
  private final ActionBar mActionBar;
  private final ViewPager mViewPager;
  private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();
  private final ArrayList<Fragment> mFragments = new ArrayList<Fragment>();

  public EventTabsAdapter(FragmentActivity activity, ViewPager pager) {
    super(activity.getSupportFragmentManager());
    mContext = activity;
    mActionBar = activity.getActionBar();
    mViewPager = pager;
    mViewPager.setAdapter(this);
    mViewPager.setOnPageChangeListener(this);
  }

  public void addTab(Tab tab, Class<?> clss, Event event) {
    TabInfo info = new TabInfo(clss, event);
    tab.setTag(info);
    tab.setTabListener(this);
    mTabs.add(info);
    mFragments.add(EventFragment.newInstance(event));
    mActionBar.addTab(tab);
    notifyDataSetChanged();
  }

  @Override
  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

  }

  @Override
  public void onPageSelected(int position) {
    mActionBar.setSelectedNavigationItem(position);
  }

  @Override
  public void onPageScrollStateChanged(int state) {

  }

  @Override
  public void onTabSelected(Tab tab, FragmentTransaction ft) {
    mViewPager.setCurrentItem(tab.getPosition());
    Object tag = tab.getTag();
    for (int i = 0; i < mTabs.size(); i++) {
      if (mTabs.get(i) == tag) {
        mViewPager.setCurrentItem(i);
      }
    }
  }

  @Override
  public void onTabUnselected(Tab tab, FragmentTransaction ft) {
  }

  @Override
  public void onTabReselected(Tab tab, FragmentTransaction ft) {

  }

  @Override
  public Fragment getItem(int position) {
    return mFragments.get(position);
  }

  @Override
  public int getCount() {
    return mTabs.size();
  }

  static final class TabInfo {
    private final Class<?> clss;
    private final Event event;

    TabInfo(Class<?> _class, Event _event) {
      clss = _class;
      event = _event;
    }
  }
}

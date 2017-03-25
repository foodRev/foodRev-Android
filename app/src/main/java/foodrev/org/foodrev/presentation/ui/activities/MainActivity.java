package foodrev.org.foodrev.presentation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import foodrev.org.foodrev.R;
import foodrev.org.foodrev.domain.dummy.DummyContent;
import foodrev.org.foodrev.presentation.presenters.DriverModePresenter;
import foodrev.org.foodrev.presentation.presenters.MainPresenter;
import foodrev.org.foodrev.presentation.presenters.impl.MainPresenterImpl;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.CoordinatorMainLanding.CoordinatorMainActivity;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.DetailItemActivity;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.FoodMap;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.DriverMode.DriverModeActivity;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.ItemFragment;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.json.JSONHelpers.JSONAsyncRequest;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.json.JsonActivity;

import static foodrev.org.foodrev.domain.dummy.DummyContent.CARE_TITLE;
import static foodrev.org.foodrev.domain.dummy.DummyContent.COMMUNITY_CENTER_TITLE;
import static foodrev.org.foodrev.domain.dummy.DummyContent.DONOR_TITLE;
import static foodrev.org.foodrev.domain.dummy.DummyContent.DRIVER_TITLE;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ItemFragment.OnListFragmentInteractionListener,
        MainPresenter.View {

    private static final String TAG = "MainActivity";
    private MainPresenter mPresenter;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private MainActivity.SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DetailItemActivity.class);

                intent.putExtra("mode", true);

                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new MainActivity.SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.view_pager_container);

        mSectionsPagerAdapter.addFragment(ItemFragment.newInstance(DRIVER_TITLE), DRIVER_TITLE);
        mSectionsPagerAdapter.addFragment(ItemFragment.newInstance(DONOR_TITLE), DONOR_TITLE);
        mSectionsPagerAdapter.addFragment(ItemFragment.newInstance(COMMUNITY_CENTER_TITLE), COMMUNITY_CENTER_TITLE);
        mSectionsPagerAdapter.addFragment(ItemFragment.newInstance(CARE_TITLE), CARE_TITLE);

        mViewPager.setAdapter(mSectionsPagerAdapter);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);

        mTabLayout.setupWithViewPager(mViewPager);

        Log.d("main", "we are at main again? maybe");

        attachPresenter();
    }

    public void attachPresenter() {
        mPresenter = (MainPresenterImpl) getLastCustomNonConfigurationInstance();
        if (mPresenter == null) {
            mPresenter = new MainPresenterImpl();
        }
        mPresenter.attachView(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            moveTaskToBack(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;

        switch(id){
            case(R.id.nav_profile):
                goToDetailItemActivity();
                break;
            case(R.id.nav_coordinator):
                intent = new Intent(this, CoordinatorMainActivity.class);
                startActivity(intent);
                break;
            case(R.id.nav_driver):
                intent = new Intent(this, DriverModeActivity.class);
                startActivity(intent);
                break;
            case(R.id.nav_donor):
                intent = new Intent(this, FoodMap.class);
                startActivity(intent);
                break;
            case(R.id.nav_community):
                intent = new Intent(this, FoodMap.class);
                startActivity(intent);
                break;
            case(R.id.nav_sign_out):
                mPresenter.signOut();
                break;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void goToSignInActivity() {
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void goToDetailItemActivity() {
        startActivity(new Intent(this, DetailItemActivity.class));
    }

    @Override
    public void signOut() {
        mPresenter.signOut();
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        mPresenter.destroy();
        super.onDestroy();
    }
}

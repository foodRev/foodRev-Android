package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.DriverMode;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import foodrev.org.foodrev.R;
import foodrev.org.foodrev.domain.infos.models.AbstractModel;
import foodrev.org.foodrev.presentation.presenters.DriverModePresenter;
import foodrev.org.foodrev.presentation.presenters.impl.DriverModePresenterImpl;
import foodrev.org.foodrev.presentation.ui.activities.SignInActivity;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.FoodMap;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.DetailItemActivity;
import foodrev.org.foodrev.presentation.ui.activities.rapidprototype.ItemFragment;


public class DriverModeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ItemFragment.OnListFragmentInteractionListener,
        DriverModePresenter.View {

    private static final String TAG = "DriverModeActivity";
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private DriverModePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_mode);
        setupUi();
        setupRecyclerView();
        attachPresenter();
    }

    private void setupUi() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setupRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.driver_mode_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        //using linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        String[] myDataset = new String[60];

        //specify an adapter
        mAdapter = new DriverModeRecyclerViewAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void attachPresenter() {
        mPresenter = (DriverModePresenterImpl) getLastCustomNonConfigurationInstance();
        if (mPresenter == null) {
            mPresenter = new DriverModePresenterImpl();
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
            case(R.id.nav_main_prototype):
                finish();
                break;
            case(R.id.nav_foodmap):
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

    @Override
    public void goToSignInActivity() {
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }

    //    @Override
    public void goToDetailItemActivity() {
        startActivity(new Intent(this, DetailItemActivity.class));
    }
    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        mPresenter.destroy();
        super.onDestroy();
    }

    @Override
    public void onListFragmentInteraction(AbstractModel item) {

    }
}

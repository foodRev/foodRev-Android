package foodrev.org.rapidprototype;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import foodrev.org.rapidprototype.dummy.DummyContent;

public class AddItemActivity extends AppCompatActivity {

    DummyContent.DummyItem mDummyItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddItemActivity.this, "Update database with new item", Toast.LENGTH_SHORT).show();

                finish();
            }
        });

        Bundle bundle = getIntent().getExtras();

        if(bundle != null && bundle.getSerializable("item")!= null)
        {
            //TODO here get the string stored in the string variable and do
            // setText() on userName
            mDummyItem = (DummyContent.DummyItem) bundle.getSerializable("item");
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mDummyItem != null) {
            Toast.makeText(this, mDummyItem.toString(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Editing Mode", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

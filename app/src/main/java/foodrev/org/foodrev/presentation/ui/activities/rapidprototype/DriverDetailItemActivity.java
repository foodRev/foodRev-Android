package foodrev.org.foodrev.presentation.ui.activities.rapidprototype;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import foodrev.org.foodrev.R;
import foodrev.org.foodrev.domain.infos.models.AbstractModel;

public class DriverDetailItemActivity extends AppCompatActivity {

    AbstractModel mItem;
    NestedScrollView contentDetail;
    NestedScrollView contentEdit;

    ImageView imageView;
    TextView name;
    TextView phone;
    TextView email;

    EditText nameEdit;
    EditText phoneEdit;
    EditText emailEdit;

    boolean mEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        contentDetail = (NestedScrollView) findViewById(R.id.content_detail);
        contentEdit = (NestedScrollView) findViewById(R.id.content_edit);

        name = (TextView) findViewById(R.id.user_name);
        phone = (TextView) findViewById(R.id.user_phone);
        email = (TextView) findViewById(R.id.user_email);

        nameEdit = (EditText) findViewById(R.id.user_name_edit);
        phoneEdit = (EditText) findViewById(R.id.user_phone_edit);
        emailEdit = (EditText) findViewById(R.id.user_email_edit);


        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mEditMode) {
                    switchToViewDetailMode();
                    Toast.makeText(DriverDetailItemActivity.this, "Update database with new item", Toast.LENGTH_SHORT).show();
                } else {
                    switchToEditDetailMode();
                }

                mEditMode = !mEditMode;
            }
        });

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            if (bundle.getSerializable("item") != null) {
                //TODO here get the string stored in the string variable and do
                // setText() on userName
                mItem = (AbstractModel) bundle.getSerializable("item");

            }

            if (bundle.getBoolean("mode")) {
                mEditMode = bundle.getBoolean("mode");
                switchToEditDetailMode();
            }
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        if (mItem != null) {
            Toast.makeText(this, mItem.toString(), Toast.LENGTH_SHORT).show();
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

    void switchToViewDetailMode() {
        contentDetail.setVisibility(View.VISIBLE);
        contentEdit.setVisibility(View.GONE);
    }

    void switchToEditDetailMode() {
        Toast.makeText(DriverDetailItemActivity.this, "edit", Toast.LENGTH_SHORT).show();
        contentDetail.setVisibility(View.GONE);
        contentEdit.setVisibility(View.VISIBLE);
    }
}

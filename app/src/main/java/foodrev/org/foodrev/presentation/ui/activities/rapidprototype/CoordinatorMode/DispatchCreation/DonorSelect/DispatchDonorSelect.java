package foodrev.org.foodrev.presentation.ui.activities.rapidprototype.CoordinatorMode.DispatchCreation.DonorSelect;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import foodrev.org.foodrev.R;
import foodrev.org.foodrev.domain.models.dispatchModels.Dispatch;
import foodrev.org.foodrev.domain.models.dispatchModels.DispatchDonor;

public class DispatchDonorSelect extends AppCompatActivity {

    ArrayList<DispatchDonor> dispatchDonors = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch_donor_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView rvDispatchDonors = (RecyclerView) findViewById(R.id.rvDonorSelect);

        // initialize donor array
        dispatchDonors.add(new DispatchDonor("Donor M", 10));

        // create adapter and pass in the data
        DonorSelectAdapter donorSelectAdapter = new DonorSelectAdapter(this,dispatchDonors);

        // attach the adapter to the rv and populate items
        rvDispatchDonors.setAdapter(donorSelectAdapter);

        // set layout manager to position items
        rvDispatchDonors.setLayoutManager(new LinearLayoutManager(this));



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

}

package foodrev.org.foodrev.presentation.ui.activities.rapidprototype;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.MessageButtonBehaviour;
import agency.tango.materialintroscreen.SlideFragmentBuilder;
import foodrev.org.foodrev.Manifest;
import foodrev.org.foodrev.R;
import foodrev.org.foodrev.presentation.ui.activities.MainActivity;

public class IntroSlidesGeneric extends MaterialIntroActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.colorPrimaryDark)
                        .buttonsColor(R.color.colorAccent)
                        .image(R.drawable.foodrev_banner)
                        .title("Be a SuperHero")
                        .description("Deliver food to those in need")
                        .build()
        );

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.colorPrimaryDark)
                        .buttonsColor(R.color.colorAccent)
                        .image(R.drawable.foodrev_banner)
                        .title("Donate to Support FoodRev Development")
                        .description("donated meals and funding will exponentially expand our efforts")
                        .build()
        );
        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.colorPrimaryDark)
                        .buttonsColor(R.color.colorAccent)
                        .image(R.drawable.foodrev_banner)
                        .title("Create the world you want to live in")
                        .description("")
                        .build()
        );
    }

    //override the finish method in order to determine where the user goes after intro slides
    @Override
    public void finish() {
        Intent i = new Intent(IntroSlidesGeneric.this, MainActivity.class);
        startActivity(i);
        super.finish();
    }

}

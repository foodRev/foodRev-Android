///**
// * Copyright 2016 Google Inc. All Rights Reserved.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// * http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package foodrev.org.foodrev.presentation.ui.activities;
//
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//
//import foodrev.org.foodrev.R;
//
//
//
//public class TemplateActivity extends AppCompatActivity implements TemplatePresenter.View {
//
//
//    private static final String TAG = "TemplateActivity";
//    private TemplatePresenter mPresenter;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_google);
//        attachPresenter();
//        setupUi();
//
//    }
//    public void attachPresenter() {
//        mPresenter = (TemplatePresenterImpl) getLastCustomNonConfigurationInstance();
//        if (mPresenter == null) {
//            mPresenter = new TemplatePresenterImpl();
//        }
//        mPresenter.attachView(this);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        mPresenter.stop();
//    }
//
//    @Override
//    protected void onDestroy() {
//        mPresenter.detachView();
//        super.onDestroy();
//    }
//
//    @Override
//    public void showProgressDialog() {
//        findViewById(R.id.sign_in_button).setVisibility(View.GONE);
//        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
//    }
//
//    @Override
//    public void hideProgressDialog() {
//        findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
//        findViewById(R.id.progressBar).setVisibility(View.GONE);
//    }
//
//    // Save presenter
//    @Override
//    public Object onRetainCustomNonConfigurationInstance() {
//        return mPresenter;
//    }
//}

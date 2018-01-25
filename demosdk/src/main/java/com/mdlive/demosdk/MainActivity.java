package com.mdlive.demosdk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mdlive.mdlcore.application.MdlApplicationSupport;
import com.mdlive.mdlcore.fwfrodeo.fwf.enumz.FwfSSOGender;
import com.mdlive.mdlcore.fwfrodeo.fwf.enumz.FwfSSORelationship;
import com.mdlive.mdlcore.fwfrodeo.fwf.enumz.FwfState;
import com.mdlive.mdlcore.model.MdlSSODetail;
import com.mdlive.mdlcore.model.MdlUserSession;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/*
 * Copyright MDLive.  All rights reserved.
 */
public class MainActivity extends AppCompatActivity {

    private Button mSeeProviderButton;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSeeProviderButton = (Button) findViewById(R.id.enter);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    public void enterSDK(View v) {
        showProgressBar(true);

        MdlSSODetail ssoDetail = MdlSSODetail.builder()
                .ou("cigna")
                .firstName("lucas")
                .lastName("hyatt")
                .gender(FwfSSOGender.MALE)
                .birthdate("25-02-1972")
                .subscriberId("U9305272401")
                .memberId("test5252|1579446979.73987|ZDQwMmE0YzU3ZjEzMjlkMTYwZWMwZWMzZTJjMjgwY2FmOWU4NGRjY2ZmODU3NGFkYmRiZmQ0MzZjZDc5ZDY0YQ==")
                .phone("555-555-5555")
                .email("ahadida@mdlive.com")
                .address1("address1")
                .address2("address2")
                .city("Sunrise")
                .state(FwfState.FL)
                .zipCode("33303")
                .relationship(FwfSSORelationship.SELF)
                .build();

        MdlApplicationSupport.getAuthenticationCenter()
                .singleSignOn(ssoDetail)
                .map(new Func1<MdlUserSession, Intent>() {
                    @Override
                    public Intent call(MdlUserSession mdlUserSession) {
                        return MdlApplicationSupport.getIntentFactory().ssoDashboard(MainActivity.this);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<Intent>() {
                            @Override
                            public void call(Intent intent) {
                                startActivity(intent);
                                showProgressBar(false);
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                Log.e(MainActivity.class.getSimpleName(), throwable.toString());
                                showProgressBar(false);
                                Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                );
    }

    private void showProgressBar(boolean showProgressBar) {
        mSeeProviderButton.setVisibility(showProgressBar ? View.GONE : View.VISIBLE);
        mProgressBar.setVisibility(showProgressBar ? View.VISIBLE : View.GONE);
    }

}

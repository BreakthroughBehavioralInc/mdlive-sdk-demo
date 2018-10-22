package com.mdlive.demosdk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

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
                .ou("Cigna")
                .firstName("Tucker")
                .lastName("Fitzgerald")
                .gender(FwfSSOGender.MALE)
                .birthdate("18-04-1973")
                .subscriberId("U9305273601")
                .memberId("oneguidetestuser1|1561063397.4181788|MGFkYzJjNzFhNTZiYjgxZmEyMTA0NTU3OWVkNGQzMzQ5NDJjZDU1ZmEwODY3ZjEyNGUyZDRiYjhjMTY3Mzg0NQ==")
                .phone("555-555-5555")
                .email("some@email.com")
                .address1("PO BOX 949")
                .address2("address2")
                .city("BLOOMFIELD")
                .state(FwfState.CT)
                .zipCode("06002")
                .relationship(FwfSSORelationship.SELF)
                .build();

        /*MdlSSODetail ssoDetail = MdlSSODetail.builder()
                .ou("Cigna")
                .firstName("Emilio")
                .lastName("Negron")
                .gender(FwfSSOGender.MALE)
                .birthdate("01-01-1979")
                .subscriberId("10067837300")
                .memberId("enegron|1548259115.2447784|YWFlZWViOGZkODVhZDY5YjJiNjA1YTBhYzc0MjI4NjliNTA0YTI3OGZkM2M1OWYyNDg2MjFhMzVkMDNhODA2NQ==")
                .phone("555-555-5555")
                .email("ahadida@mdlive.com")
                .address1("address1")
                .address2("address2")
                .city("Sunrise")
                .state(FwfState.FL)
                .zipCode("33303")
                .relationship(FwfSSORelationship.SELF)
                .build();*/

        /*MdlSSODetail ssoDetail = MdlSSODetail.builder()
                .ou("Cigna")
                .firstName("Bella")
                .lastName("Fraser")
                .gender(FwfSSOGender.MALE)
                .birthdate("10-04-1985")
                .subscriberId("10067837300")
                .memberId("bellafraser|1579446979.73987|MjQxZDU5MDMxNDVlZjlhN2U4NDY4MjQ2OGZkN2YyNGNiODE1N2ZhM2Y5YWNiYzRiODhiMWVhMTE2ZjM0NjI3NA==")
                .phone("555-555-5555")
                .email("ahadida@mdlive.com")
                .address1("address1")
                .address2("address2")
                .city("Sunrise")
                .state(FwfState.FL)
                .zipCode("33303")
                .relationship(FwfSSORelationship.SELF)
                .build();*/

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
                            }
                        }
                );
    }

    private void showProgressBar(boolean showProgressBar) {
        mSeeProviderButton.setVisibility(showProgressBar ? View.GONE : View.VISIBLE);
        mProgressBar.setVisibility(showProgressBar ? View.VISIBLE : View.GONE);
    }

}

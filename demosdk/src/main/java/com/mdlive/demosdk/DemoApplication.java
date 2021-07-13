package com.mdlive.demosdk;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDexApplication;

import com.mdlive.mdlcore.application.configuration.MdlBootstrap;
import com.mdlive.mdlcore.application.configuration.MdlConfiguration;
import com.mdlive.mdlcore.fwfrodeo.fwf.model.FwfMapConfiguration;
import com.mdlive.mdlcore.fwfrodeo.fwf.widget.FwfPhoneNumberWidget;
import com.mdlive.mdlcore.tracker.analytics.AnalyticsEvent;
import com.mdlive.mdlcore.tracker.analytics.AnalyticsUserAttribute;
import com.mdlive.mdlcore.tracker.analytics.engines.AnalyticsEngine;

import org.jetbrains.annotations.NotNull;

/*
 * Copyright MDLive.  All rights reserved.
 */
public class DemoApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        MdlConfiguration configuration = new MdlConfiguration();
        configuration.addAnalyticsEngine(new ConsoleAnalyticsEngine());
        configuration.getApplicationConstantsBuilder()
                .debug(BuildConfig.DEBUG)
                .isSessionTimeoutEnabled(false)
                .isSSOsession(true)
                .shouldConfirmWizardsExit(false)
                .phoneNumberFormatter(CSpirePhoneNumberFormatter.getInstance())
                .mapConfiguration(FwfMapConfiguration.builder()
                        .userLocationMarkerColor(FwfMapConfiguration.MarkerColor.AZURE)
                        .defaultPharmacyMarkerColor(FwfMapConfiguration.MarkerColor.RED)
                        .selectedPharmacyMarkerColor(FwfMapConfiguration.MarkerColor.MAGENTA)
                        .build());
        MdlBootstrap.start(this, configuration);
    }

    /**
     * Analytics engine class example to log events in the console using System.out.println
     */
    private static final class ConsoleAnalyticsEngine implements AnalyticsEngine {

        /**
         * This method is used for tracking all user events and are documented here https://breakthrough.atlassian.net/wiki/spaces/MP/pages/135755759/Android+4.0+Screen+and+Event+Tracking
         *
         * @param event Event.
         */
        @Override
        public void logEvent(AnalyticsEvent event) {
            // Send these analytics info to our desired analytics engine, in this case System.out
            System.out.println("ConsoleAnalyticsEngine =====> Event Name: " + event.getMName());
        }

        @Override
        public void logScreenInit(@NotNull Activity activity, @NotNull String s, @NotNull String s1) {
            System.out.println("hello");
        }

        @Override
        public void setAttribute(@NotNull AnalyticsUserAttribute analyticsUserAttribute) {
            System.out.println("hello");
        }

        @Override
        public void setUserId(int i) {
            System.out.println("hello");
        }
    }

    private static final class CSpirePhoneNumberFormatter implements FwfPhoneNumberWidget.FwfPhoneNumberFormatter {

        private static FwfPhoneNumberWidget.FwfPhoneNumberFormatter sPhoneNumberFormatter;

        private CSpirePhoneNumberFormatter() {
        }

        static FwfPhoneNumberWidget.FwfPhoneNumberFormatter getInstance() {
            if (sPhoneNumberFormatter == null) {
                sPhoneNumberFormatter = new CSpirePhoneNumberFormatter();
            }
            return sPhoneNumberFormatter;
        }

        @Override
        public String formatPhoneNumber(@NonNull String pRowPhoneNumber) {
            final int inputLength = pRowPhoneNumber.length();
            final StringBuilder builder = new StringBuilder();
            if (inputLength <= 3) {
                builder.append(pRowPhoneNumber);
            } else if (inputLength <= 6) {
                builder.append(pRowPhoneNumber.substring(0, 3))
                        .append('.')
                        .append(pRowPhoneNumber.substring(3));
            } else if (inputLength <= 10) {
                builder.append(pRowPhoneNumber.substring(0, 3))
                        .append('.')
                        .append(pRowPhoneNumber.substring(3, 6))
                        .append('.')
                        .append(pRowPhoneNumber.substring(6));
            } else {
                builder.append(pRowPhoneNumber.substring(0, 3))
                        .append('.')
                        .append(pRowPhoneNumber.substring(3, 6))
                        .append('.')
                        .append(pRowPhoneNumber.substring(6, 10));
            }
            return builder.toString();
        }

    }

}

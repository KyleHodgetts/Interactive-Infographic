package com.kylehodgetts.interactiveinfographic.controller.data;

import android.test.ActivityInstrumentationTestCase2;

import com.kylehodgetts.interactiveinfographic.model.DataBank;
import com.kylehodgetts.interactiveinfographic.model.DataEntry;
import com.kylehodgetts.interactiveinfographic.view.InfoGraphicActivity;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author Kyle Hodgetts
 * @version 1.0
 * Tests that the unemployment data task fetcher runs as expected
 */
public class GetUnemploymentPercentagesTaskTest extends ActivityInstrumentationTestCase2<InfoGraphicActivity> {
    private static final String MALE_UNEMPLOYMENT_URL =
            "http://api.worldbank.org/countries/gbr/" +
                    "indicators/SL.UEM.1524.MA.NE.ZS?" +
                    "&date=1991%3A2011&format=json";

    private boolean called;

    public GetUnemploymentPercentagesTaskTest() {
        super(InfoGraphicActivity.class);
    }

    @BeforeClass
    public void setUp() throws Exception {
        super.setUp();
        called = false;
    }

    @AfterClass
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public final void testSuccessfulFetch() throws Throwable {
        final CountDownLatch signal = new CountDownLatch(1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                new GetUnemploymentPercentagesTask(getActivity()){
                    @Override
                    protected Void doInBackground(String... params) {
                        called = true;
                        return null;
                    }

                    @Override
                    protected void onProgressUpdate(DataEntry... dataEntries) {
                        signal.countDown();
                        assertNotNull("Progress Employment Entry was null", dataEntries[0]);
                        DataBank dataBank = DataBank.getDataBank(getInstrumentation().getContext());
                        assertNotNull("Employment Entry was not added to data bank",
                                dataBank.getUnemploymentPercentages().get(0));
                    }
                }.execute(MALE_UNEMPLOYMENT_URL);
            }
        }).run();

        signal.await(10, TimeUnit.SECONDS);
        assertTrue(called);
    }
}
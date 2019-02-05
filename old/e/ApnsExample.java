package com.neijel.push.notify;

import com.neijel.push.notify.apns.Apns;
import com.neijel.push.notify.apns.config.ApnsNotificationsConfig;
import com.neijel.push.notify.apns.config.ApnsPayload;
import com.neijel.push.notify.apns.exceptions.BadCredentialException;
import com.neijel.push.notify.apns.query.PayloadResponse;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ApnsExample {
    public static void main(String[] args) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InterruptedException, BadCredentialException {

        ClassLoader classLoader = ApnsExample.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("bundle.p8")).getFile());

        ApnsNotificationsConfig apnsNotificationsConfig = new ApnsNotificationsConfig();
        apnsNotificationsConfig.setKeyId("7MQ2CY2876");
        apnsNotificationsConfig.setTeamId("SPWJM4DSDE");
        apnsNotificationsConfig.setP8(file);
        apnsNotificationsConfig.setTopic("com.tomych.itls.stage");


        ArrayList<ApnsPayload> apnsPayloads = new ArrayList<>();

        for (int j = 0; j < 1; j++) {
            ApnsPayload apnsPayload = new ApnsPayload();
            apnsPayload.setApsAlertBody("HELLO!" + j);
            apnsPayload.setApsAlertTitle("OMG!");

            ArrayList<String> strings = new ArrayList<>();
            for (int i = 0; i < 1; i++) {
                strings.add("B251F2B8DB70A15511032BFDFE1D5BA04462359B3E1E64A19A5809B8C34CC29A");
            }

            apnsPayload.setDevices(strings);

            apnsPayloads.add(apnsPayload);
        }

        Apns apns = new Apns(apnsNotificationsConfig);

        List<PayloadResponse> payloadResponses = apns.sendBulk(apnsPayloads);

        System.out.println("");

        apns.close();
    }
}

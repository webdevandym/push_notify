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
        apnsNotificationsConfig.setTopic("com.tomych.itls.dev");


        Apns apns = new Apns(apnsNotificationsConfig);

        ArrayList<ApnsPayload> apnsPayloads = new ArrayList<>();

        for (int j = 0; j < 50; j++) {
            ApnsPayload apnsPayload = new ApnsPayload();
            apnsPayload.setApsAlertBody("HELLO!" + j);
            apnsPayload.setApsAlertTitle("OMG!");

            ArrayList<String> strings = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                strings.add("F0B6F383EADAB0B47644C0B8A4CEA3192495F7C2664E88730520CDC976733CA3");
            }

            apnsPayload.setDevices(strings);

            apns.send(apnsPayload);
        }



//        List<PayloadResponse> payloadResponses = apns.sendBulk(apnsPayloads);

        System.out.println("");

        apns.close();
    }
}

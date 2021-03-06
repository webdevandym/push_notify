package com.neijel.push.notify.apns;

import com.turo.pushy.apns.util.ApnsPayloadBuilder;
import com.neijel.push.notify.apns.config.ApnsPayload;

import java.util.Map;

class ApnsMessageBuilder {

    private final ApnsPayload apnsPayload;

    ApnsMessageBuilder(ApnsPayload apnsPayload) {
        this.apnsPayload = apnsPayload;
    }

    String build() {
        final ApnsPayloadBuilder payloadBuilder = new ApnsPayloadBuilder();

        payloadBuilder.setAlertBody(apnsPayload.getApsAlertBody());
        payloadBuilder.setAlertTitle(apnsPayload.getApsAlertTitle());
        payloadBuilder.setSound(apnsPayload.getSound());

        additionProps(payloadBuilder);

        return payloadBuilder.buildWithDefaultMaximumLength();
    }


    private void additionProps(ApnsPayloadBuilder apnsPayloadBuilder) {

        Map<String, Object> additionFields = apnsPayload.getAdditionFields();

        if (additionFields.isEmpty()) {
            return;
        }

        additionFields.forEach(apnsPayloadBuilder::addCustomProperty);
    }
}

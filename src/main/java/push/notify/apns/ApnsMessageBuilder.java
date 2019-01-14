package push.notify.apns;

import com.turo.pushy.apns.util.ApnsPayloadBuilder;
import push.notify.apns.config.ApnsPayload;

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

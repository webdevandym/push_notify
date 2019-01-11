package push.notify.apns;

import com.turo.pushy.apns.util.ApnsPayloadBuilder;
import push.notify.apns.config.ApnsMessage;

import java.util.Map;

class ApnsMessageBuilder {

    private final ApnsMessage apnsMessage;

    ApnsMessageBuilder(ApnsMessage apnsMessage) {
        this.apnsMessage = apnsMessage;
    }

    String build() {
        final ApnsPayloadBuilder payloadBuilder = new ApnsPayloadBuilder();

        payloadBuilder.setAlertBody(apnsMessage.getApsAlertBody());
        payloadBuilder.setAlertTitle(apnsMessage.getApsAlertTitle());

        additionProps(payloadBuilder);

        return payloadBuilder.buildWithDefaultMaximumLength();
    }


    private void additionProps(ApnsPayloadBuilder apnsPayloadBuilder) {

        Map<String, Object> additionFields = apnsMessage.getAdditionFields();

        if (additionFields.isEmpty()) {
            return;
        }

        additionFields.forEach(apnsPayloadBuilder::addCustomProperty);
    }
}

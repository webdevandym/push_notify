package push.notify.apns;

import com.turo.pushy.apns.util.ApnsPayloadBuilder;
import push.notify.apns.config.Message;

import java.util.Map;

class ApnsMessageBuilder {

    private final Message message;

    ApnsMessageBuilder(Message message) {
        this.message = message;
    }

    String build() {
        final ApnsPayloadBuilder payloadBuilder = new ApnsPayloadBuilder();

        payloadBuilder.setAlertBody(message.getApsAlertBody());
        payloadBuilder.setAlertTitle(message.getApsAlertTitle());

        additionProps(payloadBuilder);

        return payloadBuilder.buildWithDefaultMaximumLength();
    }


    private void additionProps(ApnsPayloadBuilder apnsPayloadBuilder) {

        Map<String, Object> additionFields = message.getAdditionFields();

        if (additionFields.isEmpty()) {
            return;
        }

        additionFields.forEach(apnsPayloadBuilder::addCustomProperty);
    }
}

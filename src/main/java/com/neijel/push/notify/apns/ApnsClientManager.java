package push.notify.apns;

import com.turo.pushy.apns.ApnsClient;
import com.turo.pushy.apns.ApnsClientBuilder;
import com.turo.pushy.apns.auth.ApnsSigningKey;
import push.notify.apns.config.ApnsNotificationsConfig;
import push.notify.apns.exceptions.BadCredentialException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

class ApnsClientManager {

    private final ApnsClient apnsClient;

    ApnsClientManager(ApnsNotificationsConfig apnsNotificationsConfig) throws NoSuchAlgorithmException, BadCredentialException, InvalidKeyException, IOException {
        apnsClient = initClient(apnsNotificationsConfig);
    }

    ApnsClient getApnsClient() {
        return apnsClient;
    }

    private ApnsClient initClient(ApnsNotificationsConfig apnsNotificationsConfig) throws NoSuchAlgorithmException, InvalidKeyException, IOException, BadCredentialException {
        ApnsClientBuilder apnsClientBuilder = new ApnsClientBuilder()
                .setApnsServer(apnsNotificationsConfig.getSandbox() ? ApnsClientBuilder.DEVELOPMENT_APNS_HOST : ApnsClientBuilder.PRODUCTION_APNS_HOST);

        setAuthMethod(apnsClientBuilder, apnsNotificationsConfig);

        return apnsClientBuilder.build();
    }

    private void setAuthMethod(ApnsClientBuilder apnsClientBuilder, ApnsNotificationsConfig apnsNotificationsConfig) throws NoSuchAlgorithmException, InvalidKeyException, IOException, BadCredentialException {
        if (Objects.nonNull(apnsNotificationsConfig.getP8())) {
            apnsClientBuilder.setSigningKey(
                    ApnsSigningKey.loadFromPkcs8File(apnsNotificationsConfig.getP8(), apnsNotificationsConfig.getTeamId(), apnsNotificationsConfig.getKeyId())
            );
        } else if (Objects.nonNull(apnsNotificationsConfig.getP12())) {
            apnsClientBuilder.setClientCredentials(apnsNotificationsConfig.getP12(), apnsNotificationsConfig.getP12Password());
        } else {
            throw new BadCredentialException("No client credentials specified (p8|p12)");
        }
    }
}

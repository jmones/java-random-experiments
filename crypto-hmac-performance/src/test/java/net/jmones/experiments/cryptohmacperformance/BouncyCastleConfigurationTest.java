package net.jmones.experiments.cryptohmacperformance;

import static org.assertj.core.api.BDDAssertions.then;

import java.security.Provider;
import java.security.Security;
import java.util.Arrays;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.Test;

class BouncyCastleConfigurationTest {

    @Test
    void should_add_bouncy_castle_to_security_providers() {
        Provider[] before = Security.getProviders();
        Security.addProvider(new BouncyCastleProvider());
        Provider[] after = Security.getProviders();

        then(Arrays.stream(before).map(Provider::getName)).doesNotContain("BC");
        then(Arrays.stream(after).map(Provider::getName)).contains("BC");
    }
}

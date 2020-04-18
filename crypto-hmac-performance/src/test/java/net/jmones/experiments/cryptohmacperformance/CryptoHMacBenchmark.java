package net.jmones.experiments.cryptohmacperformance;


import static org.assertj.core.api.BDDAssertions.then;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.concurrent.TimeUnit;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;

@BenchmarkMode(Mode.AverageTime)
@State(Scope.Benchmark)
@Warmup(iterations = 1, time = 1)
@Measurement(iterations = 2, time = 1)
@Fork(value = 2, warmups = 1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Threads(4)
public class CryptoHMacBenchmark {

    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private static final BouncyCastleProvider BOUNCY_CASTLE_PROVIDER = new BouncyCastleProvider();
    private static final String HMAC_KEY = "this-is-the-hmac-key";
    private static final String HMAC_PLAINTEXT = "this-is-the-data-to-be-hashed";
    private Mac mac;

    @Setup(Level.Invocation)
    public void prepare() throws NoSuchAlgorithmException, InvalidKeyException {
        Security.addProvider(BOUNCY_CASTLE_PROVIDER);
        mac = Mac.getInstance(HMAC_ALGORITHM, BOUNCY_CASTLE_PROVIDER);
        SecretKey key = new SecretKeySpec(HMAC_KEY.getBytes(), HMAC_ALGORITHM);
        mac.init(key);
    }

    @Benchmark
    public void benchmark_mac_initialisation_with_default_provider() throws NoSuchAlgorithmException {
        Mac mac = Mac.getInstance(HMAC_ALGORITHM);
    }

    @Benchmark
    public void benchmark_mac_initialisation_with_new_provider() throws NoSuchAlgorithmException {
        Mac mac = Mac.getInstance(HMAC_ALGORITHM, new BouncyCastleProvider());
    }

    @Benchmark
    public void benchmark_mac_initialisation_reusing_provider() throws NoSuchAlgorithmException {
        Mac mac = Mac.getInstance(HMAC_ALGORITHM, BOUNCY_CASTLE_PROVIDER);
    }

    @Benchmark
    public void benchmark_mac_calculation_initialising_with_default_provider() throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(HMAC_ALGORITHM);
        SecretKey key = new SecretKeySpec(HMAC_KEY.getBytes(), HMAC_ALGORITHM);
        mac.init(key);
        mac.update(HMAC_PLAINTEXT.getBytes());
        byte[] result = mac.doFinal();

        then(result).containsExactly(-12, -93, 97, 39, -28, 112, -67, 123, -106, 18, -103, -123, -57, -64, 116, -99, 56, -19, 1, 118, 90, 96, 88, 45, 7, 17, 82, 30, 60, -17, -53, -67);
    }


    @Benchmark
    public void benchmark_mac_calculation_initialising_with_reused_provider() throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(HMAC_ALGORITHM, BOUNCY_CASTLE_PROVIDER);
        SecretKey key = new SecretKeySpec("this-is-the-hmac-key".getBytes(), HMAC_ALGORITHM);
        mac.init(key);
        mac.update("this-is-the-data-to-be-hashed".getBytes());
        byte[] result = mac.doFinal();

        then(result).containsExactly(-12, -93, 97, 39, -28, 112, -67, 123, -106, 18, -103, -123, -57, -64, 116, -99, 56, -19, 1, 118, 90, 96, 88, 45, 7, 17, 82, 30, 60, -17, -53, -67);
    }

    @Benchmark
    public void benchmark_mac_calculation_initialising_with_new_provider() throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(HMAC_ALGORITHM, new BouncyCastleProvider());
        SecretKey key = new SecretKeySpec("this-is-the-hmac-key".getBytes(), HMAC_ALGORITHM);
        mac.init(key);
        mac.update("this-is-the-data-to-be-hashed".getBytes());
        byte[] result = mac.doFinal();

        then(result).containsExactly(-12, -93, 97, 39, -28, 112, -67, 123, -106, 18, -103, -123, -57, -64, 116, -99, 56, -19, 1, 118, 90, 96, 88, 45, 7, 17, 82, 30, 60, -17, -53, -67);
    }

    @Benchmark
    public void benchmark_mac_calculation_using_initialised_mac_and_synchronising() {
        synchronized (this) {
            mac.update("this-is-the-data-to-be-hashed".getBytes());
            byte[] result = mac.doFinal();

            then(result).containsExactly(-12, -93, 97, 39, -28, 112, -67, 123, -106, 18, -103, -123, -57, -64, 116, -99, 56, -19, 1, 118, 90, 96, 88, 45, 7, 17, 82, 30, 60, -17, -53, -67);
        }
    }
}

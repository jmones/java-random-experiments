# crypto-hmac-performance

Benchmarks the HMAC implementation of BouncyCastle and the initialisation code.

## Results

Execution in a laptop with parameters in repository (very few iterations):

```
Benchmark                                                                         Mode  Cnt      Score       Error  Units
CryptoHMacBenchmark.benchmark_mac_calculation_initialising_with_default_provider  avgt    4     88.027 ±   123.673  us/op
CryptoHMacBenchmark.benchmark_mac_calculation_initialising_with_new_provider      avgt    4  15183.786 ± 32812.785  us/op
CryptoHMacBenchmark.benchmark_mac_calculation_initialising_with_reused_provider   avgt    4     52.410 ±    25.177  us/op
CryptoHMacBenchmark.benchmark_mac_initialisation_reusing_provider                 avgt    4      1.899 ±     0.756  us/op
CryptoHMacBenchmark.benchmark_mac_initialisation_with_default_provider            avgt    4      1.468 ±     0.214  us/op
CryptoHMacBenchmark.benchmark_mac_initialisation_with_new_provider                avgt    4  14143.380 ± 20418.386  us/op
```


## JMH in IntelliJ

1. Install the plugin `JMH plugin`
2. Right-click on `CryptoHMacBenchmark`
3. Press `Run CryptoHMacBenchmark..`

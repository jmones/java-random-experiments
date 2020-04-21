# crypto-hmac-performance

Benchmarks the HMAC implementation of BouncyCastle and the initialisation code.

## Results

Execution in a laptop with parameters in repository (very few iterations):

```
Benchmark                                                                              Mode  Cnt      Score       Error  Units
CryptoHMacBenchmark.benchmark_mac_calculation_initialising_with_default_provider       avgt    4     47.412 ±    19.645  us/op
CryptoHMacBenchmark.benchmark_mac_calculation_initialising_with_new_provider           avgt    4  16830.204 ± 17253.020  us/op
CryptoHMacBenchmark.benchmark_mac_calculation_initialising_with_reused_provider        avgt    4     54.446 ±    27.232  us/op
CryptoHMacBenchmark.benchmark_mac_calculation_using_initialised_mac_and_synchronising  avgt    4    199.626 ±    45.515  us/op
CryptoHMacBenchmark.benchmark_mac_initialisation_reusing_provider                      avgt    4      1.555 ±     0.416  us/op
CryptoHMacBenchmark.benchmark_mac_initialisation_with_default_provider                 avgt    4      1.615 ±     0.558  us/op
CryptoHMacBenchmark.benchmark_mac_initialisation_with_new_provider                     avgt    4  11555.593 ±  9058.594  us/op
```


## JMH in IntelliJ

1. Install the plugin `JMH plugin`
2. Right-click on `CryptoHMacBenchmark`
3. Press `Run CryptoHMacBenchmark..`

# DCP Based Performance Metrics Tool

- Command line interface
- Timing summary sent to stdout
- Optional timing summary to file
- Uses built-in DCP file when supplied dcp-file arg does not exist

The `AppTest` class is used to invoke a sample performance run/test.

*Output from sample test:*

![screenshot](images/dcp-perf-stdout.png)

## One-Off Performance Test

Sample invocation from the terminal:

```bash
java -jar dcp-perf.jar main.dcp inA.xml inB.xml result.xml timings.txt
```

## Supported system properties
- `printer.overWrite` (true)  - Reports progress to stdout on a single line
- `compare.debug`     (false) - Outputs diagnotics files for the test - to a directory with the result-file prefix 
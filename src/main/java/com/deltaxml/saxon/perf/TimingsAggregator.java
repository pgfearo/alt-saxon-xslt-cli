package com.deltaxml.saxon.perf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimingsAggregator implements TimingsData {

    Map<String, List<Long>> timingsA;
    Map<String, List<Long>> timingsB;
    Map<String, List<Long>> timingsOut;
    List<Long> coreTimes;
    private int updateCount = 0;

    public TimingsAggregator(TimingsData timeReporter) {
        timingsA = initArrayMap(timeReporter.getFilterTimesA());
        timingsB = initArrayMap(timeReporter.getFilterTimesB());
        timingsOut = initArrayMap(timeReporter.getFilterTimesResult());
        coreTimes = new ArrayList<>();
    }

    private static Map<String, List<Long>> initArrayMap(Map<String, Long> baseMap) {
        Map<String, List<Long>> resultMap = new HashMap<>();

        baseMap.entrySet().forEach(
                (entry) -> {
                    resultMap.put(entry.getKey(), new ArrayList<Long>());
                });
        return resultMap;
    }

    public void updateTimings(TimingsData timeReporter) {
        populateArrayMap(timeReporter.getFilterTimesA(), timingsA);
        populateArrayMap(timeReporter.getFilterTimesB(), timingsB);
        populateArrayMap(timeReporter.getFilterTimesResult(), timingsOut);
        coreTimes.add(timeReporter.getComparisonTime());
        updateCount++;
    }

    private static void populateArrayMap(Map<String, Long> source, Map<String, List<Long>> target) {

        source.entrySet().forEach(
                (entry) -> {
                    target.get(entry.getKey()).add(entry.getValue());
                });
    }

    private static Map<String, Long> calcAverageTimes(Map<String, List<Long>> timings) {
        Map<String, Long> result = new HashMap<>();
        timings.entrySet().forEach(
            (entry) -> {
                String k = entry.getKey();
                List<Long> vList = entry.getValue();
                Double vDouble = vList.stream().mapToDouble(d -> d).average().orElse(0.0);
                Long vLong = (long) Math.round(vDouble);
                result.put(k, vLong);
            });;
            return result;
    }

    // --------------- Timings Data -----------------

    @Override
    public Map<String, Long> getFilterTimesA() {
        return calcAverageTimes(timingsA);
    }

    @Override
    public Map<String, Long> getFilterTimesB() {
        return calcAverageTimes(timingsB);
    }

    @Override
    public Map<String, Long> getFilterTimesResult() {
        return calcAverageTimes(timingsOut);        
    }

    @Override
    public long getComparisonTime() {
        Long result = (long) coreTimes.stream().mapToDouble(d -> d).average().orElse(0.0);
        return result;
    }

    @Override
    public int getRunCount() {
        return updateCount;
    }   
}

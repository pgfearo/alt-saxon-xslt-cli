package com.deltaxml.saxon.perf;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PrintHelper {

    private int percentageThreshold = 0;
    private PrintWriterCollection pwc = null;

    public PrintHelper(PrintWriterCollection printWriterCollection, int percentageThreshold) {
        this.pwc = printWriterCollection;
        this.percentageThreshold = percentageThreshold;
    }
    /**
     * Prints summary of timing data from a comparison to stdout
     * 
     * @param timeReporter includes the timing data
     */
    public void printTimingSummary(TimingsData timeReporter, String info) {
        int lenA = maxNameLength(timeReporter.getFilterTimesA());
        int lenB = maxNameLength(timeReporter.getFilterTimesB());
        int lenResult = maxNameLength(timeReporter.getFilterTimesResult());
        int maxLenALenB = Math.max(lenA, lenB);
        int nameLength = Math.max(maxLenALenB, lenResult) + 5;
        final Map<String, Long> coreComparison = new HashMap<String, Long>();
        final long coreTime = timeReporter.getComparisonTime();
        int tableWidth = 80;

        coreComparison.put("Comparator", coreTime);
        pwc.println();
        formatTitle("XSLT Filter Timings - Releative to Core Comparator", tableWidth);
        printInfo(info, timeReporter.getRunCount(), percentageThreshold);
        printHeading(nameLength);
        printHeadingLine(nameLength);
        processTimes(coreComparison, coreTime, nameLength);
        formatTitle("A Input Pipeline Filters", tableWidth);
        long timesAll = processTimes(timeReporter.getFilterTimesA(), coreTime, nameLength);
        formatTitle("B Input Pipeline Filters", tableWidth);
        timesAll += processTimes(timeReporter.getFilterTimesB(), coreTime, nameLength);
        formatTitle("Output Pipeline Filters", tableWidth);
        timesAll += processTimes(timeReporter.getFilterTimesResult(), coreTime, nameLength);
        formatTitle("Overall", tableWidth);
        Double percentOfComparisonTime = ((timesAll / (double) coreTime) * 100);
        printDataRow(nameLength, new AtomicInteger(1), "All Filters (including < " + percentageThreshold + "%)", timesAll, percentOfComparisonTime);
        pwc.println();
        pwc.resetColor();
        pwc.flush();
    }

    public String formatTitle(String title, int width) {
        int lineLength = (width - title.length()) / 2;
        String titleString = StringUtils.padLeftChars("", lineLength, "-") + " " + title + " " + StringUtils.padRightChars("", lineLength, "-");
        pwc.print(StringUtils.Color.GREEN);
        pwc.println(titleString);
        return titleString;
    }

    private void printInfo(String infoText, int runCount, int thresholdPercent) {
        pwc.print(StringUtils.Color.BLUE);
        pwc.println(StringUtils.padRight(infoText, 30) + StringUtils.padRight("Number of runs: " + runCount, 30) + "Threshold: " + thresholdPercent + "%");       
    }

    private void printHeading(int nameLength) {
        String paddedCount = StringUtils.padRight("#", 4);
        String paddedFilterName = StringUtils.padRight("Filter Id", nameLength);
        String paddedMiliSecs = StringUtils.padRight("Time (ms)", 10);
        String paddedPercent = StringUtils.padRight("%", 6);
        pwc.print(StringUtils.Color.YELLOW_BOLD);
        pwc.println(paddedCount + paddedFilterName + paddedMiliSecs + paddedPercent);
        pwc.print(StringUtils.Color.RESET);
    }

    private void printHeadingLine(int nameLength) {
        String paddedCount = StringUtils.padRightChars("", 4 - 1, "-") + " ";
        String paddedFilterName = StringUtils.padRightChars("", nameLength - 1, "-") + " ";
        String paddedMiliSecs = StringUtils.padRightChars("", 10 - 1, "-") + " ";
        String paddedPercent = StringUtils.padRightChars("", 6 - 1, "-") + " ";
        pwc.println(paddedCount + paddedFilterName + paddedMiliSecs + paddedPercent);
    }

    private long processTimes(Map<String, Long> filterTimes, long comparisonTime, int nameLength) {
        AtomicInteger count = new AtomicInteger(1);
        AtomicLong totalTime = new AtomicLong(0);
        filterTimes.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach((entry) -> {
                    String k = entry.getKey();
                    Long v = entry.getValue();
                    totalTime.addAndGet(v);
                    Double percentOfComparisonTime = ((v / (double) comparisonTime) * 100);
                    if (percentOfComparisonTime > percentageThreshold) {
                        printDataRow(nameLength, count, k, v, percentOfComparisonTime);
                        count.incrementAndGet();
                    }
                });
                return totalTime.get();
    }

    private void printDataRow(int nameLength, AtomicInteger count, String k, Long v,
            Double percentOfComparisonTime) {
        String paddedCount = StringUtils.padRight(String.valueOf(count), 4);
        String percent = StringUtils.formatWith2DecPlaces(percentOfComparisonTime);
        String filterName = StringUtils.cleanFilterName(k);
        String paddedFilterName = StringUtils.padRight(filterName, nameLength);
        String paddedMiliSecs = StringUtils.padRight(v.toString(), 10);
        String paddedPercent = StringUtils.padRight(percent, 6);
        pwc.print(StringUtils.Color.RESET);
        pwc.println(paddedCount + paddedFilterName + paddedMiliSecs + paddedPercent);
        pwc.flush();
    }

    private static Integer maxNameLength(Map<String, Long> map) {
        Optional<Entry<String, Long>> maxEntry = map.entrySet()
                .stream()
                .max((Entry<String, Long> e1, Entry<String, Long> e2) -> StringUtils
                        .cleanFilterNameLength(e1.getKey())
                        .compareTo(StringUtils.cleanFilterNameLength(e2.getKey())));

        return StringUtils.cleanFilterNameLength(maxEntry.get().getKey());
    }
}

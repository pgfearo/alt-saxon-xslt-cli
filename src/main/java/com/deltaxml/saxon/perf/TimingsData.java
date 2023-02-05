package com.deltaxml.saxon.perf;

import java.util.Map;

public interface TimingsData {
    public Map<String, Long> getFilterTimesA ();
    public Map<String, Long> getFilterTimesB ();
    public Map<String, Long> getFilterTimesResult ();
    public long getComparisonTime();
    public int getRunCount();
}

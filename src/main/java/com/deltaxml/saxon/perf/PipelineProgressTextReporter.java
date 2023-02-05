package com.deltaxml.saxon.perf;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import com.deltaxml.cores9api.DocumentProgressListener;
import com.deltaxml.cores9api.PipelineLocationEntry;

/**
 * 
 * 1. Gathers performance data for each filter
 * 2. When comparison has finished summarise
 *    XSLT filter performance for worst-performing
 *    filters.
 * @author Phil Fearon
 */
public class PipelineProgressTextReporter implements DocumentProgressListener, TimingsData {

  public final String inputPipeA = "input-a";
  public final String inputPipeB = "input-b";
  public final String resultPipe = "result";
  private long comparisonTime = 0;
  private final Map<String, Long> filterTimesA = new HashMap<String, Long>();
  private final Map<String, Long> filterTimesB = new HashMap<String, Long>();
  private final Map<String, Long> filterTimesResult = new HashMap<String, Long>();
  private boolean reportIncidentals = false;
  private boolean reportComparisonHeartbeat = true;
  private boolean isPipelineRunning = false;
  private Instant startInstant = null;
  private Stack<Instant> chainStartInstant = null;
  private boolean useReportedTimings = true;

  public PipelineProgressTextReporter() {
    this(true);
  }


  public PipelineProgressTextReporter(boolean useReportedTimings) {
    this.useReportedTimings = useReportedTimings;
    this.reportComparisonHeartbeat = false;
  }

  public PipelineProgressTextReporter(String reportFilename) {
  }

  public void reportFlatteningPreProcessingStart(PipelineLocationEntry ple) {
    startInstant = Instant.now();
  }

  public void reportFlatteningPreProcessingFinish(PipelineLocationEntry ple,
      long elapsedTimeNS,
      long threadCPUTimeNS) {
    finishMessage(ple.toString() + "/flatteningPreProcessing", elapsedTimeNS, threadCPUTimeNS);
  }

  public void setReportTimes(boolean reportTimes) {
    //this.reportRunTimes = reportTimes;
  }

  public void setReportIncidentals(boolean reportTimes) {
    //this.reportRunTimes = reportTimes;
  }


  private void incidentalTimedMessage(String prefix, long elapsedTime, long threadCPUTime) {
    if (reportIncidentals) {
      finishMessage(prefix, elapsedTime, threadCPUTime);
    }
  }

  private void finishMessage(String prefix, long elapsedTime, long threadCPUTime) {
    Instant endInstant = Instant.now();
    Long t = (long) 0;
    if (useReportedTimings) {
      t = (long) elapsedTime / 1000000;
    } else {
      Duration dn = Duration.between(startInstant, endInstant);
      t = dn.toMillis();
    }
    if (prefix.startsWith(inputPipeA)) {
      String filterName = prefix.substring(inputPipeA.length() + 1);
      filterTimesA.put(filterName, t);
    } else if (prefix.startsWith(inputPipeB)) {
      String filterName = prefix.substring(inputPipeB.length() + 1);
      filterTimesB.put(filterName, t);
    } else if (prefix.startsWith(resultPipe)) {
      String filterName = prefix.substring(resultPipe.length() + 1);
      filterTimesResult.put(filterName, t);
    } else {
      System.out.println("************ " + prefix);
    }
  }

  @Override
  public void reportNumberOfStages(int enabledStages) {
    //incidentalMessage("number of enabled stages = " + enabledStages);
  }

  @Override
  public void reportCompareFinish(long elapsedTime, long threadCPUTime) {
    Instant endInstant = Instant.now();
    if (useReportedTimings) {
      comparisonTime = (long) elapsedTime / 1000000;
    } else {
      Duration dn = Duration.between(startInstant, endInstant);
      comparisonTime = dn.toMillis();
    }
  }

  @Override
  public void reportCompareHeartbeat() {
    if (reportComparisonHeartbeat) {
      // pw.println("comparison still running");
    }
  }

  @Override
  public void reportCompareStart() {
    startInstant = Instant.now();
    //incidentalMessage("started comparison");
  }

  @Override
  public void reportFilterFinish(PipelineLocationEntry ple, long elapsedTimeNS, long threadCPUTimeNS) {
    String prefix = "" + ple;
    finishMessage(prefix, elapsedTimeNS, threadCPUTimeNS);
  }

  @Override
  public void reportFilterStart(PipelineLocationEntry ple) {
    startInstant = Instant.now();
    //incidentalMessage("started " + ple);
  }

  @Override
  public void reportFilterOrSubchainSkipped(PipelineLocationEntry ple, int numberOfStepsSkipped) {
    //incidentalMessage("skipped " + ple + " (step count= " + numberOfStepsSkipped + ")");
  }

  @Override
  public void reportLoadFinish(PipelineLocationEntry ple, long elapsedTime, long threadCPUTime) {
    //finishMessage("finished loading " + ple, elapsedTime, threadCPUTime);
  }

  @Override
  public void reportLoadStart(PipelineLocationEntry ple) {
    if (!isPipelineRunning) {
      filterTimesA.clear();
      filterTimesB.clear();
      filterTimesResult.clear();
      comparisonTime = 0;
      chainStartInstant = new Stack<>();
      isPipelineRunning = true;
    }
    //incidentalMessage("started loading " + ple);
  }

  @Override
  public void reportSaveFinish(long elapsedTime, long threadCPUTime) {
    isPipelineRunning = false;
  }

  @Override
  public void reportSaveStart() {
    //incidentalMessage("started saving/serializing the result");
  }

  @Override
  public void reportSubchainEnter(PipelineLocationEntry ple) {
    chainStartInstant.push(Instant.now());
    //incidentalMessage("entered subchain " + ple);
  }

  @Override
  public void reportSubchainExit(PipelineLocationEntry ple, long elapsedTime, long threadCPUTime) {
    // after chain starts filter start/end events occur so we record chain starts separately
    startInstant = chainStartInstant.pop();
    incidentalTimedMessage("exited subchain " + ple, elapsedTime, threadCPUTime);
  }

  // --------- TimingsData ---------

  @Override
  public Map<String, Long> getFilterTimesA() {
    return this.filterTimesA;
  }

  @Override
  public Map<String, Long> getFilterTimesB() {
    return this.filterTimesB;
  }

  @Override
  public Map<String, Long> getFilterTimesResult() {
    return this.filterTimesResult;
  }

  public long getComparisonTime() {
    return this.comparisonTime;
  }

  public int getRunCount() {
    return 1;
  }

}

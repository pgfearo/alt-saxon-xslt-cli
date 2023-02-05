package com.deltaxml.saxon.perf;

import java.text.SimpleDateFormat;
import com.deltaxml.cores9api.DocumentProgressListener;
import com.deltaxml.cores9api.PipelineLocationEntry;

/**
 * 
 * Emits message to Standard Output for each
 * event reported by the listener
 * 
 * @author Phil Fearon
 */
public class ProgressReporter implements DocumentProgressListener {

    public final String inputPipeA = "input-a";
    public final String inputPipeB = "input-b";
    public final String resultPipe = "result";
    private int runCount = 0;
    private int stepCount = 0;
    private int stepTotal = 0;
    private boolean overwriteMessage = true;

    private boolean reportComparisonHeartbeat = true;
    public ProgressReporter(boolean overwriteMessage) {
        this.overwriteMessage = overwriteMessage;
    }

    public void resetRunCount() {
        this.runCount = 1;
    }

    private void sendMessage(String message) {
        String timeStamp = new SimpleDateFormat("HH:mm:ss").format(new java.util.Date()) + " ";
        String runCountText = StringUtils.padRight("Run: " + runCount, 7);
        String stepCountText = StringUtils.padRight("Step: " + stepCount + " of " + this.stepTotal, 18);
        if (overwriteMessage) {
            System.out.print(StringUtils.Cursor.OVERWRITE);
        } else {
            System.out.println();
        }
        System.out.print(timeStamp + runCountText + stepCountText);
        System.out.print(StringUtils.padRight(message, 120));
        System.out.flush();
    }

    public void reportFlatteningPreProcessingStart(PipelineLocationEntry ple) {
        sendMessage("started " + ple.toString() + "/flatteningPreProcessing");
        this.stepCount++;
    }

    public void reportFlatteningPreProcessingFinish(PipelineLocationEntry ple,
            long elapsedTimeNS,
            long threadCPUTimeNS) {
        sendMessage("completed " + ple.toString() + "/flatteningPreProcessing");
    }

    @Override
    public void reportNumberOfStages(int enabledStages) {
        sendMessage("number of enabled stages = " + enabledStages);
        this.stepTotal = enabledStages;
        this.stepCount = 0;
        this.runCount++;
    }

    @Override
    public void reportCompareFinish(long elapsedTime, long threadCPUTime) {
        sendMessage("core comparison completed");
    }

    @Override
    public void reportCompareHeartbeat() {
        if (reportComparisonHeartbeat) {
            sendMessage("(heartbeat)");
        }
    }

    @Override
    public void reportCompareStart() {
        sendMessage("started comparison");
        this.stepCount++;
    }

    @Override
    public void reportFilterFinish(PipelineLocationEntry ple, long elapsedTimeNS, long threadCPUTimeNS) {
        String prefix = ple.toString();
        sendMessage("completed " + prefix);
    }

    @Override
    public void reportFilterStart(PipelineLocationEntry ple) {
        this.stepCount++;
        sendMessage("started " + ple);
    }

    @Override
    public void reportFilterOrSubchainSkipped(PipelineLocationEntry ple, int numberOfStepsSkipped) {
        sendMessage("skipped " + numberOfStepsSkipped + " steps on " + ple);
    }

    @Override
    public void reportLoadStart(PipelineLocationEntry ple) {
        sendMessage("started loading " + ple);
    }

    @Override
    public void reportLoadFinish(PipelineLocationEntry arg0, long arg1, long arg2) {
        sendMessage("completed loading");
    }

    @Override
    public void reportSaveFinish(long elapsedTime, long threadCPUTime) {
        // clear line
        sendMessage("");
    }

    @Override
    public void reportSaveStart() {
        sendMessage("started serializing the result");
        this.stepCount++;
    }

    @Override
    public void reportSubchainEnter(PipelineLocationEntry ple) {
        sendMessage("entered subchain " + ple);
    }

    @Override
    public void reportSubchainExit(PipelineLocationEntry ple, long elapsedTime, long threadCPUTime) {
        sendMessage("exited subchain " + ple);
    }

}

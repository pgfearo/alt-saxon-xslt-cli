package com.deltaxml.saxon.perf;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class PrintWriterCollection {

    private List<PrintWriter> pwList = new ArrayList<>();

    PrintWriterCollection() {
        boolean autoFlush = true;
        PrintWriter pw = new PrintWriter(System.out, autoFlush);
        pwList.add(pw);
    }
    public void add(PrintWriter pw) {
        pwList.add(pw);
    }

    public void resetColor() {
        if (pwList.size() > 0) {
            pwList.get(0).print(StringUtils.Color.RESET);
        }
    }

    public void print(String s) {
        for (PrintWriter printWriter : pwList) {
            printWriter.print(s);
        }
    }

    public void print(StringUtils.Color color) {
        // assume first PrintWriter is System.out:
        if (pwList.size() > 0) {
            pwList.get(0).print(color.toString());
        }
    }

    public void println(String s) {
        for (PrintWriter printWriter : pwList) {
            printWriter.println(s);
        }
    }

    public void println() {
        for (PrintWriter printWriter : pwList) {
            printWriter.println();
        }
    }

    public void flush() {
        for (PrintWriter printWriter : pwList) {
            printWriter.flush();;
        }
    }

    public void close() {
        for (PrintWriter printWriter : pwList) {
            printWriter.close();;
        }
    }
    
}

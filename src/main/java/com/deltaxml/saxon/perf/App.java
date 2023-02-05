package com.deltaxml.saxon.perf;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * XML Compare Performance Test Application
 */
public final class App {
    private App() {
    }

    /**
     * Compares two XML Files.
     * 
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        String sourcePath = "src/test/resources/test1/input.xml";
        String xsltPath = "src/test/resources/test1/test.xsl";
        String resultPath = "src/test/resources/test1/result.xml";

        String sourceFile = "-s:" + getFileFromPath(sourcePath);
        String xsltFile = "-xsl:" + getFileFromPath(xsltPath);
        String resultFile = "-o:" + getFileFromPath(resultPath);
        String[] cmdArgs = {sourceFile, xsltFile, resultFile};

        AltTransform.main(cmdArgs);
    }

    private static String getPathFromTest1(String filename) {
        URI testURI = null;
        try {
            testURI = App.class.getResource("/test1").toURI();
        } catch (URISyntaxException e) {
            // TODO: fix?
        }
        File testDir = new File(testURI);
        File outDir = new File(testDir, filename);
        return outDir.getAbsolutePath();
    }

    private static String getFileFromPath(String filepath) {
        Path workingPath = Paths.get("");
        Path suppliedPath = Paths.get(filepath);
        Path resolvedPath = workingPath.resolve(suppliedPath);
        return resolvedPath.toFile().getAbsolutePath();
    }
}

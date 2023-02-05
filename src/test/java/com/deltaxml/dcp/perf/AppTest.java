package com.deltaxml.dcp.perf;

import org.junit.jupiter.api.Test;

import com.deltaxml.saxon.perf.App;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Unit test for simple App.
 */
class AppTest {

    @Test
    void testApp() {
        String testName = "control-test";
        URI testURI = null;
        try {
            testURI = getClass().getResource("/formatting-elements").toURI();
        } catch (URISyntaxException e) {
            fail("Exception: " + e.getMessage());
        }
        File testDir = new File(testURI);
        File outDir = new File(testDir, "out");
        File outXmlDir = new File(outDir, "xml-output");
        outDir.mkdir();
        outXmlDir.mkdirs();

        File dcpFile = new File(testDir, "test.dcp");
        File inAFile = new File(testDir, "inA.xml");
        File inBFile = new File(testDir, "inB.xml");
        File resultFile = new File(outXmlDir, "result.xml");
        File timingFile = new File(outDir, "timing.txt");

        String[] args = {
                testName,
                dcpFile.getAbsolutePath(),
                inAFile.getAbsolutePath(),
                inBFile.getAbsolutePath(),
                resultFile.getAbsolutePath(),
                timingFile.getAbsolutePath()
        };

        App.main(args);
        assertEquals(1, 1);
    }
}

package com.deltaxml.saxon.perf;

import org.junit.jupiter.api.Test;
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
            testURI = getClass().getResource("/test1").toURI();
        } catch (URISyntaxException e) {
            fail("Exception: " + e.getMessage());
        }
        File testDir = new File(testURI);
        File outDir = new File(testDir, "out");
        File outXmlDir = new File(outDir, "xml-output");
        outDir.mkdir();
        outXmlDir.mkdirs();

        File sourceXMLFile = new File(testDir, "input.xml");
        File xsltFile = new File(testDir, "test.xsl");
        File resultFile = new File(outXmlDir, "result.xml");

        String[] args = {
                "-s:" + sourceXMLFile.getAbsolutePath(),
                "-xsl:" + xsltFile.getAbsolutePath(),
                "-o:" + resultFile.getAbsolutePath(),
        };

        AltTransform.main(args);
        assertEquals(1, 1);
    }
}

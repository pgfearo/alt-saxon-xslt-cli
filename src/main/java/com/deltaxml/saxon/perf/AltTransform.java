package com.deltaxml.saxon.perf;

import net.sf.saxon.Transform;
import net.sf.saxon.lib.Logger;
import net.sf.saxon.s9api.QName;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.Xslt30Transformer;
import net.sf.saxon.s9api.XsltExecutable;
import net.sf.saxon.trans.CommandLineOptions;
import net.sf.saxon.trans.XPathException;
import net.sf.saxon.value.DateTimeValue;

public class AltTransform extends Transform {

    public static void main(String[] args) {
        // the real work is delegated to another routine so that it can be used in a subclass
        new AltTransform().doTransform(args, "com.deltaxml.dcp.perf.AltTransform");
    }

    @Override
    protected Xslt30Transformer newTransformer(
            XsltExecutable sheet, CommandLineOptions options, Logger traceDestination) throws SaxonApiException {
        final Xslt30Transformer transformer = sheet.load30();

        transformer.setTraceFunctionDestination(traceDestination);
        transformer.setMessageListener(new AltMessageListener());
        String initialMode = options.getOptionValue("im");
        if (initialMode != null) {
            transformer.setInitialMode(QName.fromClarkName(initialMode));
        }

        String now = options.getOptionValue("now");
        if (now != null) {
            try {
                DateTimeValue currentDateTime = (DateTimeValue)DateTimeValue.makeDateTimeValue(
                        now, getConfiguration().getConversionRules()).asAtomic();
                transformer.getUnderlyingController().setCurrentDateTime(currentDateTime);
            } catch (XPathException e) {
                throw new SaxonApiException("Failed to set current time", e);
            }
        }
        // Code to enable/disable assertions at run-time is relevant only when we're running pre-compiled packages
        if ("on".equals(options.getOptionValue("ea"))) {
            transformer.getUnderlyingController().setAssertionsEnabled(true);
        } else if ("off".equals(options.getOptionValue("ea"))) {
            transformer.getUnderlyingController().setAssertionsEnabled(true);
        }
        return transformer;
    }    
}

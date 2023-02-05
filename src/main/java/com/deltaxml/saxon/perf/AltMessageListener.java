package com.deltaxml.saxon.perf;

import javax.xml.transform.SourceLocator;

import net.sf.saxon.s9api.MessageListener;
import net.sf.saxon.s9api.XdmNode;

public class AltMessageListener implements MessageListener {

    @Override
    public void message(XdmNode msg, boolean terminate, SourceLocator locator) {
        System.err.println("new: " + msg.getStringValue());
    }

}
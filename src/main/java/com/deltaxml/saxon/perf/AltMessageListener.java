package com.deltaxml.saxon.perf;

import javax.xml.transform.SourceLocator;
import net.sf.saxon.s9api.MessageListener2;
import net.sf.saxon.s9api.QName;
import net.sf.saxon.s9api.XdmNode;

public class AltMessageListener implements MessageListener2 {

    @Override
    public void message(XdmNode content, QName errorCode, boolean terminate, SourceLocator locator) {
        System.err.println(content.getStringValue());        
    }


}
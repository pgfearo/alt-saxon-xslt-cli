<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xmlns:array="http://www.w3.org/2005/xpath-functions/array"
                xmlns:map="http://www.w3.org/2005/xpath-functions/map"
                xmlns:math="http://www.w3.org/2005/xpath-functions/math"
                xmlns:ext="com.deltaxml.xpath.result.print"
                exclude-result-prefixes="#all"
                expand-text="yes"
                version="3.0">
  <xsl:include href="../../../../xpath-result-serializer/src/xpath-result-serializer-color.xsl"/>
  <xsl:output method="xml" indent="yes"/>
  <xsl:mode on-no-match="shallow-copy"/>
  
  <xsl:template match="/*" mode="#all">
    <xsl:copy>
      <xsl:variable name="one" as="xs:string" select="'alpha'"/>
      <xsl:variable name="two" as="xs:string" select="'a &lt; b'"/>
      <xsl:variable name="three" as="array(*)" select="[1,2,3,4]"/>
      <xsl:variable name="four" as="map(*)" select="map{'arr': $three}"/>
      <xsl:message expand-text="yes">
        ==== Watch Variables ====
        one:    {ext:print($one,6,'  ')}
        two:    {ext:print($two,6,'  ')}
        three:  {ext:print($three,6,'  ')}
        four:   {ext:print($four,6,'  ')}
      </xsl:message>
      
      <xsl:apply-templates select="@*, node()" mode="#current"/>
    </xsl:copy>
  </xsl:template>
  
  
  
</xsl:stylesheet>
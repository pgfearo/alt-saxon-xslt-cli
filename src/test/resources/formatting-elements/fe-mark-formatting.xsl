<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xmlns:deltaxml="http://www.deltaxml.com/ns/well-formed-delta-v1"
                exclude-result-prefixes="#all"
                xpath-default-namespace="http://docbook.org/ns/docbook"
                version="2.0">
  
  <!-- identity transform -->
  <xsl:template match="node() | @*" mode="#all">
    <xsl:copy>
      <xsl:apply-templates select="@* | node()" mode="#current"/>
    </xsl:copy>
  </xsl:template>
  
  <!-- mark bold elements as text formatting elements -->
  <xsl:template match="emphasis|code|u|b">
    <xsl:copy>
      <xsl:attribute name="deltaxml:format" select="'true'"/>
      <xsl:apply-templates select="node()"/>
    </xsl:copy>
  </xsl:template>
  
</xsl:stylesheet>

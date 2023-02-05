<xsl:stylesheet version="1.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:dxa="http://www.deltaxml.com/ns/non-namespaced-attribute"
	xmlns:deltaxml="http://www.deltaxml.com/ns/well-formed-delta-v1">
	
	<!-- 
						20201215	Brandon Aperocho	Add deltaxml:key to all nodes that have @key attribute
	-->
	
	<xsl:template match="@* | node()">
		<xsl:copy>
			<xsl:apply-templates select="@* | node()" />
		</xsl:copy>
	</xsl:template>
	
	<xsl:template match="*[@key]">
		<xsl:copy>
			<xsl:attribute name="deltaxml:key" select="@key"/>
			<xsl:apply-templates select="@* | node()" />
		</xsl:copy>
	</xsl:template>
	
	<xsl:template match="*[@id]">
		<xsl:copy>
			<xsl:attribute name="deltaxml:key" select="@id"/>
			<xsl:apply-templates select="@* | node()" />
		</xsl:copy>
	</xsl:template>
	
</xsl:stylesheet>
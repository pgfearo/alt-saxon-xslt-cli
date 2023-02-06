# Extended Saxon XSLT Command-Line Interface

- Extends `net.sf.saxon.Transform` Command line interface
- For `xsl:message` output prevent escaping of:
  - XML character entities like `<`
  - Unicode characters - such as ANSI escape codes used for colorised output
- Built using Maven
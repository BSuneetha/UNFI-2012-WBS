<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" >
<xsl:output method="text" omit-xml-declaration="yes" indent="no"/>
<xsl:template match="/">Senario Name,Test Name,Browser Name,Os Name,Test Status,Test Data Row,Link to Screen Shot,Error Log,Test Type
<xsl:for-each select="//test-case">
<xsl:if test="@methodname != ''">
<xsl:value-of select=
   "concat('WhiteTest',',',@methodname,',','Desktop',',','WIN10',',',@result,',','NA',',','NA',',','NA',',','Regression','&#xA;')"/>
</xsl:if>
</xsl:for-each>
</xsl:template>
</xsl:stylesheet>
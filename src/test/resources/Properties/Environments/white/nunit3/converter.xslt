<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" >
<xsl:output method="text" omit-xml-declaration="yes" indent="no"/>
<xsl:template match="/">Senario Name,Test Name,Browser Name,Os Name,Test Status,Test Data Row,Link to Screen Shot,Error Log,Type
<xsl:for-each select="/*/*/*/*/*/*/*/*">
<xsl:value-of select=
   "concat('iFusion',',',@methodname,',','NA',',','NA',',',@result,',','NA',',','NA',',','NA',',','Regression','&#xA;')"/>
</xsl:for-each>
</xsl:template>
</xsl:stylesheet>
<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/1999/xhtml">
<!--<xsl:output method="html" encoding="utf-8" omit-xml-declaration="no" doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" indent="yes" />-->

<xsl:param name="ja">ja_JP</xsl:param>
<xsl:param name="tr">en</xsl:param>

<xsl:template match="/">
<xsl:apply-templates />
</xsl:template>

<xsl:template match="lesson">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ja">
<head>
<title>
<xsl:value-of select="title" />
</title>
</head>
<body>

<p><font size="4"><b><u>Key Forms:</u></b></font></p>

<p><font size="3">The following examples show the key forms to be practised in this lesson.</font></p>

<xsl:apply-templates select="example" />

<p><font size="3">
The sentence you create in this lesson should all be similar to the forms shown above.
The grammar required to form these sentences is covered in the text below.
</font></p>

<hr />

<xsl:copy-of select="grammar_points" />

</body>
</html>
</xsl:template>

<xsl:template match="content">
<xsl:if test="@lang=$ja or @lang=$tr">
<xsl:value-of select="." />
</xsl:if>
</xsl:template>

<xsl:template match="example">
<table align="center" border="1">
<xsl:apply-templates select="section" />
</table>
</xsl:template>

<xsl:template match="section">
<tr bgcolor="#aaaaff">
<th><xsl:number /></th>
<th colspan="2" align="left"><xsl:value-of select="title" /></th>
</tr>
<xsl:apply-templates select="sentence" />
</xsl:template>

<xsl:template match="sentence">
<xsl:apply-templates select="item" />
</xsl:template>

<xsl:template match="item">
<tr>
<td></td>
<td><xsl:apply-templates select="japanese/content" /></td>
<td><xsl:apply-templates select="translated/content" /></td>
</tr>
</xsl:template>

</xsl:stylesheet>

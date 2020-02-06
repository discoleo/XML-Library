<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="3.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:a="file:///C:/Leo%20Mada/%20_TODO_%20/Authors.Schema.xsd"
	xmlns:p="file:///C:/Leo%20Mada/%20_TODO_%20/Publishers.Schema.xsd" >

<xsl:param name="skipPublishers" as="xs:boolean" select="true()"></xsl:param>

<xsl:template match="/">
	<html>
	<style>
		tr.inst{
			background-color: #9acd32;
		}
		tr.h{
			background-color: #288AD0;
		}
		tr.h2{
			background-color: #28C0D0;
		}
		td.name{
			color: blue;
			font-weight: bold;
			padding: 10px;
		}
		td.univ{
			color: #202890;
			font-weight: bold;
			padding: 10px;
		}
		span.title{
			color:blue;
			font-weight: bold;
		}
	</style>
	
	<!-- START -->
	<body>
    <h2>Library</h2>
	<p>Created by Leo</p>
	
	
	<h3>Publishers</h3>
	
	<xsl:if test="not($skipPublishers)">
		<!-- Only Publishers -->
		<xsl:apply-templates select="/BibManagement/p:Publishers" />
	</xsl:if>
	
	<xsl:apply-templates select="/BibManagement/p:Publishers" />
	
	<h3>Authors</h3>
	<!-- Authors by Authors -->
	<xsl:apply-templates select="/BibManagement/a:Authors" />
	
	<p></p>
	<!-- Authors by Domain -->
	<xsl:call-template name="AuthorsByDomain" />
	
	<p></p>
	
	<h3>Books</h3>
	<!-- Authors by Authors -->
	<xsl:apply-templates select="/BibManagement/Books" />
	
	<p></p>
	
	</body>
	</html>
</xsl:template>

<!-- Publishers -->

<!-- Publishers: not sorted -->
<!-- TODO: use named templates -->
<xsl:template match="/BibManagement/p:Publishers">
	<table border="1">
		<tr class="inst">
			<th>ID</th>
			<th>Publisher</th>
			<th>Address</th>
		</tr>
		
		<xsl:apply-templates select="./p:Publisher" />
	</table>
</xsl:template>

<!-- Publishers: sorted -->
<xsl:template match="/BibManagement/p:Publishers">
	<table border="1">
		<tr class="inst">
			<th>Nr</th>
			<th>ID</th>
			<th>Publisher</th>
			<th>Address</th>
		</tr>
		
		<xsl:for-each select="p:Publisher">
			<xsl:sort select="."/>
			<xsl:call-template name="templPublisher">
				<xsl:with-param name="position" select="position()"/>
			</xsl:call-template>
		</xsl:for-each>
		
	</table>
</xsl:template>

<xsl:template match="p:Publisher">
	<tr>
		<!-- TODO: id vs position() -->
		<td><xsl:value-of select="@idPub" /></td>
		<td><xsl:value-of select="./Name" /></td>
		<td><xsl:value-of select="./Address" /></td>
	</tr>
</xsl:template>

<xsl:template name="templPublisher">
	<xsl:param name="position"/>
	<tr>
		<td><xsl:value-of select="$position" /></td>
		<td><xsl:value-of select="@idPub" /></td>
		<td><xsl:value-of select="./Name" /></td>
		<td><xsl:value-of select="./Address" /></td>
	</tr>
</xsl:template>


<!-- Authors -->

<!-- Authors by Author: ALL Domains, ALL Books -->
<xsl:template match="/BibManagement/a:Authors">
	<table border="1">
		<tr class="h">
			<th>ID</th>
			<th>Author</th>
			<th>Given Name</th>
			<th>Book</th>
		</tr>
		
		<xsl:apply-templates select="./a:Author" />
	</table>
</xsl:template>

<!-- Author's Details -->
<xsl:template match="/BibManagement/a:Authors/a:Author">
	<xsl:variable name="idAuthor" select="@idAuthor"/>
	<xsl:variable name="nameAut" select="./Name"/>
	<xsl:variable name="gvNameAut" select="./GivenName"/>
			
	<tr>
		<!-- Author -->
		<td><xsl:value-of select="$idAuthor" /></td>
		<td class="name"><xsl:value-of select="$nameAut" /></td>
		<td><xsl:value-of select="$gvNameAut" /></td>
		
		<td>
			<!-- ALL Books: concatenated -->
			<xsl:call-template name="templBooksOfAuthor">
					<xsl:with-param name="id" select="position()"/>
					<xsl:with-param name="idAuthor" select="$idAuthor"/>
			</xsl:call-template>
		</td>
	</tr>
</xsl:template>

<!-- helper -->

<!-- ALL Books for an Author -->
<xsl:template name="templBooksOfAuthor">
	<xsl:param name="id"/>
	<xsl:param name="idAuthor"/>
	
	<xsl:variable name="varBook" select="/BibManagement/Books/Book[./Authors/Author/@idAuthorRef=$idAuthor]"/>
	
	<xsl:for-each select="$varBook">
		<xsl:variable name="idDomain" select="Domain/@idDomainRef"/>
		<xsl:variable name="varDomain" select="/BibManagement/Domains/Domain[@idDomain = $idDomain]"/>
		
		<p><span class="title"><xsl:value-of select="concat($id, '.', position(), '.) ')" /></span>
		<xsl:value-of select="Title" />
		<br/><xsl:value-of select="$varDomain" />
		<br/><xsl:value-of select="Date/Year" /></p>
	</xsl:for-each>
</xsl:template>

<!-- currently not used -->
<xsl:template name="templBooksByAuthor">
	<xsl:param name="idAuthor"/>
	
	<xsl:variable name="varBook" select="/BibManagement/Books/Books[./Authors/Author/@idAuthorRef=$idAuthor]"/>
	<p>
		<xsl:for-each select="$varBook">
			<xsl:value-of select="position()" />.) 
			<xsl:value-of select="Title" />
			<br/>
		</xsl:for-each>
	</p>
</xsl:template>



<!-- Authors: by Domain: TODO -->
<xsl:template name="AuthorsByDomain">
	<table border="1">
		<tr class="h">
			<th>ID</th>
			<th colspan="5">Domain</th>
		</tr><tr class="h2">
			<!-- Authors -->
			<th>ID Author</th>
			<th>Author</th>
			<th>Given Name</th>
			<th>Title</th>
			<th>Year</th>
			<th>Rating</th>
		</tr>
		
		<xsl:for-each select="/BibManagement/Domains/Domain">
			<xsl:sort select="."/>
			<xsl:variable name="idDomain" select="@idDomain" />
			<!-- All Books in the Domain -->
			<xsl:variable name="nodeBooksByDomain" select="/BibManagement/Books/Book[./Domain/@idDomainRef=$idDomain]"/>
			
			<tr>
				<td class="univ"><xsl:value-of select="$idDomain" /></td>
				<td class="univ" colspan="5"><xsl:value-of select="./Name" /></td>
			</tr>
			
			<xsl:call-template name="templAuthorByDomain">
				<xsl:with-param name="idDomain" select="$idDomain"/>
				<xsl:with-param name="nodeBooksByDomain" select="$nodeBooksByDomain"/>
			</xsl:call-template>
		</xsl:for-each>
	</table>
</xsl:template>


<xsl:template name="templAuthorByDomain">
	<xsl:param name="idDomain"/>
	<xsl:param name="nodeBooksByDomain"/>
	
	<xsl:for-each select="/BibManagement/a:Authors/a:Author">
			<xsl:variable name="idAuthor" select="@idAuthor"/>
			<xsl:variable name="nameAut" select="Name"/>
			<xsl:variable name="gvNameAut" select="GivenName"/>
			<!-- Selects Authors who wrote one of the books -->
			<xsl:variable name="nodeBooksByAuthor"
				select="$nodeBooksByDomain[./Authors/Author/@idAuthorRef = $idAuthor]" />
				
				<xsl:for-each select="$nodeBooksByAuthor">
				<tr>
					<!-- <td></td><td></td><td></td><td></td> -->
					<td><xsl:value-of select="concat($idDomain, '.', $idAuthor)"/></td>
					<td><xsl:value-of select="$nameAut"/></td>
					<td><xsl:value-of select="$gvNameAut"/></td>
					<!-- Books -->
					<td><xsl:value-of select="./Title"/></td>
					<td><xsl:value-of select="./Date/Year"/></td>
					<td><xsl:value-of select="Ratings/Rating" /></td>
				</tr>
				</xsl:for-each>
			
		</xsl:for-each>
</xsl:template>


<!-- Books -->

<!-- ALL Books -->
<xsl:template match="/BibManagement/Books">
	<table border="1">
		<tr class="h">
			<th>ID</th>
			<th>Authors</th>
			<th>Book</th>
			<th>Year</th>
			<th>Domain</th>
			<th>Rating</th>
			<th>Comments</th>
		</tr>
		
		<xsl:apply-templates select="./Book" />
	</table>
</xsl:template>

<xsl:template match="/BibManagement/Books/Book">
	<xsl:variable name="idAuthors" select="./Authors/Author/@idAuthorRef"/>
	<xsl:variable name="idDomain" select="Domain/@idDomainRef"/>
	<xsl:variable name="varDomain" select="/BibManagement/Domains/Domain[@idDomain = $idDomain]"/>
	
	<tr>
		<td><xsl:value-of select="position()" /></td>
		<td>
			<xsl:for-each select="$idAuthors">
				<xsl:variable name="idAuthor" select="." />
				<xsl:variable name="varAuthors" select="/BibManagement/a:Authors/a:Author[@idAuthor = $idAuthor]"/>
				<span class="title"><xsl:value-of select="position()" />.) </span>
				<xsl:value-of select="$varAuthors/Name" />,
				<xsl:value-of select="$varAuthors/GivenName" /><br/>
			</xsl:for-each>
		</td>
		<td><xsl:value-of select="Title" /></td>
		<td><xsl:value-of select="Date/Year" /></td>
		<td><xsl:value-of select="$varDomain" /></td>
		<td><xsl:value-of select="Ratings/Rating" /></td>
		<td><xsl:value-of select="Ratings/Comment" /></td>
	</tr>
</xsl:template>

</xsl:stylesheet>
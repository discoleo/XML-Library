/*
 *    XML Virtual Library
 *    
 *    XML Project
 *    Team: Leo Mada, LB, CB
 *    2020-01
 */

package gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.dom4j.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import data.BookObj;
import data.AuthorObj;
import data.GetMapsINTF;
import data.PublisherObj;
import dom.Controller;
import dom.Parser;
import dom.Updater;
// import sax.BibSaxHandler;
// import valid.XmlValidator;


public class MainGui {
	
	// ++++++++++ version 1.0-pre-alfa +++++++++++
	
	public static final String sPath = "C:\\Users\\...\\XML\\Library";
	public static final String sPath2 = "file://C:/Users/.../XML/Library";
	private final String sXmlFile = "\\Library.xml";
	// do NOT override original
	private final String sXmlUpdateFile = "\\Library.update.xml";
	// Schema: TODO
	private final String sSchemaFile = "\\Library.Schema.xsd";
	
	final Parser parserDom = new Parser();
	final Controller controller = new Controller(parserDom);
	
	// +++ IO +++
	public File GetFile() {
		return new File(sPath + sXmlFile);
	}
	public File GetUpdateFile() {
		return new File(sPath + sXmlUpdateFile);
	}
	public File GetSchema() {
		return new File(sPath + sSchemaFile);
	}
	
	// Print Articles
	public void Print(final Vector<BookObj> vBooks) {
		for(final BookObj article : vBooks) {
			System.out.println(article.toString());
			// System.out.println("\n");
		}
	}
	
	// +++ Find in XML +++
	
	public void FindBooks() {
		// test finding Books
		Vector<BookObj> vBooks = FindBooksByTitle("science");
		// print
		System.out.println("\nBooks found: " + vBooks.size());
		this.Print(vBooks);
		
		vBooks = FindBooksByAuthor("dreistein");
		// print
		System.out.println("\nBooks found: " + vBooks.size());
		this.Print(vBooks);
		
		vBooks = FindBooksByRegexTitle("\\p{L}{13}");
		// print
		System.out.println("\nBooks found: " + vBooks.size());
		this.Print(vBooks);
		
		vBooks = FindBooksByMinAuthors(3);
		// print
		System.out.println("\nBooks found: " + vBooks.size());
		this.Print(vBooks);
		
		vBooks = FindBooksByDomain("Loop Theory");
		// print
		System.out.println("\nBooks found: " + vBooks.size());
		this.Print(vBooks);
		
		vBooks = FindBooksByYear(2020);
		// print
		System.out.println("\nBooks found: " + vBooks.size());
		this.Print(vBooks);
	}
	public Vector<BookObj> FindBooksByTitle(final String sTitle) {
		return controller.FindBooksByTitle(sTitle);
	}
	public Vector<BookObj> FindBooksByRegexTitle(final String sRegexTitle) {
		return controller.FindBooksByRegexTitle(sRegexTitle);
	}
	public Vector<BookObj> FindBooksByAuthor(final String sAuthor) {
		return controller.FindBooksByAuthor(sAuthor);
	}
	public Vector<BookObj> FindBooksByMinAuthors(final int minCount) {
		return controller.FindBooksByMinAuthors(minCount);
	}
	public Vector<BookObj> FindBooksByDomain(final String sDomain) {
		return controller.FindBooksByDomain(sDomain);
	}
	public Vector<BookObj> FindBooksByYear(final int iYear) {
		return controller.FindBooksByYear(iYear);
	}
	
	// +++ Parse XML +++
	public void Process() {
		this.Process(this.GetFile());
	}
	public void Process(final String sFile) {
		this.Process(new File(sFile));
	}
	public void Process(final File file) {
		try {
			final InputSource inSrc = new InputSource(new FileInputStream(file));
			
			// final BibSaxHandler handler = new BibSaxHandler();
			// final XMLReader reader = this.GetXMLSaxReader(handler);
			// if(reader != null) {
			//	reader.parse(inSrc);
			//	this.Print(handler.GetArticles());
			// }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}// catch (SAXException e) {
		//	e.printStackTrace();
		// }
		System.out.println("Finished parsing!");
	}
	
	/*
	protected XMLReader GetXMLSaxReader(final BibSaxHandler handler) {
		final SAXParserFactory spf = SAXParserFactory.newInstance();
		final SAXParser sp;
		final XMLReader reader;
		
		try {
			sp = spf.newSAXParser();
			reader = sp.getXMLReader();
			reader.setContentHandler(handler);
			return reader;
		} catch(SAXException | ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		}
	} /* */
	
	// +++ Parse DOM +++
	public Document ParseDom() {
		return this.ParseDom(this.GetFile());
	}
	public Document ParseDom(final File file) {
		final Document doc = parserDom.Parse(file);
		//
		this.Print(parserDom.GetBooks());
		return doc;
	}
	
	// +++ Update XML +++
	// TODO: implement more update functions
	public boolean Update(final Document doc, final GetMapsINTF maps) {
		return this.Update(doc, maps, this.GetUpdateFile());
	}
	public boolean Update(final Document doc, final GetMapsINTF maps, final File fileXML) {
		final Updater updater = new Updater(doc, maps);
		// Test
		final BookObj book = new BookObj();
		book.idBook = 1;
		book.sTitle = "_TEST_";
		
		// Test: Publisher
		final PublisherObj publisher = new PublisherObj();
		publisher.sName = "Gottlob Farms Publishing";
		publisher.sAddress = "Branchial Arch 42";
		updater.AddPublisher(book, publisher);

		// Test: Add Author
		AuthorObj testAuthor = new AuthorObj("His");
		testAuthor.sGivenName = "Noncredere";
		book.AddAuthor(testAuthor);
		testAuthor = new AuthorObj("Purkinje");
		testAuthor.sGivenName = "Dubiosus";
		book.AddAuthor(testAuthor);
		updater.AddAuthor(book);
		
		// Test: Add Book
		book.publisher = publisher;
		book.iYear = 2020;
		updater.AddBook(book);
		
		// TODO: many more updates;
		updater.Write(fileXML);
		return false; // TODO
	}
	
	// +++ Validate XML +++
	public boolean Validate() {
		return this.Validate(this.GetFile(), this.GetSchema());
	}
	public boolean Validate(final File fileXML, final File fileSchema) {
		// TODO
		// final XmlValidator validator = new XmlValidator();
		// return validator.Validate(fileXML, fileSchema);
		return true;
	}
	
	// ++++++++++ MAIN +++++++++

	public static void main(String[] args) {
		final MainGui gui = new MainGui();
		
		// SAX Parser
		// gui.Process();
		
		// TODO: Validation
		if(gui.Validate()) {
			System.out.println("Xml was validated");
		} else {
			System.out.println("Xml was NOT validated!");
		}
		
		// DOM Parser
		final Document doc = gui.ParseDom();
		
		// Find some Books
		gui.FindBooks();
		
		// Update xml file
		gui.Update(doc, gui.parserDom);
	}

}

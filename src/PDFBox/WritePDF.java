package PDFBox;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.pdfbox.io.RandomAccessBuffer;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdfwriter.COSWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.junit.Test;

public class WritePDF {

	@Test
	public void writeread() throws IOException{

		ByteArrayOutputStream out = new ByteArrayOutputStream();

	    //PDFドキュメントを作成
	    PDDocument document = new PDDocument();
	    //ページを追加
	    document.addPage(new PDPage());

	    try(COSWriter writer = new COSWriter(out)){
	    	writer.write(document);
	    }

	    try(RandomAccessRead read = new RandomAccessBuffer(out.toByteArray())){

		     PDFParser pdfParser = new PDFParser(read);
		     pdfParser.parse(); // 分析
		     PDDocument pdf = pdfParser.getPDDocument();
	    }
	}

}

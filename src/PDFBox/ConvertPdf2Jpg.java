package PDFBox;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

public class ConvertPdf2Jpg {
	public static void main(String[] args) throws FileNotFoundException, IOException  {

		File pdf = new File(args[0]);
		File jpg = new File(args[0] + ".jpg");

		try (InputStream input = new FileInputStream(pdf)){
	        try (PDDocument doc = PDDocument.load(input)) {

	            PDFRenderer pdfRenderer = new PDFRenderer(doc);
	            BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 300, ImageType.RGB);

	            try(OutputStream output = new FileOutputStream(jpg)) {
	            	ImageIO.write(bim, "JPEG", output);
	            }
	        }
		}
	}
}

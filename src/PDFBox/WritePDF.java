package PDFBox;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.pdfbox.io.RandomAccessBuffer;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdfwriter.COSWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.Ignore;
import org.junit.Test;

public class WritePDF {

	@Test
	public void writeRead() throws IOException {

		byte[] buffer;

		// PDFの書き込み
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

			// PDFドキュメントを作成
			PDDocument document = new PDDocument();

			// ページを追加
			document.addPage(new PDPage());

			try (COSWriter writer = new COSWriter(out)) {
				writer.write(document);
			}

			buffer = out.toByteArray();
		}

		// PDFの読み込み
		try (RandomAccessRead read = new RandomAccessBuffer(buffer)) {

			PDFParser pdfParser = new PDFParser(read);
			pdfParser.parse(); // 分析
			PDDocument pdf = pdfParser.getPDDocument();
		}
	}

	@Test @Ignore
	public void write1行() throws IOException {

		try (PDDocument doc = new PDDocument()) {

			// 新しいページを追加
			PDPage page = new PDPage();
			doc.addPage(page);

			// フォントファイルを読み込み
			PDFont font = PDType0Font.load(doc, getClass().getResourceAsStream("TanukiMagic.ttf"));

			// 出力用のストリームを開く
			try (PDPageContentStream contents = new PDPageContentStream(doc, page)) {

				// テキスト出力開始
				contents.beginText();

				// 使用するフォントに読み込んだフォントを指定。
				// 第二引数はフォントサイズ
				contents.setFont(font, 46);

				// テキストの出力位置を指定。
				// 左下がX:0 Y:0の座標となる
				contents.newLineAtOffset(100, 500);
				contents.showText("日本語出力123");
				// テキスト出力終了
				contents.endText();

			}
			// pdfファイルを出力
			doc.save("sample.pdf");
		}
	}

	@Test @Ignore
	public void write2行() throws IOException {

		try (PDDocument doc = new PDDocument()) {

			// 新しいページを追加
			PDPage page = new PDPage();
			doc.addPage(page);

			// フォントファイルを読み込み
			PDFont font = PDType0Font.load(doc, getClass().getResourceAsStream("TanukiMagic.ttf"));

			// 出力用のストリームを開く
			try (PDPageContentStream contents = new PDPageContentStream(doc, page)) {

			      // 行間隔を指定
		        contents.setLeading(46 * 1.2);

		        // テキスト出力開始
		        contents.beginText();

		        // 使用するフォントに読み込んだフォントを指定。
		        // 第二引数はフォントサイズ
		        contents.setFont(font, 46);

		        // テキストの出力位置を指定。
		        // 左下がX:0 Y:0の座標となる
		        contents.newLineAtOffset(100, 500);
		        contents.showText("日本語出力123");

		        // 改行
		        contents.newLine();
		        contents.showText("二行目を出力！");

				// テキスト出力終了
				contents.endText();
			}

			// pdfファイルを出力
			doc.save("sample2.pdf");
		}
	}

	@Test
	public void read() throws IOException{
		// PDFの読み込み
		try (RandomAccessRead read = new RandomAccessBuffer(getClass().getResourceAsStream("PDFBox.pdf"))) {

			PDFParser pdfParser = new PDFParser(read);
			pdfParser.parse(); // 分析
			try(PDDocument pdf = pdfParser.getPDDocument()){

				PDDocumentCatalog docCatalog = pdf.getDocumentCatalog();
				PDAcroForm acroForm = docCatalog.getAcroForm();

			    //テキスト分解クラス生成
			    PDFTextStripper stripper = new PDFTextStripper();
			    //抽出＆出力実施
			    String text = stripper.getText(pdf);
			    assertThat(text.contains("PDFBox"), is(true));
			}
		}
	}

}

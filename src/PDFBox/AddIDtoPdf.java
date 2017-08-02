package PDFBox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.util.Matrix;

public class AddIDtoPdf {

	/** 文字列の追加場所*/
	public enum Position{
		 /** 左上*/ TL {
			@Override
			public float getY(PDRectangle pageSize, float msgSize) {
				return pageSize.getWidth() - msgSize;
			}
		}
		,/** 右上*/ TR {
			@Override
			public float getX(PDRectangle pageSize, float msgSize) {
				return pageSize.getHeight() - msgSize;
			}
			@Override
			public float getY(PDRectangle pageSize, float msgSize) {
				return pageSize.getWidth() - msgSize;
			}
		}
		,/** 左下*/ BL
		,/** 右下(デフォルト)*/BR {
			@Override
			public float getX(PDRectangle pageSize, float msgSize) {
				return pageSize.getHeight() - msgSize;
			}
		};

		/**
		 * PDF内のX座標を取得する
		 * @param pageSize ページサイズの情報
		 * @param msgSize 追記するテキストの幅
		 * @return X座標
		 */
		public float getX(PDRectangle pageSize, float msgSize){return 0;};

		/**
		 * PDF内のY座標を取得する
		 * @param pageSize ページサイズの情報
		 * @param msgSize 追記するテキストの高さ
		 * @return Y座標
		 */
		public float getY(PDRectangle pageSize, float msgSize){return 0;};
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		File from = new File(args[0]);
		File to = new File(args[0] + ".pdf");

		String message = args[1];
		Position position = args.length != 3 ? Position.BR : Position.valueOf(args[2]);

		try (InputStream input = new FileInputStream(from)){
	        try (PDDocument doc = PDDocument.load(input)) {
	        	PDPage page = doc.getPage(0);
				// フォントファイルを読み込み
	        	PDFont font = PDType1Font.HELVETICA_BOLD;
	        	int fontSize = 20;

				// 出力用のストリームを開く
				try (PDPageContentStream contents = new PDPageContentStream(doc, page, true, true)) {

			        // テキスト出力開始
			        contents.beginText();
			        contents.setFont(font, 20);

			        // 出力を回転
			        PDRectangle pageSize = page.getMediaBox();
			        contents.transform(new Matrix(0, 1, -1, 0, pageSize.getWidth(), 0));

			        // テキストの出力位置を指定。
			        // 左下がX:0 Y:0の座標となる
			        float msgSize = getFontTextWidth(font, message, fontSize);
			        contents.newLineAtOffset(position.getX(pageSize, msgSize), position.getY(pageSize, fontSize));
			        contents.setNonStrokingColor(200,200,200);
			        contents.showText(message);
			        contents.endText();
				}

				doc.save(to);
	        }
		}
	}

	private static float getFontTextWidth(PDFont font, String text, float fontSize) throws IOException {
        return font.getStringWidth(text) / 1000 * fontSize;
    }
}

package dynamiccompile;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;

import org.junit.Test;

/**
 * 参考:http://waman.hatenablog.com/entry/20110310/1299721161
 */
public class CompileTest {


	/**
	 * ソース一つにつき、インスタンスを一つ作成する
	 */
	private class DynamicJavaSourceCodeObject extends SimpleJavaFileObject {

	    private final String code;

	    public DynamicJavaSourceCodeObject(String name, String code) {
	        super(URI.create("string:///" + name.replaceAll(".", "/") + Kind.SOURCE.extension), Kind.SOURCE);
	        this.code = code ;
	    }

	    @Override
	    public CharSequence getCharContent(boolean ignoreEncodingErrors)throws IOException {
	        return code;
	    }

	}


	@Test
	public void compile(){

		// コンパイルしたクラスファイルの出力先のルート
	    String DEST_DIR = "bin";

	    // クラスのパッケージ名。テストクラスと同じパッケージ名にした。
	    String PACKAGE_NAME = getClass().getPackage().getName();

	    // クラスの名称
	    String CLASS_NAME = "DynamicCompileTest";

	    // パッケージ名 + クラス名
	    String QUALIFIED_CLASS_NAME = PACKAGE_NAME +"."+ CLASS_NAME;

	    // 作成するクラスのソース。変数valueにsetter,getterメソッドがあるだけ。
	    String SOURCE =
	            "package "+ PACKAGE_NAME +";" +
	            "class "+ CLASS_NAME +" {" +
	            "private int value; " +
	            "public int getValue(){ return value;} " +
	            "public void setValue(int value){this.value = value;} " +
	            "}";

	    /**********  動的コンパイル            *********/
	    // (1) コンパイルに渡すソースコードやオプションを作成
        // コンパイル後にエラーが無いか調べる（ステップ(4)参照）
        DiagnosticCollector<JavaFileObject> diags = new DiagnosticCollector<JavaFileObject>();
        // コンパイル・オプション
        List<String> options = Arrays.asList("-d", DEST_DIR);

        // Java のソースコード
        List<? extends JavaFileObject> src = Arrays.asList(
            new DynamicJavaSourceCodeObject(QUALIFIED_CLASS_NAME, SOURCE)
        );

        // (2) コンパイラ・オブジェクト取得
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        // (3) コンパイル実行
        JavaCompiler.CompilationTask compilerTask = compiler.getTask(null, null, diags, options, null, src);

        // (4) コンパイル・エラーのチェック
        if (!compilerTask.call()){
            for (Diagnostic diag : diags.getDiagnostics()){
                System.out.format("Error on line %d in %s", diag.getLineNumber(), diag);
            }
        }

        // bin\dynamiccompile に DynamicCompileTest.class が生成されている
        // 既存のクラスがあった場合、更新される

	    /**********  リフレクションでインスタンス生成、メソッド実施            *********/
        Object obj = null;

        try {
        	// インスタンスの生成
			obj = Class.forName(QUALIFIED_CLASS_NAME).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}

        try {
        	// setValue(1)で値を設定
			Method setMethod =  obj.getClass().getMethod("setValue", int.class);
			setMethod.invoke(obj, 1);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}


        Object result = null;

        try {
        	// getValue()で値を取得
			Method getMethod =  obj.getClass().getMethod("getValue");
			result = getMethod.invoke(obj);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}

        assertThat(result, is(1));

	}

}

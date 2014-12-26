package annotationaop;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

public class AnnotationTest {

	/**
	 * アノテーションの定義
	 * ・RetentionPolicy.RUNTIME: 実行時に有効になる
	 * ・ElementType.METHOD:メソッドに使用するアノテーション
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public @interface MethodAnnotation{
	}

	/**
	 * アノテーション付きメソッド
	 */
	@MethodAnnotation
	public void method(){
	}

	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		Class<AnnotationTest> clazz = AnnotationTest.class;
		Method method = clazz.getMethod("method");
		Annotation[] annotations = method.getAnnotations();
		for (Annotation annotation: annotations){
			System.out.println(annotation.toString());
		}
	}

}

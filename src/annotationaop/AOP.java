package annotationaop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import annotationaop.AnnotationTest.MethodAnnotation;

public class AOP {

	/**
	 * インターフェース
	 */
	interface Command{
		/**
		 * アノテーション、引数ありのメソッド
		 */
		@MethodAnnotation
		void execute(String message);

		/**
		 * アノテーション、戻り値ありのメソッド
		 */
		@MethodAnnotation
		boolean booleanMethod();

		/**
		 * アノテーションなし
		 */
		void voidMethod();
	}

	/**
	 * インターフェースの実装クラス
	 */
	class CommandImpl implements Command{

		@Override
		public void execute(String message) {
			System.out.println("CommandImpl.execute呼び出し");
		}

		@Override
		@MethodAnnotation
		public boolean booleanMethod() {
			System.out.println("CommandImpl.booleanMethod呼び出し");
			return false;
		}

		/*
		 * 実装側にアノテーションをつけてみるが、効果がないことを確認する
		 */
		@Override
		@MethodAnnotation
		public void voidMethod() {
			System.out.println("CommandImpl.voidMethod呼び出し");
		}
	}

	/**
	 * インターセプター。処理を横取りして、処理を追加するクラス
	 */
	class Intercepter implements InvocationHandler{

		private Object target;
		public Intercepter(Object target) {
			this.target = target;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {

			// MethodAnnotationアノテーションがついていないメソッドは、追加処理せず、終了。
			if (! Arrays.stream(method.getAnnotations()).anyMatch(p-> p instanceof MethodAnnotation)){
				return method.invoke(target, args);
			}

			// 以下、MethodAnnotationアノテーションがついているメソッドは、処理を追加する
			System.out.println("AOP処理開始");

			// 引数の一覧を作成
			StringBuilder sb = new StringBuilder();
			if(args != null){
				Arrays.stream(args).forEach(arg -> sb.append(arg.toString()).append(" "));
			}

			// メソッド名と引数を出力
			System.out.println("呼び出しメソッド:" + method.getName() + " 引数:" + sb.toString());

			// 実際に実施し、結果を保存する
			Object result = method.invoke(target, args);

			// 結果がnullでなければ、結果を出力する
			if (result != null){
				System.out.println("結果:" + result.toString());
			}

			// AOPの処理が完了したことを出力。空行も出力。
			System.out.println("AOP処理完了");
			System.out.println();

			// 実施した結果を返し、通常の実施を同じにする
			return result;

		}
	}

	public static void main(String[] args) {
		AOP aop = new AOP();
		Command commandImpl = aop.new CommandImpl();

		Command proxy = (Command)Proxy.newProxyInstance(
				Command.class.getClassLoader()
				//, new Class[]{Command.class}
				, commandImpl.getClass().getInterfaces()
				, aop.new Intercepter(commandImpl));

		// 各メソッドを実施
		proxy.execute("引数テスト");
		proxy.booleanMethod();
		proxy.voidMethod();
	}

}

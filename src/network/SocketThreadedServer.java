package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketThreadedServer {
	public static void main(String args[]) throws IOException {
		int port = Integer.parseInt(args[0]); // サーバ側の待受ポート番号

		try (ServerSocket server = new ServerSocket(port)) {

			ExecutorService executor = Executors.newFixedThreadPool(10);

			for(;;){

				Socket socket = server.accept();
				executor.execute(new Result(socket.getInputStream(), socket.getOutputStream()));
			}
		}
	}

	public static class Result implements Runnable{

		private InputStream input;

		private OutputStream output;

		public Result(InputStream input, OutputStream output) {
			this.input = input;
			this.output = output;
		}

		@Override
		public void run() {

			try (// クライアントからのデータ受信する設定
				DataInputStream inputData = new DataInputStream(input);
				ObjectInputStream input = new ObjectInputStream(inputData);

				// クライアントへのデータ送信を実施する設定
				DataOutputStream outputData = new DataOutputStream(output)){

					// クライアントに返信するデータを作成する
					SocketData receivedData = (SocketData)input.readObject();
					Thread.sleep(receivedData.getAge() * 100);

					outputData.writeBytes("Success: " + receivedData.getName() + "(" + receivedData.getAge() + ")");

			} catch(IOException | ClassNotFoundException | InterruptedException e){
				e.printStackTrace();
			}
		}

	}
}

package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

	public static void main(String args[]) throws IOException {
		int port = Integer.parseInt(args[0]); // サーバ側の待受ポート番号

		try (ServerSocket server = new ServerSocket(port)) {

			for(;;){

				// クライアントからの通信開始要求が来るまで待機
				Socket socket = server.accept();

				// 以下、クライアントからの要求発生後

				try (// クライアントからのデータ受信する設定
					InputStream inputStream = socket.getInputStream();
					DataInputStream inputData = new DataInputStream(inputStream);
					ObjectInputStream input = new ObjectInputStream(inputData);

					// クライアントへのデータ送信を実施する設定
					OutputStream outputStream = socket.getOutputStream();
					DataOutputStream outputData = new DataOutputStream(outputStream)){

						// クライアントからのデータを取得して、結果を返す
						Runnable data = (Runnable) input.readObject();
						new Thread(data).start();

						// クライアントに返信するデータを作成する
						SocketData receivedData = (SocketData) data;
						outputData.writeBytes("Success: " + receivedData.getName() + "(" + receivedData.getAge() + ")");

				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

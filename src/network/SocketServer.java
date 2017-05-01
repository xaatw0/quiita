package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

	public static void main(String args[]) {
		int port = Integer.parseInt(args[0]); // サーバ側の待受ポート番号

		try (ServerSocket server = new ServerSocket(port)) {

			while (true) {
				Socket socket = server.accept(); // クライアントからの通信開始要求が来るまで待機

				// 以下、クライアントからの要求発生後
				try (InputStream inputStream = socket.getInputStream();
						DataInputStream inputData = new DataInputStream(inputStream)) {

					int req = inputData.readInt();

					try (OutputStream outputStream = socket.getOutputStream();
							DataOutputStream outputData = new DataOutputStream(outputStream)) {
						outputData.writeInt(req * req);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

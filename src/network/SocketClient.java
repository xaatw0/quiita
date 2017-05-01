package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClient {
	public static void main(String args[]) throws UnknownHostException, IOException {

		String server = args[0];
		int port = Integer.parseInt(args[1]); // サーバー側のポート番号

		// サーバーに数値を送信
		try (Socket socket = new Socket(server, port);
				OutputStream os = socket.getOutputStream();
				DataOutputStream dos = new DataOutputStream(os)) {

			dos.writeInt(Integer.parseInt(args[2]));

			// 演算結果を受信
			try(InputStream is = socket.getInputStream();
					DataInputStream dis = new DataInputStream(is)){
				int res = dis.readInt();
				System.out.println(res);
			}
		}
	}
}

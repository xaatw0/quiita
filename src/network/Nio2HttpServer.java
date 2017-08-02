package network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Nio2HttpServer {

    private static final int DEFAULT_PORT = 9000;
    private static final int TIMEOUT = 10;

    private final AsynchronousServerSocketChannel server;

    public Nio2HttpServer() throws IOException {
        server = AsynchronousServerSocketChannel.open();
        server.setOption(StandardSocketOptions.SO_REUSEADDR, true);
        server.bind(new InetSocketAddress(DEFAULT_PORT));
    }

    public void start() throws Exception {
        while(true) {
            Future<AsynchronousSocketChannel> acceptFuture = server.accept();
            handleRequest(acceptFuture.get());
        }
    }

    private void handleRequest(AsynchronousSocketChannel channel) {

        try (AsynchronousSocketChannel acceptedChannel = channel) {

            ByteBuffer buff = ByteBuffer.allocateDirect(8192);

            acceptedChannel.read(buff).get(TIMEOUT, TimeUnit.SECONDS);

            printRequest(buff);
            mkResponse(buff);
            buff.flip();

            acceptedChannel.write(buff).get(TIMEOUT, TimeUnit.SECONDS);

        } catch (InterruptedException | ExecutionException | TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }

    private void printRequest(ByteBuffer buff){
        buff.flip();
        byte[] bytes = new byte[buff.limit()];
        buff.get(bytes);
        buff.compact();
        System.out.println(new String(bytes));
    }

    private void mkResponse(ByteBuffer buff) {
        buff.clear();
        String text = "HTTP/1.1 200 OK\n\n"
            + new Date() + " (" + Thread.currentThread() + ")";
        buff.put(text.getBytes());
    }

    public static void main(String[] args) throws Exception {
        new Nio2HttpServer().start();
    }
}
package org.rublin.tcp;

import org.slf4j.Logger;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import static org.rublin.Main.*;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Send message {@link String} to TCP socket {@link java.net.Socket}
 *
 * @author Ruslan Sheremet
 * @see String
 * @see Socket
 * @since 1.0
 */
public class TcpSender implements Sender {

    private static final Logger LOG = getLogger(TcpSender.class);

    private static final TcpSender sender = new TcpSender();

    public static TcpSender getSender() {
        return sender;
    }

    @Override
    public void sendMessage(String message) {
        String server = properties.getProperty("server.ip");
        int port = Integer.valueOf(properties.getProperty("server.port"));
        try (Socket socket = new Socket(server, port);
        OutputStream out = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(out)) {
            dos.write(message.getBytes());
            LOG.info("Message {} send successful", message);
        } catch (IOException e) {
            LOG.error("Message send failed. Check network connection to server {} and port {}. Error: {}", server, port, e.getMessage());
        }
    }
}

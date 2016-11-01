package org.rublin.tcp;

import org.slf4j.Logger;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import static org.rublin.Main.*;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * ???
 *
 * @author Ruslan Sheremet
 * @see
 * @since 1.0
 */
public class TcpSender implements Sender {

    private static final Logger LOG = getLogger(TcpSender.class);

    private static final TcpSender sender = new TcpSender();
//    private Socket socket;

    public static TcpSender getSender() {
        return sender;
    }

    @Override
    public void sendMessage(String message) {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
        OutputStream out = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(out)) {
            dos.write(message.getBytes());
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }
}

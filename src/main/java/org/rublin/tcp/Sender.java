package org.rublin.tcp;

/**
 * Send message {@link String} to socket {@link java.net.Socket}
 *
 * @author Ruslan Sheremet
 * @see String
 * @see java.net.Socket
 * @since 1.0
 */
public interface Sender {
    void sendMessage(String message);
}

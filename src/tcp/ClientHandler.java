package tcp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.util.Date;

// Thread
public class ClientHandler implements Runnable {
    private final Socket sock;

    public ClientHandler(Socket s) {
        sock = s;
    }

    // Entry point for the thread (similar to main())
    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();

        try {
            // Get input stream - best to open in reverse order 
            // (ie. if client open output first, server should open input first)
            InputStream is = sock.getInputStream();
            Reader reader = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(reader);

            // Get output stream
            OutputStream os = sock.getOutputStream();
            Writer writer = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(writer);

            // Read result from client
            String fromClient = br.readLine();
            System.out.printf(">>> [%s] CLIENT: %s\n", threadName, fromClient);

            // Process data
            fromClient = (new Date()).toString() + " " + fromClient.toUpperCase();

            // Write result back to client
            bw.write(fromClient);
            bw.newLine();
            bw.flush();
            os.flush();

            // Close streams & socket
            os.close();
            is.close();
            sock.close();
        } catch (IOException ioEx) {
            // Exception handler
            ioEx.printStackTrace();
        }
    } 
}

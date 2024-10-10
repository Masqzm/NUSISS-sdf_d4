package tcp;

import java.net.*;
import java.io.Reader;
import java.io.Writer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class ClientMain {
    public static void main(String[] args) throws IOException {
        // Set default port to 3000
        int port = 3000;
        if(args.length > 0)
            port = Integer.parseInt(args[0]);

        // Create connection to server 
        // Connect to port on server
        // localhost - 127.0.0.1
        System.out.println("Connecting to server...");
        Socket sock = new Socket("localhost", port);

        System.out.println("Connected!");

        // Write msg to server
        Console cons = System.console();
        String msg = cons.readLine("input: ");;

        // Get output stream
        OutputStream os = sock.getOutputStream();
        Writer writer = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(writer);

        // Get input stream
        InputStream is = sock.getInputStream();
        Reader reader = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(reader);

        bw.write(msg);
        bw.newLine();
        bw.flush();
        os.flush();

        // Read result from server
        String fromServer = br.readLine();

        System.out.printf(">>> SERVER: %s\n", fromServer);

        // Close streams & socket
        os.close();
        is.close();
        sock.close();
    }
}

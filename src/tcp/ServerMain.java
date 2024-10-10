package tcp;

import java.net.*;
import java.util.Date;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

public class ServerMain {
    public static void main(String[] args) throws IOException {
        // Set default port to 3000
        int port = 3000;
        if(args.length > 0)
            port = Integer.parseInt(args[0]);

        // Create server port (TCP) - ONLY NEED TO CREATE 1!
        ServerSocket server = new ServerSocket(port);

        while(true) 
        {
            System.out.printf("Waiting for connection on port %d\n", port);
            
            // Wait for incoming connection (listens to port for connection)
            Socket sock = server.accept();  

            System.out.println("Received new connection.");

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
            System.out.printf(">>> CLIENT: %s\n", fromClient);

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
        }
    }
}

package tcp;

import java.net.*;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadedServerMain {
    public static void main(String[] args) throws IOException {
        // Create a thread pool
        ExecutorService thrPool = Executors.newFixedThreadPool(2);

        String threadName = Thread.currentThread().getName();

        // Set default port to 3000
        int port = 3000;
        if(args.length > 0)
            port = Integer.parseInt(args[0]);

        // Create server port (TCP) - ONLY NEED TO CREATE 1!
        ServerSocket server = new ServerSocket(port);

        while(true) 
        {
            System.out.printf("[%s] Waiting for connection on port %d\n", threadName, port);

            // Waiting for incoming connection (listens to port for connection)
            Socket sock = server.accept();  

            System.out.println("Received new connection.");

            // Create a "worker" to handle work
            ClientHandler cHandler = new ClientHandler(sock);
            // Pass the work to the worker
            thrPool.submit(cHandler);
        }
    }
}

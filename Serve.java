import com.sun.net.httpserver.*;
import java.net.*;
import java.io.*;
import java.nio.file.*;

public class Serve {
  public static void main(String[] args) throws Exception {
    String dir = args.length > 0 ? args[0] : ".";
    HttpServer server = HttpServer.create(new InetSocketAddress(8765), 0);
    server.createContext("/", exchange -> {
      try {
        byte[] bytes = Files.readAllBytes(Paths.get(dir, "index.html"));
        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=utf-8");
        exchange.sendResponseHeaders(200, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
      } catch (Exception e) {
        exchange.sendResponseHeaders(500, 0);
        exchange.getResponseBody().close();
      }
    });
    server.start();
    System.out.println("Serving on http://localhost:8765");
  }
}

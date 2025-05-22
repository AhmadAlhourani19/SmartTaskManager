import server.HttpServer;

public class Main {
    public static void main(String[] args) {
        try {
            HttpServer server = new HttpServer(8080);
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package server;

import controller.TaskController;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class HttpServer {

    private final int port;

    public HttpServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("🚀 Server running at http://localhost:" + port);

        while (true) {
            Socket client = serverSocket.accept();
            handleClient(client);
        }
    }

    private void handleClient(Socket client) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()))
        ) {
            String requestLine = in.readLine();
            if (requestLine == null || requestLine.isEmpty()) return;

            String[] parts = requestLine.split(" ");
            String method = parts[0];
            String path = parts[1];

            // Skip headers
            String line;
            while (!(line = in.readLine()).isEmpty()) {}

            // Handle CORS preflight
            if (method.equals("OPTIONS")) {
                sendResponse(out, 200, "{}");
                return;
            }

            String response;

            if (method.equals("GET") && path.equals("/tasks")) {
                response = TaskController.handleGetAllTasks();
                sendResponse(out, 200, response);

            } else if (method.equals("POST") && path.startsWith("/tasks")) {
                Map<String, String> queryParams = getQueryParams(path);
                String title = queryParams.getOrDefault("title", "Untitled");
                String priority = queryParams.getOrDefault("priority", "LOW");
                String dueDate = queryParams.getOrDefault("dueDate", "none");
                response = TaskController.handleCreateTask(title, priority, dueDate);
                sendResponse(out, 201, response);

            } else if (method.equals("DELETE") && path.startsWith("/tasks")) {
                Map<String, String> queryParams = getQueryParams(path);
                int id = Integer.parseInt(queryParams.getOrDefault("id", "-1"));
                boolean deleted = TaskController.handleDeleteTask(id);
                if (deleted) {
                    sendResponse(out, 200, "{\"success\":true}");
                } else {
                    sendResponse(out, 404, "{\"error\":\"Task not found\"}");
                }

            } else {
                sendResponse(out, 404, "{\"error\":\"Not found\"}");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendResponse(BufferedWriter out, int statusCode, String body) throws IOException {
        out.write("HTTP/1.1 " + statusCode + " OK\r\n");
        out.write("Content-Type: application/json\r\n");
        out.write("Access-Control-Allow-Origin: *\r\n");
        out.write("Access-Control-Allow-Methods: GET, POST, DELETE, OPTIONS\r\n");
        out.write("Access-Control-Allow-Headers: Content-Type\r\n");
        out.write("Content-Length: " + body.length() + "\r\n");
        out.write("\r\n");
        out.write(body);
        out.flush();
    }

    private Map<String, String> getQueryParams(String path) {
        Map<String, String> params = new HashMap<>();
        int idx = path.indexOf("?");
        if (idx >= 0 && idx < path.length() - 1) {
            String query = path.substring(idx + 1);
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                String[] kv = pair.split("=");
                if (kv.length == 2) {
                    params.put(URLDecoder.decode(kv[0], java.nio.charset.StandardCharsets.UTF_8),
                               URLDecoder.decode(kv[1], java.nio.charset.StandardCharsets.UTF_8));
                }
            }
        }
        return params;
    }
}

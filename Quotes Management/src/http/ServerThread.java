package http;

import app.RequestHandler;
import http.response.Response;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.StringTokenizer;

public class ServerThread implements Runnable{

    private Socket client;
    private BufferedReader in;
    private PrintWriter out;

    public ServerThread(Socket client) {
        this.client = client;

        try {
            //inicijalizacija ulaznog toka
            in = new BufferedReader(
                    new InputStreamReader(
                            client.getInputStream()));

            //inicijalizacija izlaznog sistema
            out = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    client.getOutputStream())), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try{
            String requestLine = in.readLine();

            StringTokenizer stringTokenizer = new StringTokenizer(requestLine);

            String method = stringTokenizer.nextToken();
            String path = stringTokenizer.nextToken();
            int contentLength =0;

            System.out.println("\nHTTP ZAHTEV KLIJENTA:\n");
            do{
                System.out.println(requestLine);
                requestLine = in.readLine();
                if(requestLine.contains("Content-Length"))
                {
                    contentLength = Integer.parseInt(requestLine.split(": ")[1]);
                }
            }while(!requestLine.trim().equals(""));

            HashMap<String, String> postParams = new HashMap<>();

            if(method.equals(HttpMethod.POST.toString()))
            {
                char[] buff = new char[contentLength];
                in.read(buff);
                String params = new String(buff);
                String[] split = params.split("&");
                for(String s: split)
                {
                    postParams.put(s.split("=")[0],
                            URLDecoder.decode(s.split("=")[1], StandardCharsets.UTF_8.name()));
                }
            }

            Request request = new Request(HttpMethod.valueOf(method), path,postParams);

            RequestHandler requestHandler = new RequestHandler();
            Response response = requestHandler.handle(request);

            System.out.println("\nHTTP odgovor:\n");
            System.out.println(response.getResponseString());

            out.println(response.getResponseString());

            in.close();
            out.close();
            client.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package helper.http;

import com.google.gson.Gson;
import helper.app.QuoteStorage;
import http.HttpMethod;

import java.io.*;
import java.net.Socket;
import java.util.Random;
import java.util.StringTokenizer;

public class ServerThread implements Runnable{

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Gson gson = new Gson();

    public ServerThread(Socket socket) {
        try {
            this.socket = socket;

            this.in = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()
                    )
            );

            this.out = new PrintWriter(
                    new OutputStreamWriter(
                            socket.getOutputStream()
                    ), true
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try {
            String requestLine = in.readLine();

            StringTokenizer tokenizer = new StringTokenizer(requestLine);

            String method = tokenizer.nextToken();
            String path = tokenizer.nextToken();
            System.out.println(path);

            System.out.println("\nHTTP ZAHTEV KLIJENTA:\n");

            do {
                System.out.println(requestLine);
                requestLine = in.readLine();
            }while(!requestLine.trim().equals(""));

            Random random = new Random();

            //bira random citat dana iz storage-a
            if(method.equals(HttpMethod.GET.toString()) && path.equals("/"))
            {
                StringBuilder quoteOfTheDay = new StringBuilder();
                int index = random.nextInt(7);
                quoteOfTheDay.append("HTTP/1.1 200 OK\r\nContent-Type: application/json\r\n\r\n");

                quoteOfTheDay.append(gson.toJson(QuoteStorage.getInstance().getQuoteList().get(index)));

                System.out.println(quoteOfTheDay.toString());

                out.println(quoteOfTheDay.toString());
            }else{
                StringBuilder quoteResponse = new StringBuilder();
                quoteResponse.append("HTTP/1.1 404 Not Found\r\nContent-Type: text/html\r\n\r\n");
                out.println(quoteResponse.toString());
            }

            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

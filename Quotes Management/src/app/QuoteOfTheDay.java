package app;

import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;

public class QuoteOfTheDay {

    private Gson gson = new Gson();
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    //povlaci helper
    public Quote getQuoteOfTheDay()
    {
        try {
            socket = new Socket("localhost", 8114);
            in = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()
                    )
            );
            out = new PrintWriter(
                    new OutputStreamWriter(
                            socket.getOutputStream()
                    ), true
            );

            StringBuilder quoteRequest =new StringBuilder();
            quoteRequest.append("GET / HTTP/1.1\r\nHost: localhost:8114\r\n\r\n");

            out.println(quoteRequest.toString());

            String requestLine = in.readLine();

            do{
                System.out.println(requestLine);
                requestLine = in.readLine();
            }while(!requestLine.trim().equals(""));

            String quoteJson = in.readLine();
            System.out.println(quoteJson);

            //deserijalizacija
            Quote quoteOfTheDay = gson.fromJson(quoteJson, Quote.class);

            socket.close();
            in.close();
            out.close();

            return quoteOfTheDay;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}

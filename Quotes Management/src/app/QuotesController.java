package app;

import http.Request;
import http.response.HtmlResponse;
import http.response.RedirectResponse;
import http.response.Response;

import java.util.Iterator;

public class QuotesController extends Controller{

    public QuotesController(Request request) {
        super(request);
    }

    @Override
    public Response doGet() {
        Quote quoteOfTheDay = (new QuoteOfTheDay()).getQuoteOfTheDay();
        StringBuilder body = new StringBuilder();
        body.append(
                "<form action=\"/save-quote\" method=\"POST\">" +
                        "<label>Author: </label><input name=\"author\" type=\"text\"><br>" +
                        "<label>Quote: </label><input name=\"quote\" type=\"text\"><br><br>" +
                        "<button>Save Quote</button>" +
                        "</form>" +
                        "<br>" +
                        "<h2>Quote of the day:</h2>\n" +
                        "<p><b>" + quoteOfTheDay.getAuthor() + "</b> - \"" + quoteOfTheDay.getText() + "\"</p>\n" +
                        "<h2>Saved quotes:</h2>"
        );
        Iterator iterator = QuotesStorage.getInstance().getQuotes().iterator();
        while(iterator.hasNext())
        {
            Quote curr = (Quote)iterator.next();
            body.append("<p><b>" + curr.getAuthor() + "</b> - \"" + curr.getText() + "\"</p>\n");
        }
        return new HtmlResponse(body.toString());
    }

    @Override
    public Response doPost() {
        QuotesStorage.getInstance().getQuotes().add(
                new Quote(
                        request.getPostParams().get("author"),
                        request.getPostParams().get("quote")
                )
        );
        return new RedirectResponse();
    }
}

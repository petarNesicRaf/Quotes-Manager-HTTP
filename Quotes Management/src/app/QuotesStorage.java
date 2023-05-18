package app;

import java.util.concurrent.CopyOnWriteArrayList;

public class QuotesStorage {

    private static QuotesStorage instance = null;
    private CopyOnWriteArrayList<Quote> quotes = new CopyOnWriteArrayList<>();

    private QuotesStorage(){ }

    //lista korisnikovih citata
    public static QuotesStorage getInstance() {
        if(instance == null){
            instance = new QuotesStorage();
        }
        return instance;
    }

    public CopyOnWriteArrayList<Quote> getQuotes() {
        return quotes;
    }

}

package helper.app;

import app.Quote;

import java.util.ArrayList;
import java.util.List;

public class QuoteStorage {

    private List<Quote> quoteList;
    private static QuoteStorage instance = null;

    public static QuoteStorage getInstance()
    {
        if(instance == null)
        {
            instance = new QuoteStorage();
        }
        return instance;
    }

    private QuoteStorage()
    {
        quoteList = new ArrayList<>();
        initQuotes();
    }

    private void initQuotes()
    {
        quoteList.add(new Quote("Albert Einstein", "Imagination is more important than knowledge."));
        quoteList.add(new Quote("Maya Angelou", "I\'ve learned that people will forget what you said, people will forget what you did, but people will never forget how you made them feel."));
        quoteList.add(new Quote("William Shakespeare", "All the world\'s a stage, and all the men and women merely players."));
        quoteList.add(new Quote("Rust Cole", "Death created time to grow things it can kill"));
        quoteList.add(new Quote("Nelson Mandela", "Education is the most powerful weapon which you can use to change the world."));
        quoteList.add(new Quote("Jane Austen",  "It isn\'t what we say or think that defines us, but what we do."));
        quoteList.add(new Quote("Martin Luther King Jr",  "Darkness cannot drive out darkness, only light can do that. Hate cannot drive out hate, only love can do that."));
    }

    public List<Quote> getQuoteList() {
        return quoteList;
    }
}

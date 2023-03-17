package ngordnet.proj2b_testing;

import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.browser.NgordnetServer;
import ngordnet.main.HistoryHandler;
import ngordnet.main.HistoryTextHandler;
import ngordnet.main.HyponymsHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.wordnet.WordnetGraph;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {

        WordnetGraph wng = new WordnetGraph(synsetFile, hyponymFile);

        return new HyponymsHandler(wng);
    }
}

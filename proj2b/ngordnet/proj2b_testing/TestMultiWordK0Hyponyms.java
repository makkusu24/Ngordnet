package ngordnet.proj2b_testing;

import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/** Tests the case where the list of words is length greater than 1, but k is still zero. */
public class TestMultiWordK0Hyponyms {
    // this case doesn't use the NGrams dataset at all, so the choice of files is irrelevant
    public static final String WORDS_FILE = "data/ngrams/very_short.csv";
    public static final String STAFF_WORDS_FILE = "data/ngrams/top_14377_words.csv";
    public static final String TOTAL_COUNTS_FILE = "data/ngrams/total_counts.csv";
    public static final String SMALL_SYNSET_FILE = "data/wordnet/synsets16.txt";
    public static final String SMALL_HYPONYM_FILE = "data/wordnet/hyponyms16.txt";
    public static final String LARGE_SYNSET_FILE = "data/wordnet/synsets.txt";
    public static final String LARGE_HYPONYM_FILE = "data/wordnet/hyponyms.txt";

    /** This is an example from the spec.*/
    @Test
    public void testOccurrenceAndChangeK0() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        List<String> words = List.of("occurrence", "change");

        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 0);
        String actual = studentHandler.handle(nq);
        String expected = "[alteration, change, increase, jump, leap, modification, saltation, transition]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void largeMultiWordTest() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, LARGE_SYNSET_FILE, LARGE_HYPONYM_FILE);
        List<String> words = List.of("event", "link");

        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 0);
        String actual = studentHandler.handle(nq);
        String expected = "[articulation, bridge, concatenation, connection, connexion, contact"
                + ", inter-group_communication, interconnection, junction, juncture, liaison, link, osculation, tie]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void specMultiWordTest() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, LARGE_SYNSET_FILE, LARGE_HYPONYM_FILE);
        List<String> words = List.of("video", "recording");

        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 0);
        String actual = studentHandler.handle(nq);
        String expected = "[video, video_recording, videocassette, videotape]";
        assertThat(actual).isEqualTo(expected);

        List<String> words2 = List.of("pastry", "tart");
        NgordnetQuery nq2 = new NgordnetQuery(words2, 0, 0, 0);
        String actual2 = studentHandler.handle(nq2);
        String expected2 = "[apple_tart, lobster_tart, quiche, quiche_Lorraine, tart, tartlet]";
        assertThat(actual2).isEqualTo(expected2);
    }

    @Test
    public void k0Tests() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                STAFF_WORDS_FILE, TOTAL_COUNTS_FILE, LARGE_SYNSET_FILE, LARGE_HYPONYM_FILE);
        List<String> words = List.of("food", "cake");
        List<String> words2 = List.of("dance");

        NgordnetQuery nq = new NgordnetQuery(words, 1950, 1990, 5);
        String actual = studentHandler.handle(nq);
        String expected = "[cake, cookie, kiss, snap, wafer]";
        assertThat(actual).isEqualTo(expected);

        NgordnetQuery nq2 = new NgordnetQuery(words2, 2000, 2020, 10);
        String actual2 = studentHandler.handle(nq2);
        String expected2 = "[ball, dance, dancing, extension, formal, phrase, strip, swing, twist, variation]";
        assertThat(actual2).isEqualTo(expected2);
    }

    @Test
    public void k0ComprehensiveTest1() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                STAFF_WORDS_FILE, TOTAL_COUNTS_FILE, LARGE_SYNSET_FILE, LARGE_HYPONYM_FILE);
        List<String> words = List.of("dominion");

        NgordnetQuery nq = new NgordnetQuery(words, 1470, 2019, 8);
        String actual = studentHandler.handle(nq);
        String expected = "[addition, city, community, country, development, land, rule, state]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void k0ComprehensiveTest2() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                STAFF_WORDS_FILE, TOTAL_COUNTS_FILE, LARGE_SYNSET_FILE, LARGE_HYPONYM_FILE);
        List<String> words = List.of("stuff", "dung");

        NgordnetQuery nq = new NgordnetQuery(words, 1470, 2019, 4);
        String actual = studentHandler.handle(nq);
        String expected = "[chip]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void k0ComprehensiveTest3() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                STAFF_WORDS_FILE, TOTAL_COUNTS_FILE, LARGE_SYNSET_FILE, LARGE_HYPONYM_FILE);
        List<String> words = List.of("unit", "roofing_tile");

        NgordnetQuery nq = new NgordnetQuery(words, 1470, 2019, 9);
        String actual = studentHandler.handle(nq);
        String expected = "[tile]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void k0ComprehensiveTest4() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                STAFF_WORDS_FILE, TOTAL_COUNTS_FILE, LARGE_SYNSET_FILE, LARGE_HYPONYM_FILE);
        List<String> words = List.of("measure", "rest_period");

        NgordnetQuery nq = new NgordnetQuery(words, 1920, 1980, 8);
        String actual = studentHandler.handle(nq);
        String expected = "[breath, relief, rest]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void k0ComprehensiveTest5() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                STAFF_WORDS_FILE, TOTAL_COUNTS_FILE, LARGE_SYNSET_FILE, LARGE_HYPONYM_FILE);
        List<String> words = List.of("entity");

        NgordnetQuery nq = new NgordnetQuery(words, 1470, 2019, 8);
        String actual = studentHandler.handle(nq);
        String expected = "[are, at, can, have, he, in, one, will]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void k0NotInWordsFile() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                STAFF_WORDS_FILE, TOTAL_COUNTS_FILE, LARGE_SYNSET_FILE, LARGE_HYPONYM_FILE);
        List<String> words = List.of("cardinal");

        NgordnetQuery nq = new NgordnetQuery(words, 1920, 1980, 8);
        String actual = studentHandler.handle(nq);
        String expected = "[cardinal, frequency]";
        assertThat(actual).isEqualTo(expected);
    }

}

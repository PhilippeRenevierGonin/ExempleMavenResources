package dico;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DicoTest {

    private Dico dico;

    @BeforeEach
    void setUp() {
        dico = new Dico();
    }

    /**
     * Plusieurs cas de tests, avec des mots existants, une lettre, un mot vide, des suites de lettres
     */
    @Test
    void anagram() {
        String [] entrées = { "voiture", "a", "maison", "allusion", "", "abcde", "edcba", "nbvcxwqsdfghjklmzeartyuiop"};
        String [] sortieAttendues = { "eiortuv", "a", "aimnos", "aillnosu", "", "abcde", "abcde", "abcdefghijklmnopqrstuvwxyz"};
        for(int i = 0; i < entrées.length && i < sortieAttendues.length; i++) {
            String anagram = dico.anagram(entrées[i]);
            assertEquals(sortieAttendues[i], anagram);
        }
    }

    /**
     * Si on passe null en paramètre, une exception est levée
     */
    @Test
    void anagramThrowsNull() {
        assertThrows(NullPointerException.class, () -> dico.anagram(null));
    }

    @Test
    void nomalize() {
    }
}
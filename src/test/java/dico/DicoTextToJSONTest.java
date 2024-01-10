package dico;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DicoTextToJSONTest {
    @Mock
    Dico mockedDico;

    DicoTextToJSON testedReader;

    String testFileName = "test.txt";


    @TempDir
    File temp;



    @BeforeEach
    void setUp() {
        testedReader = new DicoTextToJSON();
        testedReader.setDico(mockedDico);

        File testFile = new File(temp, testFileName);
        if (testFile.exists()) testFile.delete();
    }

    @AfterEach
    void tearDown() {
        System.out.println("tearDown");
        File testFile = new File(temp, testFileName);
        System.out.println(testFile.getAbsolutePath());
        if (testFile.exists()) {
            assertTrue(testFile.delete());
        }
    }

    /**
     * Test en chargeant un fichier avec une ligne
     */
    @Test
    void loadUnFichierAvecUneLigne() {
        when(mockedDico.addWord("a")).thenReturn(true);
        try {
            testedReader.load(getClass().getResourceAsStream("uneLigne.txt"));
        } catch (IOException e) {
            fail();
        }
        verify(mockedDico, times(1)).addWord("a");
    }


    /**
     * Test en chargeant un fichier "normal", toutes les lignes sont acceptées.
     */
    @Test
    void loadUnFichierFr() {
        when(mockedDico.addWord(anyString())).thenReturn(true);
        try {
            testedReader.load(getClass().getResourceAsStream("/liste_francais.txt"));
        } catch (IOException e) {
            fail();
        }
        verify(mockedDico, times(22740)).addWord(anyString()); // toutes les lignes sont prises
    }

    /**
     * Test en chargeant un fichier vide
     */
    @Test
    void loadUnFichierAvecAucuneLigne() {
        try {
            testedReader.load(getClass().getResourceAsStream("empty.txt"));
        } catch (IOException e) {
            fail();
        }
        verify(mockedDico, never()).addWord(anyString());
    }


    /**
     * test quand il y a une IOException (un nouveau dico est créé)
     */
    @Test
    void loadError() throws IOException {
        // on crée un fichier temporaire
        InputStream is = null;
        File testFile = new File(temp, testFileName);

        try  {
            is = getClass().getResourceAsStream("uneLigne.txt");
            Files.copy(is, Path.of(testFile.toURI()));
        } catch (IOException e) {
            fail();
        }

        RandomAccessFile toLockFile = new RandomAccessFile(testFile, "rw");
        FileLock fileControls = toLockFile.getChannel().lock();
        System.out.println(fileControls);

        // on lance le chargement
        InputStream fis = new FileInputStream(testFile);
        testedReader.load(fis);

        // il y a dû y avoir une exception, testReader devrait avoir un nouveau dico
        assertNotEquals(mockedDico, testedReader.getDico());


        // on relâche le fichier pour pouvoir l'effacer dans le @AfterEach
        fileControls.close(); // à commenter pour faire planter le test (car le fichier ne sera pas effaçable)
        toLockFile.close(); // à commenter pour faire planter le test (car le fichier ne sera pas effaçable)
        fis.close();
        is.close();
    }
}
package dico;

import org.apache.commons.math3.util.CombinatoricsUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class ListAnagram {


    private final Dico dico;

    public ListAnagram(Dico dico) {
        this.dico = dico;
    }



    public final static void main(String [] args) throws IOException {
        DicoTextToJSON importText = new DicoTextToJSON();
        importText.load(ListAnagram.class.getResourceAsStream("/liste_francais.txt"));

        ListAnagram la =  new ListAnagram(importText.getDico());
        System.out.println("*************** portes (uniquement avec toutes les lettres) ***************");
        System.out.println((la.getAnamgrams("portes")));
        System.out.println("*************** portes ***************");
        System.out.println((la.getAllAnamgrams("portes")));
        System.out.println("*************** zzzzzwsdfgabsent ***************");
        System.out.println((la.getAllAnamgrams("zzzzzwsdfgabsent")));
        System.out.println("*************** zzzzzwsdfg ***************");
        System.out.println((la.getAllAnamgrams("zzzzzwsdfg")));
        System.out.println("*************** a ***************");
        System.out.println((la.getAllAnamgrams("a")));
        System.out.println("*************** y ***************");
        System.out.println((la.getAllAnamgrams("y")));
        System.out.println("*************** i ***************");
        System.out.println((la.getAllAnamgrams("i")));
        System.out.println("*************** test ***************");
        System.out.println((la.getAllAnamgrams("test")));
        System.out.println("*************** sett ***************");
        System.out.println((la.getAllAnamgrams("sett")));
        System.out.println("*************** toutes ***************");
        HashSet<String> toutesAnagram = la.getAllAnamgrams("toutes");
        StringBuilder sb = new StringBuilder();
        toutesAnagram.stream().sorted().forEach(s -> {
            sb.append(s+", ");
        });
        if (sb.length() > 2) {
            System.out.println(sb.substring(0, sb.length()-2));
            // sb.replace(sb.length()-2, sb.length()-1, "");
            // System.out.println(sb);
        }

    }

    /**
     * pour récupérer la liste des anagrammes d'un mot
     * @param word
     * @return
     */
    private ArrayList<String> getAnamgrams(String word) {
        String key = dico.normalize(word.trim());
        key = dico.anagram(key);
        return dico.getDictionnary().get(key);
    }

    /**
     * pour récupérer la collection des mots que l'on peut former avec toutes ou une partie des lettres d'un mot
     * chaque anagramme doit être unique dans la connection
     * @param word le mot dont il faut trouver tous les mots possibles à former avec une partie de ses lettres
     * @return la collection de mots
     */
    private HashSet<String> getAllAnamgrams(String word) {
        HashSet<String> possibleAnagrams = new HashSet<>();
        String key = dico.normalize(word.trim());
        key = dico.anagram(key);

        ArrayList<int[]> subwordIndexes = new ArrayList<>();

        for(int i = 1; i <= key.length(); i++) {
            Iterator<int[]> list = CombinatoricsUtils.combinationsIterator(key.length(), i);
            while (list.hasNext()) {
                final int[] combination = list.next();
                subwordIndexes.add(combination);
            }
        }

        for(int i = 0; i < subwordIndexes.size(); i++) {
            int[] indexes = subwordIndexes.get(i);
            String anagram = "";
            for(int j = 0; j < indexes.length; j++) anagram += key.charAt(indexes[j]);

            ArrayList<String> adds = dico.getDictionnary().get(anagram);
            if ((adds != null) && (adds.size() > 0) )  possibleAnagrams.addAll(adds);

        }

        return possibleAnagrams;
    }
}

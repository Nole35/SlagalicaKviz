package com.example.slagalica.Controllers;

import com.example.slagalica.HelperClasses.Association;
import com.example.slagalica.HelperClasses.KorakPoKorak;
import com.example.slagalica.HelperClasses.ResourceHelper;

import java.util.ArrayList;
import java.util.Random;

public class KorakPoKorakKontorler {

    private static KorakPoKorakKontorler instance;

    private ArrayList<KorakPoKorak> associations;

    private Random random;

    private KorakPoKorakKontorler() {
        associations = ResourceHelper.getInstance(null).getKorakPoKorak();
        random = ResourceHelper.getInstance(null).getRandomInstance();
    }

    public static KorakPoKorakKontorler getInstance() {
        if (instance == null) {
            instance = new KorakPoKorakKontorler();
        }
        return instance;
    }

//        public KorakPoKorak getAssociation(int index) {
//            if (index < associations.size()) {
//                return associations.get(index);
//            } else {
//                return associations.get(0);
//            }
//        }

    public KorakPoKorak getAssociation(int index) {
        if (associations.size() > 0) {
            if (index < associations.size()) {
                return associations.get(index);
            } else {
                return associations.get(0);
            }
        } else {
            return null; // or any appropriate value to indicate no associations
        }
    }
    //        public Integer getAssociationNumber() {
//            return Math.abs(random.nextInt() % associations.size());
//        }
    public Integer getAssociationNumber() {
        if (associations.size() > 0) {
            int randomNumber = random.nextInt(associations.size());
            return Math.abs(randomNumber);
        } else {
            return -1; // or any appropriate value to indicate no associations
        }
    }
    private String transform(String word) {
        ArrayList<Character> transformedWord = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            char currentChar = word.charAt(i);
            switch (currentChar) {
                case 'a':
                    transformedWord.add('а');
                    break;
                case 'b':
                    transformedWord.add('б');
                    break;
                case 'v':
                    transformedWord.add('в');
                    break;
                case 'g':
                    transformedWord.add('г');
                    break;
                case 'd':
                    transformedWord.add('д');
                    break;
                case 'đ':
                    transformedWord.add('ђ');
                    break;
                case 'e':
                    transformedWord.add('е');
                    break;
                case 'ž':
                    if (!transformedWord.isEmpty() && transformedWord.get(transformedWord.size() - 1) == 'д') {
                        transformedWord.remove(transformedWord.size() - 1);
                        transformedWord.add('џ');
                    } else {
                        transformedWord.add('ж');
                    }
                    break;
                case 'z':
                    if (!transformedWord.isEmpty() && transformedWord.get(transformedWord.size() - 1) == 'д') {
                        transformedWord.remove(transformedWord.size() - 1);
                        transformedWord.add('џ');
                    } else {
                        transformedWord.add('з');
                    }
                    break;
                case 'i':
                    transformedWord.add('и');
                    break;
                case 'j':
                    if (!transformedWord.isEmpty()) {
                        char lastChar = transformedWord.get(transformedWord.size() - 1);
                        if (lastChar == 'л') {
                            transformedWord.remove(transformedWord.size() - 1);
                            transformedWord.add('љ');
                        } else if (lastChar == 'н') {
                            transformedWord.remove(transformedWord.size() - 1);
                            transformedWord.add('њ');
                        } else if (lastChar == 'д') {
                            transformedWord.remove(transformedWord.size() - 1);
                            transformedWord.add('ђ');
                        } else {
                            transformedWord.add('ј');
                        }
                    } else {
                        transformedWord.add('ј');
                    }
                    break;
                case 'k':
                    transformedWord.add('к');
                    break;
                case 'l':
                    transformedWord.add('л');
                    break;
                case 'm':
                    transformedWord.add('м');
                    break;
                case 'n':
                    transformedWord.add('н');
                    break;
                case 'o':
                    transformedWord.add('о');
                    break;
                case 'p':
                    transformedWord.add('п');
                    break;
                case 'r':
                    transformedWord.add('р');
                    break;
                case 's':
                    transformedWord.add('с');
                    break;
                case 't':
                    transformedWord.add('т');
                    break;
                case 'ć':
                    transformedWord.add('ћ');
                    break;
                case 'u':
                    transformedWord.add('у');
                    break;
                case 'f':
                    transformedWord.add('ф');
                    break;
                case 'h':
                    transformedWord.add('х');
                    break;
                case 'c':
                    transformedWord.add('ц');
                    break;
                case 'č':
                    transformedWord.add('ч');
                    break;
                case 'š':
                    transformedWord.add('ш');
                    break;
                case ' ':
                    transformedWord.add(' ');
                    break;
            }
        }

        StringBuilder newWord = new StringBuilder();
        for (Character ch : transformedWord) {
            newWord.append(Character.toUpperCase(ch));
        }

        return newWord.toString();
    }

    public boolean matchWords(String word1, String word2, boolean Cyrilic) {
        String transformedWord = word2;
        if (!Cyrilic) {
            transformedWord = transform(word2);
        }

        if (Math.abs(word1.length() - transformedWord.length()) > 1) {
            return false;
        }

        if (word1.length() == transformedWord.length() && word1.length() < 4) {
            int numberOfTries = 0;
            int tryNumber = Math.min(word1.length(), transformedWord.length());
            int longerWord = Math.max(word1.length(), transformedWord.length());

            for (int i = 0; i < tryNumber; i++) {
                if (word1.charAt(i) == transformedWord.charAt(i)) {
                    numberOfTries++;
                } else if (transformedWord.charAt(i) == 'З') {
                    if (word1.charAt(i) == 'Ж' || word1.charAt(i) == 'З') {
                        numberOfTries++;
                    }
                } else if (transformedWord.charAt(i) == 'С') {
                    if (word1.charAt(i) == 'С' || word1.charAt(i) == 'Ш') {
                        numberOfTries++;
                    }
                } else if (transformedWord.charAt(i) == 'Ц') {
                    if (word1.charAt(i) == 'Ч' || word1.charAt(i) == 'Ћ' || word1.charAt(i) == 'Ц') {
                        numberOfTries++;
                    }
                }
            }

            return numberOfTries == longerWord;
        } else if (word1.length() >= 4) {
            int numberOfTries1 = 0;
            int numberOfTries2 = 0;
            int pointer1 = 0, pointer2 = 0;
            int numberOfMisses1 = 0;
            int numberOfMisses2 = 0;

            while (pointer2 != transformedWord.length() && pointer1 != word1.length()) {
                if (transformedWord.charAt(pointer2) == word1.charAt(pointer1)) {
                    numberOfTries1++;
                    pointer2++;
                    pointer1++;
                } else if (transformedWord.charAt(pointer2) == 'З') {
                    if (word1.charAt(pointer1) == 'Ж' || word1.charAt(pointer1) == 'З') {
                        pointer1++;
                        pointer2++;
                        numberOfTries1++;
                    } else {
                        numberOfMisses1++;
                        pointer1++;
                    }
                } else if (transformedWord.charAt(pointer2) == 'С') {
                    if (word1.charAt(pointer1) == 'С' || word1.charAt(pointer1) == 'Ш') {
                        pointer1++;
                        pointer2++;
                        numberOfTries1++;
                    } else {
                        numberOfMisses1++;
                        pointer1++;
                    }
                } else if (transformedWord.charAt(pointer2) == 'Ц') {
                    if (word1.charAt(pointer1) == 'Ч' || word1.charAt(pointer1) == 'Ћ' || word1.charAt(pointer1) == 'Ц') {
                        pointer1++;
                        pointer2++;
                        numberOfTries1++;
                    } else {
                        numberOfMisses1++;
                        pointer1++;
                    }
                } else {
                    numberOfMisses1++;
                    pointer1++;
                }
            }

            pointer1 = 0;
            pointer2 = 0;

            while (pointer1 != word1.length() && pointer2 != transformedWord.length()) {
                if (transformedWord.charAt(pointer2) == word1.charAt(pointer1)) {
                    numberOfTries2++;
                    pointer1++;
                    pointer2++;
                } else if (transformedWord.charAt(pointer2) == 'З') {
                    if (word1.charAt(pointer1) == 'Ж' || word1.charAt(pointer1) == 'З') {
                        pointer1++;
                        pointer2++;
                        numberOfTries2++;
                    } else {
                        numberOfMisses2++;
                        pointer2++;
                    }
                } else if (transformedWord.charAt(pointer2) == 'С') {
                    if (word1.charAt(pointer1) == 'С' || word1.charAt(pointer1) == 'Ш') {
                        pointer1++;
                        pointer2++;
                        numberOfTries2++;
                    } else {
                        numberOfMisses2++;
                        pointer2++;
                    }
                } else if (transformedWord.charAt(pointer2) == 'Ц') {
                    if (word1.charAt(pointer1) == 'Ч' || word1.charAt(pointer1) == 'Ћ' || word1.charAt(pointer1) == 'Ц') {
                        pointer1++;
                        pointer2++;
                        numberOfTries2++;
                    } else {
                        numberOfMisses2++;
                        pointer2++;
                    }
                } else {
                    numberOfMisses2++;
                    pointer2++;
                }
            }

            return (Math.abs(word1.length() - numberOfTries1) <= 1 && numberOfMisses1 <= 1) || (Math.abs(word1.length() - numberOfTries2)) <= 1 && numberOfMisses2 <= 1;
        }

        return false;
    }
}
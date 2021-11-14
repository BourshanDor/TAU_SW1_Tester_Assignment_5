package il.ac.tau.cs.sw1.ex5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BigramModelTest {
    public static final String TEST_FILENAME = "resources/hw5/test.txt";
    public static final String TEST_MODEL_DIR = "resources/hw5/test_model";
    public static final String ONE_WORD_FILENAME = "resources/hw5/one_word.txt";
    public static final String ONE_WORD_DIR = "resources/hw5/one_word_model";
    BigramModel test = new BigramModel();
    BigramModel one_word = new BigramModel();


    @Test
    void initModel() throws IOException {

        test.initModel(TEST_FILENAME);

        // check yourself:

  /*    System.out.println("mVocabulary:");
        System.out.println(Arrays.toString(test.mVocabulary));
        System.out.println("mBigramCounts: ");
        for (int i = 0; i < test.mBigramCounts.length; i ++){
            System.out.println(Arrays.toString(test.mBigramCounts[i]));
        }*/
        Assertions.assertAll("Checking buildVocabularyIndex & buildCountsArray",
                () -> assertEquals(31, test.mVocabulary.length),
                () -> assertEquals(31, test.mBigramCounts.length),
                () -> assertEquals(31, test.mBigramCounts[0].length),
                () -> assertEquals(3, test.mBigramCounts[0][1]),
                () -> assertEquals(3, test.mBigramCounts[1][2]),
                () -> assertEquals(0, test.mBigramCounts[0][27]),
                () -> assertEquals(20, test.mBigramCounts[0][0]),
                () -> assertEquals(3, test.mBigramCounts[4][5])
        );
    }
    @Test
    void saveModel() throws IOException {
        test.initModel(TEST_FILENAME);
        test.saveModel(TEST_MODEL_DIR);
        BufferedReader br = new BufferedReader(new FileReader(TEST_MODEL_DIR + ".voc"));
        BufferedReader br1 = new BufferedReader(new FileReader(TEST_MODEL_DIR + ".counts"));

        Assertions.assertAll("Checking right format",
                () -> assertEquals("31 words" , br.readLine()),
                () -> assertEquals("0,a", br.readLine()),
                () -> assertEquals("1,b", br.readLine()),
                () -> assertEquals("2,c", br.readLine()),
                () -> assertEquals("0,0:20", br1.readLine()),
                () -> assertEquals("0,1:3", br1.readLine()),
                () -> assertEquals("1,2:3", br1.readLine()),
                () -> assertEquals("2,3:3", br1.readLine())
        );
        br.close();
        br1.close();
    }
    /**
     * Run this After you ran "saveModel" !!
     * saveModel create files that this function assumes for existence
     */

    @Test
    void loadModel() throws IOException {
        test.initModel(TEST_FILENAME);
        BigramModel test1 = new BigramModel();
        test1.loadModel(TEST_MODEL_DIR);
        Assertions.assertAll("loadModel",
                () -> assertArrayEquals(test.mVocabulary,test1.mVocabulary ),
                () -> assertArrayEquals(test.mBigramCounts[0],test1.mBigramCounts[0]),
                () -> assertArrayEquals(test.mBigramCounts[1],test1.mBigramCounts[1]),
                () -> assertArrayEquals(test.mBigramCounts[2],test1.mBigramCounts[2]),
                () -> assertArrayEquals(test.mBigramCounts[3],test1.mBigramCounts[3]),
                () -> assertArrayEquals(test.mBigramCounts[4],test1.mBigramCounts[4]),
                () -> assertArrayEquals(test.mBigramCounts[5],test1.mBigramCounts[5]),
                () -> assertArrayEquals(test.mBigramCounts[6],test1.mBigramCounts[6]),
                () -> assertArrayEquals(test.mBigramCounts[7],test1.mBigramCounts[7]),
                () -> assertArrayEquals(test.mBigramCounts[8],test1.mBigramCounts[8]),
                () -> assertArrayEquals(test.mBigramCounts[9],test1.mBigramCounts[9]),
                () -> assertArrayEquals(test.mBigramCounts[10],test1.mBigramCounts[10]),
                () -> assertArrayEquals(test.mBigramCounts[11],test1.mBigramCounts[11])
        );

    }

    @Test
    void getWordIndex() throws IOException {
        test.initModel(TEST_FILENAME);

        Assertions.assertAll("getWordIndex",
                () -> assertEquals(30 , test.getWordIndex("a,")),
                () -> assertEquals(0 , test.getWordIndex("a")),
                () -> assertEquals(26 , test.getWordIndex("some_num")),
                () -> assertEquals(27 , test.getWordIndex("a%")),
                () -> assertEquals(-1 , test.getWordIndex("aa")),
                () -> assertEquals(-1 , test.getWordIndex("$")),
                () -> assertEquals(-1 , test.getWordIndex("bbb")),
                () -> assertEquals(-1 , test.getWordIndex("122"))

        );

    }

    @Test
    void getBigramCount() throws IOException {
        test.initModel(TEST_FILENAME);

        Assertions.assertAll("getBigramCount",
                () -> assertEquals(20 , test.getBigramCount("a" , "a")),
                () -> assertEquals(0 , test.getBigramCount("b" , "a")),
                () -> assertEquals(3 , test.getBigramCount("a" , "b")),
                () -> assertEquals(3 , test.getBigramCount("b" , "c")),
                () -> assertEquals(3 , test.getBigramCount("c" , "d")),
                () -> assertEquals(0 , test.getBigramCount("a" , "aa")),
                () -> assertEquals(0 , test.getBigramCount("xx" , "a")),
                () -> assertEquals(0 , test.getBigramCount("1" , "a")),
                () -> assertEquals(2 , test.getBigramCount("f" , "g"))
        );
    }

    @Test
    void getMostFrequentProceeding() throws IOException {
        test.initModel(TEST_FILENAME);

        Assertions.assertAll("getMostFrequentProceeding",
                () -> assertEquals("a" , test.getMostFrequentProceeding("a")),
                () -> assertEquals("h" , test.getMostFrequentProceeding("g")),
                () -> assertEquals(null , test.getMostFrequentProceeding("a,")),
                () -> assertEquals(null , test.getMostFrequentProceeding("$$$1a")),
                () -> assertEquals(null , test.getMostFrequentProceeding("1"))

        );
    }

    @Test
    void isLegalSentence() throws IOException {
        test.initModel(TEST_FILENAME);

        Assertions.assertAll("isLegalSentence",
                () -> assertEquals(false , test.isLegalSentence("x ax xb")),
                () -> assertEquals(true , test.isLegalSentence("a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a")),
                () -> assertEquals(true , test.isLegalSentence("a,")),
                () -> assertEquals(true , test.isLegalSentence("g i g h")),
                () -> assertEquals(true , test.isLegalSentence("some_num a")),
                () -> assertEquals(false , test.isLegalSentence("some_num b"))
        );

    }

    @Test
    void calcCosineSim() {

        int [] arr1 = {1,2,3};
        int [] arr2 = {3,2,1};
        int [] arr3 = {1,1,1};
        int [] arr4 = {0,0,0};
        int [] arr5 = {2,2,2};
        int [] arr6 = {5,5,5};
        Assertions.assertAll("calcCosineSim",
                () -> assertEquals(5.0/7 , BigramModel.calcCosineSim(arr1,arr2),0.001),
                () -> assertEquals(Math.sqrt(42)/7 , BigramModel.calcCosineSim(arr1,arr3),0.001),
                () -> assertEquals(-1 , BigramModel.calcCosineSim(arr1,arr4)),
                () -> assertEquals(Math.sqrt(42)/7 , BigramModel.calcCosineSim(arr1,arr5) , 0.001),
                () -> assertEquals(1 , BigramModel.calcCosineSim(arr6,arr5) , 0.001)

        );

    }

    @Test
    void getClosestWord() throws IOException {
        test.initModel(TEST_FILENAME);
        one_word.initModel(ONE_WORD_FILENAME);

        Assertions.assertAll("getClosestWord",
                () -> assertEquals("a" , test.getClosestWord("c")),
                () -> assertEquals("a" , test.getClosestWord("b")),
                () -> assertEquals("a" , one_word.getClosestWord("a")),
                () -> assertEquals("f" , test.getClosestWord("i")),
                () -> assertEquals("i" , test.getClosestWord("f"))
        );

    }
}
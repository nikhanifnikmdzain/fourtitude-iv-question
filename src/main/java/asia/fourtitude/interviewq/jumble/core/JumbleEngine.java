package asia.fourtitude.interviewq.jumble.core;

import java.io.*;
import java.lang.System.Logger;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.PrintStream;

public class JumbleEngine {


	/**
     * From the input `word`, produces/generates a copy which has the same
     * letters, but in different ordering.
     *
     * Example: from "elephant" to "aeehlnpt".
     *
     * Evaluation/Grading:
     * a) pass unit test: JumbleEngineTest#scramble()
     * b) scrambled letters/output must not be the same as input
     *
     * @param word  The input word to scramble the letters.
     * @return  The scrambled output/letters.
     */
    public String scramble(String word) {
        /*
         * Refer to the method's Javadoc (above) and implement accordingly.
         * Must pass the corresponding unit tests.
         */
    	try {
            if (word == null || word.isEmpty()) {
                return word;
            }

            List<Character> characters = new ArrayList<>();
            for (char c : word.toCharArray()) {
                characters.add(c);
            }
            Collections.shuffle(characters);

            StringBuilder scrambledWord = new StringBuilder();
            for (char c : characters) {
                scrambledWord.append(c);
            }
            
            return scrambledWord.toString();
        } catch (Exception e) {
        	throw new Error(e);
        }

        
    
    }

    /**
     * Retrieves the palindrome words from the internal
     * word list/dictionary ("src/main/resources/words.txt").
     *
     * Word of single letter is not considered as valid palindrome word.
     *
     * Examples: "eye", "deed", "level".
     *
     * Evaluation/Grading:
     * a) able to access/use resource from classpath
     * b) using inbuilt Collections
     * c) using "try-with-resources" functionality/statement
     * d) pass unit test: JumbleEngineTest#palindrome()
     *
     * @return  The list of palindrome words found in system/engine.
     * @see https://www.google.com/search?q=palindrome+meaning
     */
    public Collection<String> retrievePalindromeWords() {
        /*
         * Refer to the method's Javadoc (above) and implement accordingly.
         * Must pass the corresponding unit tests.
         */
		List<String> palindromes = new ArrayList<>();
        try (InputStream is = getClass().getResourceAsStream("/words.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            palindromes = reader.lines()
                    .filter(word -> word.length() > 1 && isPalindrome(word))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return palindromes;
    	
    }
    
    private boolean isPalindrome(String word) {
        int left = 0;
        int right = word.length() - 1;
        while (left < right) {
            if (word.charAt(left) != word.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    /**
     * Picks one word randomly from internal word list.
     *
     * Evaluation/Grading:
     * a) pass unit test: JumbleEngineTest#randomWord()
     * b) provide a good enough implementation, if not able to provide a fast lookup
     * c) bonus points, if able to implement a fast lookup/scheme
     *
     * @param length  The word picked, must of length.
     * @return  One of the word (randomly) from word list.
     *          Or null if none matching.
     */
    public String pickOneRandomWord(Integer length) {
        /*
         * Refer to the method's Javadoc (above) and implement accordingly.
         * Must pass the corresponding unit tests.
         */

    	
		 List<String> wordsOfGivenLength = new ArrayList<>();
	     try (InputStream is = getClass().getResourceAsStream("/words.txt");
	          BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

	         wordsOfGivenLength = reader.lines()
	                 .filter(word -> word == null || validLength(length, word.length()))
	                 .collect(Collectors.toList());
	     } catch (Exception e) {
	         e.printStackTrace();
	     }
	
	     if (wordsOfGivenLength.isEmpty()) {
	         return null;
	     }
	
	     Random random = new Random();
	     return wordsOfGivenLength.get(random.nextInt(wordsOfGivenLength.size()));
     
    }
    
    public boolean validLength(Integer length, Integer wordlength) {
    	
    	if (length == null) {
    		return true;
    	}else if (wordlength == length) {
    		return true;
    	}
    	
    	return false;
    }

    /**
     * Checks if the `word` exists in internal word list.
     * Matching is case insensitive.
     *
     * Evaluation/Grading:
     * a) pass related unit tests in "JumbleEngineTest"
     * b) provide a good enough implementation, if not able to provide a fast lookup
     * c) bonus points, if able to implement a fast lookup/scheme
     *
     * @param word  The input word to check.
     * @return  true if `word` exists in internal word list.
     */
    public boolean exists(String word) {
        /*
         * Refer to the method's Javadoc (above) and implement accordingly.
         * Must pass the corresponding unit tests.
         */
    	
    	 try (Stream<String> lines = Files.lines(Paths.get(getClass().getResource("/words.txt").toURI()))) {
    	        return lines.anyMatch(line -> line.equalsIgnoreCase(word));
    	    } catch (IOException | URISyntaxException e) {
    	        e.printStackTrace();
    	        return false;
    	    }
    
    }

    /**
     * Finds all the words from internal word list which begins with the
     * input `prefix`.
     * Matching is case insensitive.
     *
     * Invalid `prefix` (null, empty string, blank string, non letter) will
     * return empty list.
     *
     * Evaluation/Grading:
     * a) pass related unit tests in "JumbleEngineTest"
     * b) provide a good enough implementation, if not able to provide a fast lookup
     * c) bonus points, if able to implement a fast lookup/scheme
     *
     * @param prefix  The prefix to match.
     * @return  The list of words matching the prefix.
     */
    public Collection<String> wordsMatchingPrefix(String prefix) {
        /*
         * Refer to the method's Javadoc (above) and implement accordingly.
         * Must pass the corresponding unit tests.
         */

    	List<String> matchList = new ArrayList<>();    	
    	try (InputStream is = getClass().getResourceAsStream("/words.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
			  	if (prefix == null || prefix.trim().isEmpty() || !prefix.matches("[a-zA-Z]+")) {
		            return matchList; 
		        }
			  	matchList = reader.lines()
		                 .filter(word -> word != null && word.toLowerCase().startsWith(prefix.toLowerCase()))
		                 .collect(Collectors.toList());
               
           } catch (Exception e) {
               e.printStackTrace();
           }
        return matchList;

    }

    /**
     * Finds all the words from internal word list that is matching
     * the searching criteria.
     *
     * `startChar` and `endChar` must be 'a' to 'z' only. And case insensitive.
     * `length`, if have value, must be positive integer (>= 1).
     *
     * Words are filtered using `startChar` and `endChar` first.
     * Then apply `length` on the result, to produce the final output.
     *
     * Must have at least one valid value out of 3 inputs
     * (`startChar`, `endChar`, `length`) to proceed with searching.
     * Otherwise, return empty list.
     *
     * Evaluation/Grading:
     * a) pass related unit tests in "JumbleEngineTest"
     * b) provide a good enough implementation, if not able to provide a fast lookup
     * c) bonus points, if able to implement a fast lookup/scheme
     *
     * @param startChar  The first character of the word to search for.
     * @param endChar    The last character of the word to match with.
     * @param length     The length of the word to match.
     * @return  The list of words matching the searching criteria.
     */
    public Collection<String> searchWords(Character startChar, Character endChar, Integer length) {
        /*
         * Refer to the method's Javadoc (above) and implement accordingly.
         * Must pass the corresponding unit tests.
         */
    	List<String> matchList = new ArrayList<>();   
    	  if (startChar == null && endChar == null && (length == null || length < 1)) {

              return matchList;
          }
    	try (InputStream is = getClass().getResourceAsStream("/words.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

			  	matchList = reader.lines()
		                 .filter(word -> validWord(word,startChar,endChar))
		                 .collect(Collectors.toList());
			  	if (length != null && length >= 1) {
	                matchList = matchList.stream()
	                        .filter(word -> word.length() == length)
	                        .collect(Collectors.toList());
		           }

           } catch (Exception e) {
               e.printStackTrace();
           }
    	
        return matchList;
 
    }
    
    public boolean validWord(String word,Character startChar, Character endChar) {
    	
    	if (word == null) {
    		return false;
    	}
    	startChar = (startChar != null) ? Character.toLowerCase(startChar) : null;
    	endChar = (endChar != null) ? Character.toLowerCase(endChar) : null;
    	
    	if((startChar != null && word.charAt(0) != startChar) || (endChar != null && word.charAt(word.length()-1) != endChar)) {
    		return false;
    	}
    	
    	return true;
    }

    /**
     * Generates all possible combinations of smaller/sub words using the
     * letters from input word.
     *
     * The `minLength` set the minimum length of sub word that is considered
     * as acceptable word.
     *
     * If length of input `word` is less than `minLength`, then return empty list.
     *
     * Example: From "yellow" and `minLength` = 3, the output sub words:
     *     low, lowly, lye, ole, owe, owl, well, welly, woe, yell, yeow, yew, yowl
     *
     * Evaluation/Grading:
     * a) pass related unit tests in "JumbleEngineTest"
     * b) provide a good enough implementation, if not able to provide a fast lookup
     * c) bonus points, if able to implement a fast lookup/scheme
     *
     * @param word       The input word to use as base/seed.
     * @param minLength  The minimum length (inclusive) of sub words.
     *                   Expects positive integer.
     *                   Default is 3.
     * @return  The list of sub words constructed from input `word`.
     */
    public Collection<String> generateSubWords(String word, Integer minLength) {
        /*
         * Refer to the method's Javadoc (above) and implement accordingly.
         * Must pass the corresponding unit tests.
         */
    	List<String> subWords = new ArrayList<>();  
    	
    	  if (minLength == null) {
              minLength = 3;
          }

          if (word == null || minLength < 1 || word.length() < minLength || !word.matches("[a-zA-Z]+")) {
              return subWords;
          }
          generateSubWordsHelper(word, "", minLength, subWords,word.length(), word);
      return subWords;
    }
    
    private void generateSubWordsHelper(String remaining, String current, int minLength, List<String> subWords,int originalLength, String originalWord) {
        if (current.length() >= minLength && current.length() <= originalLength && !current.equals(originalWord) &&  exists(current) && !subWords.contains(current) ) {
            subWords.add(current);
        }

        for (int i = 0; i < remaining.length(); i++) {
            generateSubWordsHelper(remaining.substring(0, i) + remaining.substring(i + 1),
                    current + remaining.charAt(i), minLength, subWords, originalLength, originalWord);
        }
    }
      

    /**
     * Creates a game state with word to guess, scrambled letters, and
     * possible combinations of words.
     *
     * Word is of length 6 characters.
     * The minimum length of sub words is of length 3 characters.
     *
     * @param length     The length of selected word.
     *                   Expects >= 3.
     * @param minLength  The minimum length (inclusive) of sub words.
     *                   Expects positive integer.
     *                   Default is 3.
     * @return  The game state.
     */
    public GameState createGameState(Integer length, Integer minLength) {
        Objects.requireNonNull(length, "length must not be null");
        if (minLength == null) {
            minLength = 3;
        } else if (minLength <= 0) {
            throw new IllegalArgumentException("Invalid minLength=[" + minLength + "], expect positive integer");
        }
        if (length < 3) {
            throw new IllegalArgumentException("Invalid length=[" + length + "], expect greater than or equals 3");
        }
        if (minLength > length) {
            throw new IllegalArgumentException("Expect minLength=[" + minLength + "] greater than length=[" + length + "]");
        }
        String original = this.pickOneRandomWord(length);
        if (original == null) {
            throw new IllegalArgumentException("Cannot find valid word to create game state");
        }
        String scramble = this.scramble(original);
        Map<String, Boolean> subWords = new TreeMap<>();
        for (String subWord : this.generateSubWords(original, minLength)) {
            subWords.put(subWord, Boolean.FALSE);
        }
        return new GameState(original, scramble, subWords);
    }

}

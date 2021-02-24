package bg.sofia.uni.fmi.mjt.tagger;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *  Tagger class' functionality: adds XML tags to all cities in a file; gets some statistical data
 */
public class Tagger {

    private Map<String, String> cities;
    private Map<String, Integer> citiesTagged;
    private long totalTagCount;

    /**
     * Creates a new instance of Tagger for a given list of city/country pairs
     *
     * @param citiesReader a java.io.Reader input stream containing list of cities and countries
     *                     in the specified CSV format
     */

    public Tagger(Reader citiesReader) {
        this.cities = new HashMap<>();
        this.citiesTagged = new HashMap<>();
        this.totalTagCount = 0;

        try (BufferedReader br = new BufferedReader(citiesReader)) {
            String line;

            while ((line = br.readLine()) != null) {
                int commaIndex = line.indexOf(",");
                cities.put(line.substring(0, commaIndex), line.substring(commaIndex + 1));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private boolean isLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    private String capitalizeFirst(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    /**
     * Processes an input stream of a text file, tags any cities and outputs result
     * to a text output stream.
     *
     * @param text   a java.io.Reader input stream containing text to be processed
     * @param output a java.io.Writer output stream containing the result of tagging
     */

    public void tagCities(Reader text, Writer output) {
        this.totalTagCount = 0;
        this.citiesTagged.clear();
        Pattern pat = Pattern.compile(".*\\R|.+\\z");

        try (var scanner = new Scanner(new BufferedReader(text));
                var writer = new BufferedWriter(output)) {

            String line;
            while ((line = scanner.findWithinHorizon(pat, 0)) != null) {
                int i = 0;
                while (i < line.length()) {
                    String word = "";
                    int j = i;
                    if (!isLetter(line.charAt(j))) {
                        j++;
                    } else {
                        while (j < line.length() && isLetter(line.charAt(j))) {
                            word += line.charAt(j);
                            j++;
                        }
                        String editedWord = capitalizeFirst(word);
                        if (cities.containsKey(editedWord)) {
                            String toAdd = "<city country=\"" + cities.get(editedWord) + "\">"
                                    + line.substring(i, j) + "</city>";
                            line = line.substring(0, i) + toAdd + line.substring(j);

                            j = i + toAdd.length();

                            if (citiesTagged.containsKey(editedWord)) {
                                citiesTagged.replace(editedWord, citiesTagged.get(editedWord) + 1);
                            } else {
                                citiesTagged.put(editedWord, 1);
                            }
                            totalTagCount++;
                        }
                    }
                    i = j;
                }
                writer.write(line);
                writer.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns a collection the top @n most tagged cities' unique names
     * from the last tagCities() invocation. Note that if a particular city has been tagged
     * more than once in the text, just one occurrence of its name should appear in the result.
     * If @n exceeds the total number of cities tagged, return as many as available
     * If tagCities() has not been invoked at all, return an empty collection.
     *
     * @param n the maximum number of top tagged cities to return
     * @return a collection the top @n most tagged cities' unique names
     * from the last tagCities() invocation.
     */
    public Collection<String> getNMostTaggedCities(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }

        if (n == 0) {
            return new ArrayList<String>();
        }

        if (n > citiesTagged.size()) {
            return citiesTagged.keySet();
        }

        List<Map.Entry<String, Integer>> list =
                new LinkedList<>(citiesTagged.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        Collection<String> result = new ArrayList<>();
        for (Iterator<Map.Entry<String, Integer>> iterator = list.iterator(); iterator.hasNext() && n > 0; n--) {
            Map.Entry<String, Integer> city = iterator.next();
            result.add(city.getKey());
        }
        return result;
    }

    /**
     * Returns a collection of all tagged cities' unique names
     * from the last tagCities() invocation. Note that if a particular city has been tagged
     * more than once in the text, just one occurrence of its name should appear in the result.
     * If tagCities() has not been invoked at all, return an empty collection.
     *
     * @return a collection of all tagged cities' unique names
     * from the last tagCities() invocation.
     */

    public Collection<String> getAllTaggedCities() {
        return citiesTagged.keySet();
    }

    /**
     * Returns the total number of tagged cities in the input text
     * from the last tagCities() invocation
     * In case a particular city has been taged in several occurences, all must be counted.
     * If tagCities() has not been invoked at all, return 0.
     *
     * @return the total number of tagged cities in the input text
     */

    public long getAllTagsCount() {
        return totalTagCount;
    }

}
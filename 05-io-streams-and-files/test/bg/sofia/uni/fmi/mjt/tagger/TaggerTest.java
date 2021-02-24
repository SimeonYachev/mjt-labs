package bg.sofia.uni.fmi.mjt.tagger;

import org.junit.Before;
import org.junit.Test;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.io.IOException;
import java.io.Writer;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class TaggerTest {

    private Tagger tagger;

    @Before
    public void initialize() throws IOException {
        try (Reader r = new BufferedReader(new FileReader(new File("world-cities.csv").getAbsolutePath()))) {
            tagger = new Tagger(r);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testTagCitiesNoCities() {

        String expectedLine = "";
        String actualLine = "";

        try (Writer writerInput = new PrintWriter("input.txt");
                Writer writerExpected = new PrintWriter("expected.txt")) {
            writerInput.write("Pesho is a good boy.");
            writerExpected.write("Pesho is a good boy.");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (Reader inputReader = new BufferedReader(new FileReader(new File("input.txt").getAbsolutePath()));
                Writer output = new PrintWriter("output.txt")) {

            tagger.tagCities(inputReader, output);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedReader outputReader = new BufferedReader(
                new FileReader(new File("output.txt").getAbsolutePath()));
             BufferedReader expectedReader = new BufferedReader(
                     new FileReader(new File("expected.txt").getAbsolutePath()))) {

            actualLine = outputReader.readLine();
            expectedLine = expectedReader.readLine();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        assertEquals("The actual output doesn't match the one that's expected.", expectedLine, actualLine);

        File input = new File("input.txt");
        File output = new File("output.txt");
        File expected = new File("expected.txt");

        if (input != null) {
            input.delete();
        }
        if (output != null) {
            output.delete();
        }
        if (expected != null) {
            expected.delete();
        }
    }

    @Test
    public void testTagCitiesStandard() {

        String expectedLine = "";
        String actualLine = "";

        try (Writer writerInput = new PrintWriter("input.txt");
                Writer writerExpected = new PrintWriter("expected.txt")) {
            writerInput.write("Sofia is a nice city.");
            writerExpected.write("<city country=\"Bulgaria\">Sofia</city> is "
                    + "a <city country=\"France\">nice</city> city.");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (Reader inputReader = new BufferedReader(new FileReader(new File("input.txt").getAbsolutePath()));
                Writer output = new PrintWriter("output.txt")) {

            tagger.tagCities(inputReader, output);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedReader outputReader = new BufferedReader(
                new FileReader(new File("output.txt").getAbsolutePath()));
             BufferedReader expectedReader = new BufferedReader(
                     new FileReader(new File("expected.txt").getAbsolutePath()))) {

            actualLine = outputReader.readLine();
            expectedLine = expectedReader.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }


        assertEquals("The function doesn't tag correctly.", expectedLine, actualLine);

        File input = new File("input.txt");
        File output = new File("output.txt");
        File expected = new File("expected.txt");

        if (input != null) {
            input.delete();
        }
        if (output != null) {
            output.delete();
        }
        if (expected != null) {
            expected.delete();
        }
    }

    @Test
    public void testTagCitiesMixedLetters() {

        String expectedLine = "";
        String actualLine = "";

        try (Writer writerInput = new PrintWriter("input.txt");
                Writer writerExpected = new PrintWriter("expected.txt")) {
            writerInput.write("sOfIa is a nice city.");
            writerExpected.write("<city country=\"Bulgaria\">sOfIa</city> is "
                    + "a <city country=\"France\">nice</city> city.");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (Reader inputReader = new BufferedReader(new FileReader(new File("input.txt").getAbsolutePath()));
                Writer output = new PrintWriter("output.txt")) {

            tagger.tagCities(inputReader, output);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedReader outputReader = new BufferedReader(
                new FileReader(new File("output.txt").getAbsolutePath()));
             BufferedReader expectedReader = new BufferedReader(
                     new FileReader(new File("expected.txt").getAbsolutePath()))) {

            actualLine = outputReader.readLine();
            expectedLine = expectedReader.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }


        assertEquals("The function changes upper/lower case letters.", expectedLine, actualLine);

        File input = new File("input.txt");
        File output = new File("output.txt");
        File expected = new File("expected.txt");

        if (input != null) {
            input.delete();
        }
        if (output != null) {
            output.delete();
        }
        if (expected != null) {
            expected.delete();
        }
    }

    @Test
    public void testTagCitiesWithSymbols() {

        String expectedLine = "";
        String actualLine = "";

        try (Writer writerInput = new PrintWriter("input.txt");
                Writer writerExpected = new PrintWriter("expected.txt")) {
            writerInput.write("Sofia[2020] is a nice city.");
            writerExpected.write("<city country=\"Bulgaria\">Sofia</city>[2020] is "
                    + "a <city country=\"France\">nice</city> city.");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (Reader inputReader = new BufferedReader(new FileReader(new File("input.txt").getAbsolutePath()));
                Writer output = new PrintWriter("output.txt")) {

            tagger.tagCities(inputReader, output);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedReader outputReader = new BufferedReader(
                new FileReader(new File("output.txt").getAbsolutePath()));
             BufferedReader expectedReader = new BufferedReader(
                     new FileReader(new File("expected.txt").getAbsolutePath()))) {

            actualLine = outputReader.readLine();
            expectedLine = expectedReader.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }


        assertEquals("The function doesn't tag correctly.", expectedLine, actualLine);

        File input = new File("input.txt");
        File output = new File("output.txt");
        File expected = new File("expected.txt");

        if (input != null) {
            input.delete();
        }
        if (output != null) {
            output.delete();
        }
        if (expected != null) {
            expected.delete();
        }
    }

    @Test
    public void testTagCitiesAtEnd() {

        String expectedLine = "";
        String actualLine = "";

        try (Writer writerInput = new PrintWriter("input.txt");
                Writer writerExpected = new PrintWriter("expected.txt")) {
            writerInput.write("This is a city at the end Plovdiv");
            writerExpected.write("This is a city at the end <city country=\"Bulgaria\">Plovdiv</city>");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (Reader inputReader = new BufferedReader(new FileReader(new File("input.txt").getAbsolutePath()));
                Writer output = new PrintWriter("output.txt")) {

            tagger.tagCities(inputReader, output);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedReader outputReader = new BufferedReader(
                new FileReader(new File("output.txt").getAbsolutePath()));
             BufferedReader expectedReader = new BufferedReader(
                     new FileReader(new File("expected.txt").getAbsolutePath()))) {

            actualLine = outputReader.readLine();
            expectedLine = expectedReader.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }


        assertEquals("The function doesn't tag correctly at the end of a line.", expectedLine, actualLine);

        File input = new File("input.txt");
        File output = new File("output.txt");
        File expected = new File("expected.txt");

        if (input != null) {
            input.delete();
        }
        if (output != null) {
            output.delete();
        }
        if (expected != null) {
            expected.delete();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNMostTaggedCitiesIllegalArgument() {
        tagger.getNMostTaggedCities(-1);
    }

    @Test
    public void testGetZeroMostTaggedCities() {
        assertEquals("Empty collection expected.", 0, tagger.getNMostTaggedCities(0).size());
    }

    @Test
    public void testGetNMostTaggedCitiesOnlyOne() {

        try (Writer writerInput = new PrintWriter("input.txt")) {
            writerInput.write("This is a city at the end Plovdiv");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (Reader inputReader = new BufferedReader(new FileReader(new File("input.txt").getAbsolutePath()));
                Writer output = new PrintWriter("output.txt")) {

            tagger.tagCities(inputReader, output);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Collection<String> expectedCol = new ArrayList<>();
        expectedCol.add("Plovdiv");

        assertEquals("Actual and expected collections' content mismatch.", expectedCol, tagger.getNMostTaggedCities(1));

        File input = new File("input.txt");
        File output = new File("output.txt");

        if (input != null) {
            input.delete();
        }
        if (output != null) {
            output.delete();
        }
    }

    @Test
    public void testGetNMostTaggedCitiesStandard() {

        try (Writer writerInput = new PrintWriter("input.txt")) {
            writerInput.write("This nice from Sofia to the of Sofia is a city at nice of the end Plovdiv");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (Reader inputReader = new BufferedReader(new FileReader(new File("input.txt").getAbsolutePath()));
                Writer output = new PrintWriter("output.txt")) {

            tagger.tagCities(inputReader, output);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Collection<String> expectedCol = new ArrayList<>();
        expectedCol.add("Sofia");
        expectedCol.add("Of");
        expectedCol.add("Nice");

        assertEquals("Actual and expected collections' content mismatch.", expectedCol, tagger.getNMostTaggedCities(3));

        File input = new File("input.txt");
        File output = new File("output.txt");

        if (input != null) {
            input.delete();
        }
        if (output != null) {
            output.delete();
        }
    }

    @Test
    public void testGetNMostTaggedCitiesNMoreThanCollectionSize() {

        try (Writer writerInput = new PrintWriter("input.txt")) {
            writerInput.write("This nice from Sofia to the of Sofia is a city at nice of the end Plovdiv");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (Reader inputReader = new BufferedReader(new FileReader(new File("input.txt").getAbsolutePath()));
                Writer output = new PrintWriter("output.txt")) {

            tagger.tagCities(inputReader, output);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Collection<String> expectedCol = new HashSet<>();
        expectedCol.add("Sofia");
        expectedCol.add("Of");
        expectedCol.add("Nice");
        expectedCol.add("Plovdiv");

        assertEquals("Function should return all tagged cities.", expectedCol, tagger.getNMostTaggedCities(9));

        File input = new File("input.txt");
        File output = new File("output.txt");

        if (input != null) {
            input.delete();
        }
        if (output != null) {
            output.delete();
        }
    }

    @Test
    public void testGetAllTaggedCities() {
        try (Writer writerInput = new PrintWriter("input.txt")) {
            writerInput.write("This nice from Sofia to the of Sofia is a city at nice of the end Plovdiv");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (Reader inputReader = new BufferedReader(new FileReader(new File("input.txt").getAbsolutePath()));
                Writer output = new PrintWriter("output.txt")) {

            tagger.tagCities(inputReader, output);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Collection<String> expectedCol = new HashSet<>();
        expectedCol.add("Sofia");
        expectedCol.add("Of");
        expectedCol.add("Nice");
        expectedCol.add("Plovdiv");

        assertEquals("Function should return all tagged cities.", expectedCol, tagger.getAllTaggedCities());

        File input = new File("input.txt");
        File output = new File("output.txt");

        if (input != null) {
            input.delete();
        }
        if (output != null) {
            output.delete();
        }
    }

    @Test
    public void testGetAllTagsCountStandard() {
        try (Writer writerInput = new PrintWriter("input.txt")) {
            writerInput.write("This nice from Sofia to the of Sofia is a city at nice of the end Plovdiv");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (Reader inputReader = new BufferedReader(new FileReader(new File("input.txt").getAbsolutePath()));
                Writer output = new PrintWriter("output.txt")) {

            tagger.tagCities(inputReader, output);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertEquals("Tag count doesn't match the actual number", 7, tagger.getAllTagsCount());

        File input = new File("input.txt");
        File output = new File("output.txt");

        if (input != null) {
            input.delete();
        }
        if (output != null) {
            output.delete();
        }
    }

    @Test
    public void testGetAllTagsCountNoTags() {
        try (Writer writerInput = new PrintWriter("input.txt")) {
            writerInput.write("Pesho is a good boy.");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (Reader inputReader = new BufferedReader(new FileReader(new File("input.txt").getAbsolutePath()));
                Writer output = new PrintWriter("output.txt")) {

            tagger.tagCities(inputReader, output);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertEquals("Tag count doesn't match the actual number", 0, tagger.getAllTagsCount());

        File input = new File("input.txt");
        File output = new File("output.txt");

        if (input != null) {
            input.delete();
        }
        if (output != null) {
            output.delete();
        }
    }
}

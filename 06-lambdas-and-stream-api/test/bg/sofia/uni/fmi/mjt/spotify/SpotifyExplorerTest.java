package bg.sofia.uni.fmi.mjt.spotify;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


import static org.junit.Assert.assertEquals;

public class SpotifyExplorerTest {

    private SpotifyExplorer se;

    @Test
    public void testGetAllSpotifyTracksEmpty() {
        try (Reader r = new BufferedReader
                (new InputStreamReader
                        (new FileInputStream("test-empty.csv"), StandardCharsets.UTF_8))) {
            se = new SpotifyExplorer(r);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Collection<SpotifyTrack> expected = new ArrayList<>();

        assertEquals("Empty dataset should return an empty collection.", expected, se.getAllSpotifyTracks());
    }

    @Test
    public void testGetAllSpotifyTracksStandard() {
        try (Reader r = new BufferedReader
                (new InputStreamReader
                        (new FileInputStream("test-data.csv"), StandardCharsets.UTF_8))) {
            se = new SpotifyExplorer(r);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Collection<SpotifyTrack> expected = new ArrayList<>();
        expected.add(SpotifyTrack.of("5V1Lf3KHwcoLTInrP93cqc,['REO Speedwagon'],Roll with the Changes,2004,38,"
                + "336907,93.67,-6.649,0.703,0.00513,0.465,0.879,0.156,0.0421,0"));
        expected.add(SpotifyTrack.of("1AEoNmoU5UaP1wV4UisLh7,['Heiruspecs'],Heartsprings,2004,36,199467,94.075"
                + ",-9.967,0.663,0.516,0.7,0.687,0.105,0.303,0"));
        expected.add(SpotifyTrack.of("6io3AG4PaHu5Ksx41r4zHw,['Talking Heads'],Love → Building on Fire - 2004 Remaster"
                + ",2004,40,178000,129.53,-6.667,0.757,0.0826,0.723,0.658,0.17,0.0507,0"));
        expected.add(SpotifyTrack.of("7iGt5mjMkuz9YnDyTJ6ovU,['John Williams'],Lupin's Transformation and Chasing "
                + "Scabbers,2004,42,181867,85.664,-22.111,0.0865,0.774,0.249,0.232,0.0919,0.0541,0"));
        expected.add(SpotifyTrack.of("61mIRqIKyoKaWXn8puXoRQ,['Shabba Ranks'; 'Krystal'],Twice My Age (feat. Krystal)"
                + ",2005,45,347160,92.322,-18.303,0.929,0.00771,0.828,0.419,0.0894,0.139,0"));
        expected.add(SpotifyTrack.of("25OkbTWnnvrgCGBRAUeLVh,['Webbie'],Like That,2005,38,242147,79.981,-6.953,0.205"
                + ",0.00186,0.792,0.423,0.0875,0.058,1"));
        expected.add(SpotifyTrack.of("0KGZzYTmH3lfpSKOcjnOkp,['Webbie'; 'Bun B'],Give Me That,2005,38,267720,78.001"
                + ",-6.002,0.506,0.00694,0.674,0.773,0.312,0.288,1"));
        expected.add(SpotifyTrack.of("0v4150cKFLKaB56wSohF49,['T-Pain'],I'm Sprung 2,2005,40,259840,100.029,-9.7"
                + ",0.41,0.0883,0.755,0.473,0.189,0.342,1"));

        assertEquals("Wrong collection of all tracks.", expected, se.getAllSpotifyTracks());
    }

    @Test
    public void testGetExplicitSpotifyTracksEmpty() {
        try (Reader r = new BufferedReader
                (new InputStreamReader
                        (new FileInputStream("test-empty.csv"), StandardCharsets.UTF_8))) {
            se = new SpotifyExplorer(r);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Collection<SpotifyTrack> expected = new ArrayList<>();

        assertEquals("Empty dataset should return an empty collection.", expected, se.getExplicitSpotifyTracks());
    }

    @Test
    public void testGetExplicitSpotifyTracksStandard() {
        try (Reader r = new BufferedReader
                (new InputStreamReader
                        (new FileInputStream("test-data.csv"), StandardCharsets.UTF_8))) {
            se = new SpotifyExplorer(r);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Collection<SpotifyTrack> expected = new ArrayList<>();
        expected.add(SpotifyTrack.of("25OkbTWnnvrgCGBRAUeLVh,['Webbie'],Like That,2005,38,242147,79.981,-6.953,0.205"
                + ",0.00186,0.792,0.423,0.0875,0.058,1"));
        expected.add(SpotifyTrack.of("0KGZzYTmH3lfpSKOcjnOkp,['Webbie'; 'Bun B'],Give Me That,2005,38,267720,78.001"
                + ",-6.002,0.506,0.00694,0.674,0.773,0.312,0.288,1"));
        expected.add(SpotifyTrack.of("0v4150cKFLKaB56wSohF49,['T-Pain'],I'm Sprung 2,2005,40,259840,100.029,-9.7"
                + ",0.41,0.0883,0.755,0.473,0.189,0.342,1"));

        assertEquals("Wrong collection of explicit tracks.", expected, se.getExplicitSpotifyTracks());
    }

    @Test
    public void testGroupSpotifyTracksByYearEmpty() {
        try (Reader r = new BufferedReader
                (new InputStreamReader
                        (new FileInputStream("test-empty.csv"), StandardCharsets.UTF_8))) {
            se = new SpotifyExplorer(r);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Map<Integer, Set<SpotifyTrack>> expected = new HashMap<>();

        assertEquals("Empty dataset should return an empty map.", expected, se.groupSpotifyTracksByYear());
    }

    @Test
    public void testGroupSpotifyTracksByYearStandard() {
        try (Reader r = new BufferedReader
                (new InputStreamReader
                        (new FileInputStream("test-data.csv"), StandardCharsets.UTF_8))) {
            se = new SpotifyExplorer(r);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Map<Integer, Set<SpotifyTrack>> expected = new HashMap<>();
        Set<SpotifyTrack> set2004 = new HashSet<>();
        set2004.add(SpotifyTrack.of("5V1Lf3KHwcoLTInrP93cqc,['REO Speedwagon'],Roll with the Changes,2004,38,"
                + "336907,93.67,-6.649,0.703,0.00513,0.465,0.879,0.156,0.0421,0"));
        set2004.add(SpotifyTrack.of("1AEoNmoU5UaP1wV4UisLh7,['Heiruspecs'],Heartsprings,2004,36,199467,94.075"
                + ",-9.967,0.663,0.516,0.7,0.687,0.105,0.303,0"));
        set2004.add(SpotifyTrack.of("6io3AG4PaHu5Ksx41r4zHw,['Talking Heads'],Love → Building on Fire - 2004 Remaster"
                + ",2004,40,178000,129.53,-6.667,0.757,0.0826,0.723,0.658,0.17,0.0507,0"));
        set2004.add(SpotifyTrack.of("7iGt5mjMkuz9YnDyTJ6ovU,['John Williams'],Lupin's Transformation and Chasing "
                + "Scabbers,2004,42,181867,85.664,-22.111,0.0865,0.774,0.249,0.232,0.0919,0.0541,0"));
        expected.put(2004, set2004);

        Set<SpotifyTrack> set2005 = new HashSet<>();
        set2005.add(SpotifyTrack.of("61mIRqIKyoKaWXn8puXoRQ,['Shabba Ranks'; 'Krystal'],Twice My Age (feat. Krystal)"
                + ",2005,45,347160,92.322,-18.303,0.929,0.00771,0.828,0.419,0.0894,0.139,0"));
        set2005.add(SpotifyTrack.of("25OkbTWnnvrgCGBRAUeLVh,['Webbie'],Like That,2005,38,242147,79.981,-6.953,0.205"
                + ",0.00186,0.792,0.423,0.0875,0.058,1"));
        set2005.add(SpotifyTrack.of("0KGZzYTmH3lfpSKOcjnOkp,['Webbie'; 'Bun B'],Give Me That,2005,38,267720,78.001"
                + ",-6.002,0.506,0.00694,0.674,0.773,0.312,0.288,1"));
        set2005.add(SpotifyTrack.of("0v4150cKFLKaB56wSohF49,['T-Pain'],I'm Sprung 2,2005,40,259840,100.029,-9.7"
                + ",0.41,0.0883,0.755,0.473,0.189,0.342,1"));
        expected.put(2005, set2005);

        assertEquals("Wrong collection of all tracks.", expected, se.groupSpotifyTracksByYear());
    }

    @Test
    public void testGetArtistActiveYearsEmpty() {
        try (Reader r = new BufferedReader
                (new InputStreamReader
                        (new FileInputStream("test-empty.csv"), StandardCharsets.UTF_8))) {
            se = new SpotifyExplorer(r);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int expected = 0;

        assertEquals("Empty dataset should return 0.", expected, se.getArtistActiveYears("Webbie"));
    }

    @Test
    public void testGetArtistActiveYearsNoTracks() {
        try (Reader r = new BufferedReader
                (new InputStreamReader
                        (new FileInputStream("test-data.csv"), StandardCharsets.UTF_8))) {
            se = new SpotifyExplorer(r);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int expected = 0;

        assertEquals("No tracks from this artist should return 0.", expected, se.getArtistActiveYears("Eminem"));
    }

    @Test
    public void testGetArtistActiveYearsStandard() {
        try (Reader r = new BufferedReader
                (new InputStreamReader
                        (new FileInputStream("test-data.csv"), StandardCharsets.UTF_8))) {
            se = new SpotifyExplorer(r);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int expected = 1;

        assertEquals("Wrong number of active years.", expected, se.getArtistActiveYears("Webbie"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetTopNHighestValenceTracksFromThe80sNegativeN() {
        try (Reader r = new BufferedReader
                (new InputStreamReader
                        (new FileInputStream("test-data.csv"), StandardCharsets.UTF_8))) {
            se = new SpotifyExplorer(r);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        se.getTopNHighestValenceTracksFromThe80s(-1);
    }

    @Test
    public void testGetTopNHighestValenceTracksFromThe80sOne() {
        try (Reader r = new BufferedReader
                (new InputStreamReader
                        (new FileInputStream("test-80s.csv"), StandardCharsets.UTF_8))) {
            se = new SpotifyExplorer(r);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<SpotifyTrack> expected = new ArrayList<>();
        expected.add(SpotifyTrack.of("1p5GFCHCm5u8t2tvhDqQ7Z,['The Flirts'],Danger,1982,58,371267,128.979"
                + ",-10.934,0.693,0.00552,0.757,0.505,0.13,0.0358,0"));

        assertEquals("Wrong collection of high valence tracks."
                , expected, se.getTopNHighestValenceTracksFromThe80s(1));
    }

    @Test
    public void testGetTopNHighestValenceTracksFromThe80sMultiple() {
        try (Reader r = new BufferedReader
                (new InputStreamReader
                        (new FileInputStream("test-80s.csv"), StandardCharsets.UTF_8))) {
            se = new SpotifyExplorer(r);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<SpotifyTrack> expected = new ArrayList<>();
        expected.add(SpotifyTrack.of("1p5GFCHCm5u8t2tvhDqQ7Z,['The Flirts'],Danger,1982,58,371267,128.979"
                + ",-10.934,0.693,0.00552,0.757,0.505,0.13,0.0358,0"));
        expected.add(SpotifyTrack.of("5IPCeuuTLjdKJGruJ1x6kL,['Social Distortion'],Mommy's Little Monster,1982"
                + ",45,212933,178.12,-4.714,0.48,0.00018,0.149,0.917,0.158,0.0686,0"));
        expected.add(SpotifyTrack.of("1KYaAx4p3OVHE1zS00NUwJ,['Van Halen'],Hang 'em High - 2015 Remaster,1982"
                + ",44,208053,129.503,-3.646,0.477,0.0263,0.366,0.983,0.122,0.0797,0"));


        assertEquals("Wrong collection of high valence tracks."
                , expected, se.getTopNHighestValenceTracksFromThe80s(3));
    }

    @Test
    public void testGetTopNHighestValenceTracksFromThe80sNMoreThanSize() {
        try (Reader r = new BufferedReader
                (new InputStreamReader
                        (new FileInputStream("test-80s.csv"), StandardCharsets.UTF_8))) {
            se = new SpotifyExplorer(r);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<SpotifyTrack> expected = new ArrayList<>();
        expected.add(SpotifyTrack.of("1p5GFCHCm5u8t2tvhDqQ7Z,['The Flirts'],Danger,1982,58,371267,128.979"
                + ",-10.934,0.693,0.00552,0.757,0.505,0.13,0.0358,0"));
        expected.add(SpotifyTrack.of("5IPCeuuTLjdKJGruJ1x6kL,['Social Distortion'],Mommy's Little Monster,1982"
                + ",45,212933,178.12,-4.714,0.48,0.00018,0.149,0.917,0.158,0.0686,0"));
        expected.add(SpotifyTrack.of("1KYaAx4p3OVHE1zS00NUwJ,['Van Halen'],Hang 'em High - 2015 Remaster,1982"
                + ",44,208053,129.503,-3.646,0.477,0.0263,0.366,0.983,0.122,0.0797,0"));


        assertEquals("Wrong collection of high valence tracks."
                , expected, se.getTopNHighestValenceTracksFromThe80s(8));
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetMostPopularTrackFromThe90sNoTrackFrom90s() {
        try (Reader r = new BufferedReader
                (new InputStreamReader
                        (new FileInputStream("test-80s.csv"), StandardCharsets.UTF_8))) {
            se = new SpotifyExplorer(r);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        se.getMostPopularTrackFromThe90s();
    }

    @Test
    public void testGetMostPopularTrackFromThe90sStandard() {
        try (Reader r = new BufferedReader
                (new InputStreamReader
                        (new FileInputStream("test-90s.csv"), StandardCharsets.UTF_8))) {
            se = new SpotifyExplorer(r);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SpotifyTrack expected = SpotifyTrack.of("5oPFYXq8KnZuYXiSUkjDFT,['Los Diablitos'],Cuando Casi Te Olvidaba,1999"
                + ",55,283293,144.062,-2.17,0.823,0.211,0.811,0.793,0.176,0.057,0");

        assertEquals("Wrong most popular track from the 90s.", expected, se.getMostPopularTrackFromThe90s());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNumberOfLongerTracksBeforeYearNegativeMinutes() {
        try (Reader r = new BufferedReader
                (new InputStreamReader
                        (new FileInputStream("test-data.csv"), StandardCharsets.UTF_8))) {
            se = new SpotifyExplorer(r);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        se.getNumberOfLongerTracksBeforeYear(-1,2005);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNumberOfLongerTracksBeforeYearNegativeYear() {
        try (Reader r = new BufferedReader
                (new InputStreamReader
                        (new FileInputStream("test-data.csv"), StandardCharsets.UTF_8))) {
            se = new SpotifyExplorer(r);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        se.getNumberOfLongerTracksBeforeYear(2,-1945);
    }

    @Test
    public void testGetNumberOfLongerTracksBeforeYearStandard() {
        try (Reader r = new BufferedReader
                (new InputStreamReader
                        (new FileInputStream("test-data.csv"), StandardCharsets.UTF_8))) {
            se = new SpotifyExplorer(r);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int expected = 3;
        int minutes = 3;
        assertEquals("Wrong number of tracks longer than " + minutes
                + " minutes.", expected, se.getNumberOfLongerTracksBeforeYear(minutes, 2005));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetTheLoudestTrackInYearNegativeYear() {
        try (Reader r = new BufferedReader
                (new InputStreamReader
                        (new FileInputStream("test-data.csv"), StandardCharsets.UTF_8))) {
            se = new SpotifyExplorer(r);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        se.getTheLoudestTrackInYear(-1945);
    }

    @Test
    public void testGetTheLoudestTrackInYearStandard() {
        try (Reader r = new BufferedReader
                (new InputStreamReader
                        (new FileInputStream("test-data.csv"), StandardCharsets.UTF_8))) {
            se = new SpotifyExplorer(r);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Optional<SpotifyTrack> expected = Optional.of(SpotifyTrack.of("0KGZzYTmH3lfpSKOcjnOkp,['Webbie'; 'Bun B']"
                + ",Give Me That,2005,38,267720,78.001,-6.002,0.506,0.00694,0.674,0.773,0.312,0.288,1"));

        assertEquals("That's not the loudest track for this year!",
                expected, se.getTheLoudestTrackInYear(2005));
    }
}

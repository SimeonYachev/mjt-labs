package bg.sofia.uni.fmi.mjt.wish.list;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import static org.junit.Assert.assertEquals;

public class CommandExecutorTest {

    private CommandExecutor cmdExec;

    @Before
    public void initialize() {
        cmdExec = new CommandExecutor();
    }

    @Test
    public void testRegisterSuccessful() {
        try {
            SocketChannel sc = SocketChannel.open();
            String message = "register Zdravko parola";
            String actualReply = cmdExec.execute(sc.hashCode(), message);

            String expectedReply = "[ Username Zdravko successfully registered ]" + System.lineSeparator();

            assertEquals("Wrong server response for register.", expectedReply, actualReply);

        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the socket channel");
        }
    }

    @Test
    public void testRegisterAlreadyTaken() {
        try {
            SocketChannel sc = SocketChannel.open();
            String message = "register Zdravko parola";
            cmdExec.execute(sc.hashCode(), message);
            message = "register Zdravko parola";
            String actualReply = cmdExec.execute(sc.hashCode(), message);

            String expectedReply = "[ Username Zdravko is already taken, select another one ]" + System.lineSeparator();

            assertEquals("Wrong server response for register.", expectedReply, actualReply);

        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the socket channel");
        }
    }

    @Test
    public void testRegisterInvalid() {
        try {
            SocketChannel sc = SocketChannel.open();
            String message = "register Zdr#avko parola";
            String actualReply = cmdExec.execute(sc.hashCode(), message);

            String expectedReply = "[ Username Zdr#avko is invalid, select a valid one ]" + System.lineSeparator();

            assertEquals("Wrong server response for register.", expectedReply, actualReply);

        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the socket channel");
        }
    }

    @Test
    public void testLoginSuccessful() {
        try {
            SocketChannel sc = SocketChannel.open();
            String message = "register Zdravko parola";
            cmdExec.execute(sc.hashCode(), message);
            message = "login Zdravko parola";
            String actualReply = cmdExec.execute(sc.hashCode(), message);

            String expectedReply = "[ User Zdravko successfully logged in ]" + System.lineSeparator();

            assertEquals("Wrong server response for login.", expectedReply, actualReply);

        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the socket channel");
        }
    }

    @Test
    public void testLoginInvalidCombination() {
        try {
            SocketChannel sc = SocketChannel.open();
            String message = "register Zdravko parola";
            cmdExec.execute(sc.hashCode(), message);
            message = "login Zdravko parola123";
            String actualReply = cmdExec.execute(sc.hashCode(), message);

            String expectedReply = "[ Invalid username/password combination ]" + System.lineSeparator();

            assertEquals("Wrong server response for login.", expectedReply, actualReply);

        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the socket channel");
        }
    }

    @Test
    public void testPostWishSuccessful() {
        try {
            SocketChannel sc = SocketChannel.open();
            String message = "register Zdravko parola";
            cmdExec.execute(sc.hashCode(), message);
            message = "post-wish Zdravko kola";
            String actualReply = cmdExec.execute(sc.hashCode(), message);

            String expectedReply = "[ Gift kola for student Zdravko submitted successfully ]" + System.lineSeparator();

            assertEquals("Wrong server response for post-wish.", expectedReply, actualReply);

        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the socket channel");
        }
    }

    @Test
    public void testPostWishAlreadySubmitted() {
        try {
            SocketChannel sc = SocketChannel.open();
            String message = "register Zdravko parola";
            cmdExec.execute(sc.hashCode(), message);
            message = "post-wish Zdravko kola";
            cmdExec.execute(sc.hashCode(), message);
            message = "post-wish Zdravko kola";
            String actualReply = cmdExec.execute(sc.hashCode(), message);

            String expectedReply = "[ The same gift for student Zdravko was already submitted ]"
                    + System.lineSeparator();

            assertEquals("Wrong server response for post-wish.", expectedReply, actualReply);

        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the socket channel");
        }
    }

    @Test
    public void testPostWishUserNotRegistered() {
        try {
            SocketChannel sc = SocketChannel.open();
            String message = "register Zdravko parola";
            cmdExec.execute(sc.hashCode(), message);
            message = "post-wish Pesho kola";
            String actualReply = cmdExec.execute(sc.hashCode(), message);

            String expectedReply = "[ Student with username Pesho is not registered ]" + System.lineSeparator();

            assertEquals("Wrong server response for post-wish.", expectedReply, actualReply);

        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the socket channel");
        }
    }

    @Test
    public void testPostWishNotLoggedIn() {
        try {
            SocketChannel sc = SocketChannel.open();
            String message = "register Zdravko parola";
            cmdExec.execute(sc.hashCode(), message);
            message = "logout";
            cmdExec.execute(sc.hashCode(), message);
            message = "post-wish Zdravko kola";
            String actualReply = cmdExec.execute(sc.hashCode(), message);

            String expectedReply = "[ You are not logged in ]" + System.lineSeparator();

            assertEquals("Wrong server response for post-wish.", expectedReply, actualReply);

        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the socket channel");
        }
    }

    @Test
    public void testGetWishSuccessful() {
        try {
            SocketChannel sc = SocketChannel.open();
            String message = "register Zdravko parola";
            cmdExec.execute(sc.hashCode(), message);
            message = "register Pesho parolapesho";
            cmdExec.execute(sc.hashCode(), message);
            message = "post-wish Zdravko kola";
            cmdExec.execute(sc.hashCode(), message);
            message = "get-wish Zdravko";
            String actualReply = cmdExec.execute(sc.hashCode(), message);

            String expectedReply = "[ Zdravko: [kola] ]" + System.lineSeparator();

            assertEquals("Wrong server response for get-wish.", expectedReply, actualReply);

        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the socket channel");
        }
    }

    @Test
    public void testGetWishNotLoggedIn() {
        try {
            SocketChannel sc = SocketChannel.open();
            String message = "get-wish Zdravko";
            String actualReply = cmdExec.execute(sc.hashCode(), message);

            String expectedReply = "[ You are not logged in ]" + System.lineSeparator();

            assertEquals("Wrong server response for get-wish.", expectedReply, actualReply);

        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the socket channel");
        }
    }

    @Test
    public void testGetWishNoStudents() {
        try {
            SocketChannel sc = SocketChannel.open();
            String message = "register Zdravko parola";
            cmdExec.execute(sc.hashCode(), message);
            message = "get-wish Pesho";
            String actualReply = cmdExec.execute(sc.hashCode(), message);

            String expectedReply = "[ There are no students present in the wish list ]" + System.lineSeparator();

            assertEquals("Wrong server response for get-wish.", expectedReply, actualReply);

        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the socket channel");
        }
    }

    @Test
    public void testGetWishForYourself() {
        try {
            SocketChannel sc = SocketChannel.open();
            String message = "register Zdravko parola";
            cmdExec.execute(sc.hashCode(), message);
            message = "post-wish Zdravko kola";
            cmdExec.execute(sc.hashCode(), message);
            message = "get-wish Zdravko";
            String actualReply = cmdExec.execute(sc.hashCode(), message);

            String expectedReply = "[ There are no students present in the wish list ]" + System.lineSeparator();

            assertEquals("Wrong server response for get-wish.", expectedReply, actualReply);

        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the socket channel");
        }
    }

    @Test
    public void testLogoutSuccessful() {
        try {
            SocketChannel sc = SocketChannel.open();
            String message = "register Zdravko parola";
            cmdExec.execute(sc.hashCode(), message);
            message = "logout";
            String actualReply = cmdExec.execute(sc.hashCode(), message);

            String expectedReply = "[ Successfully logged out ]" + System.lineSeparator();

            assertEquals("Wrong server response for logout.", expectedReply, actualReply);

        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the socket channel");
        }
    }

    @Test
    public void testLogoutNotLoggedIn() {
        try {
            SocketChannel sc = SocketChannel.open();
            String message = "logout";
            String actualReply = cmdExec.execute(sc.hashCode(), message);

            String expectedReply = "[ You are not logged in ]" + System.lineSeparator();

            assertEquals("Wrong server response for logout.", expectedReply, actualReply);

        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the socket channel");
        }
    }

    @Test
    public void testDisconnect() {
        try {
            SocketChannel sc = SocketChannel.open();
            String message = "disconnect";
            String actualReply = cmdExec.execute(sc.hashCode(), message);

            String expectedReply = "[ Disconnected from server ]" + System.lineSeparator();

            assertEquals("Wrong server response for disconnect.", expectedReply, actualReply);

        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the socket channel");
        }
    }

    @Test
    public void testUnknownCommand() {
        try {
            SocketChannel sc = SocketChannel.open();
            String message = "blabla";
            String actualReply = cmdExec.execute(sc.hashCode(), message);

            String expectedReply = "[ Unknown command ]" + System.lineSeparator();

            assertEquals("Wrong server response for unknown command.", expectedReply, actualReply);

        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the socket channel");
        }
    }
}

package bg.sofia.uni.fmi.mjt.wish.list;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Map;


public class CommandExecutor {

    private UserStorage users;

    public CommandExecutor() {
        Map<String, Set<String>> wishes = new HashMap<>();
        Map<String, String> registeredUsers = new HashMap<>();
        Map<Integer, String> loggedUsers = new HashMap<>();
        this.users = new UserStorage(wishes, registeredUsers, loggedUsers);
    }

    public String execute(Integer scHash, String message) {
        String reply;
        if (message.startsWith("register")) {
            reply = register(scHash, message);

        } else if (message.startsWith("login")) {
            reply = login(scHash, message);

        } else if (message.startsWith("post-wish")) {
            reply = postWish(scHash, message);

        } else if (message.startsWith("get-wish")) {
            reply = getWish(scHash);

        } else if (message.equals("logout")) {
            reply = logout(scHash);

        } else if (message.equals("disconnect")) {
            reply = disconnect(scHash);

        } else {
            reply = "[ Unknown command ]";
        }

        reply += System.lineSeparator();
        return reply;
    }

    private String register(Integer scHash, String message) {
        String reply;
        if (validUsername(message)) {
            StringsPair p = getTwoSubstrings(message);
            String username = p.first();
            String password = p.second();

            if (users.registeredUsers().containsKey(username)) {
                reply = "[ Username " + username + " is already taken, select another one ]";
            } else {
                users.registeredUsers().put(username, password);
                users.loggedUsers().put(scHash, username);
                reply = "[ Username " + username + " successfully registered ]";
            }
        } else {
            StringsPair p = getTwoSubstrings(message);
            String username = p.first();
            reply = "[ Username " + username + " is invalid, select a valid one ]";
        }

        return reply;
    }

    private String login(Integer scHash, String message) {
        String reply;
        StringsPair p = getTwoSubstrings(message);
        String username = p.first();
        String password = p.second();

        if (users.registeredUsers().containsKey(username) && password.equals(users.registeredUsers().get(username))) {
            reply = "[ User " + username + " successfully logged in ]";
            users.loggedUsers().put(scHash, username);
        } else {
            reply = "[ Invalid username/password combination ]";
        }

        return reply;
    }

    private String postWish(Integer scHash, String message) {
        String reply;
        if (!users.loggedUsers().containsKey(scHash)) {
            reply = "[ You are not logged in ]";
            return reply;
        }
        StringsPair p = getTwoSubstrings(message);
        String name = p.first();
        String gift = p.second();

        if (users.wishes().containsKey(name)) {
            if (users.wishes().get(name).contains(gift)) {
                reply = "[ The same gift for student " + name + " was already submitted ]";

            } else {
                users.wishes().get(name).add(gift);
                reply = "[ Gift " + gift + " for student " + name + " submitted successfully ]";
            }
        } else {
            if (!users.registeredUsers().containsKey(name)) {
                reply = "[ Student with username " + name + " is not registered ]";
            } else {
                addNewStudent(name, gift);
                reply = "[ Gift " + gift + " for student " + name + " submitted successfully ]";
            }
        }

        return reply;
    }

    private String getWish(Integer scHash) {
        String reply;
        if (!users.loggedUsers().containsKey(scHash)) {
            reply = "[ You are not logged in ]";
            return reply;
        }
        if (users.wishes().isEmpty()) {
            reply = "[ There are no students present in the wish list ]";

        } else {
            Set<String> keySet = users.wishes().keySet();
            keySet.remove(users.loggedUsers().get(scHash));
            if (keySet.size() == 0) {
                reply = "[ There are no students present in the wish list ]";
                return reply;
            }
            String[] keys = new String[keySet.size()];
            keySet.toArray(keys);
            String randomName = keys[new Random().nextInt(keys.length)];

            Set<String> gifts = users.wishes().get(randomName);
            reply = "[ " + randomName + ": " + gifts + " ]";
            users.wishes().remove(randomName);
        }

        return reply;
    }

    private String logout(Integer scHash) {
        String reply;
        if (!users.loggedUsers().containsKey(scHash)) {
            reply = "[ You are not logged in ]";
            return reply;
        } else {
            users.loggedUsers().remove(scHash);
            reply = "[ Successfully logged out ]";
        }

        return reply;
    }

    private String disconnect(Integer scHash) {
        users.loggedUsers().remove(scHash);

        return "[ Disconnected from server ]";
    }

    private void addNewStudent(String name, String gift) {
        Set<String> set = new HashSet<>();
        set.add(gift);
        users.wishes().put(name, set);
    }

    private StringsPair getTwoSubstrings(String s) {
        int wsIndex = s.indexOf(" ");
        int secondWsIndex = s.substring(wsIndex + 1).indexOf(" ")
                + s.substring(0, wsIndex).length() + 1;
        String first = s.substring(wsIndex + 1, secondWsIndex);
        String second = s.substring(secondWsIndex + 1).strip();

        return new StringsPair(first, second);
    }

    private boolean validUsername(String message) {
        if (!message.contains(" ") && !message.substring(message.indexOf(" ") + 1).contains(" ")) {
            return false;
        }
        StringsPair p = getTwoSubstrings(message);
        String username = p.first();

        return username.matches("[a-zA-Z0-9-_.]+");
    }
}

package bg.sofia.uni.fmi.mjt.wish.list;

import java.util.Map;
import java.util.Set;

public record UserStorage(Map<String, Set<String>> wishes,
                          Map<String, String> registeredUsers,
                          Map<Integer, String> loggedUsers) {}

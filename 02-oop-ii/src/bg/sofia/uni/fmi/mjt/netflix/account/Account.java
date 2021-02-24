package bg.sofia.uni.fmi.mjt.netflix.account;

import java.time.LocalDateTime;

public class Account {
    private String username;
    private LocalDateTime birthdayDate;

    public Account(String username, LocalDateTime birthdayDate) {
        this.username = username;
        this.birthdayDate = LocalDateTime.of(birthdayDate.getYear(),
                                             birthdayDate.getMonth(),
                                             birthdayDate.getDayOfMonth(),
                                             birthdayDate.getHour(),
                                             birthdayDate.getMinute());
    }

    public boolean equals(Account a) {
        return this.username.equals(a.username);
    }

    public boolean over14() {
        return LocalDateTime.now().minusYears(14).isAfter(this.birthdayDate);
    }

    public boolean over18() {
        return LocalDateTime.now().minusYears(18).isAfter(this.birthdayDate);
    }
}

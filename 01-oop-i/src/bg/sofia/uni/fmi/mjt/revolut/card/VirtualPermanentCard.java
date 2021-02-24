package bg.sofia.uni.fmi.mjt.revolut.card;

import java.time.LocalDate;

public class VirtualPermanentCard implements Card {

    private final String number;
    private final int pin;
    private LocalDate expirationDate;
    private int invalidPinInputs;

    public VirtualPermanentCard(String number, int pin, LocalDate expirationDate) {
        this.number = number;
        this.pin = pin;
        this.expirationDate = LocalDate.of(expirationDate.getYear(), expirationDate.getMonth(), expirationDate.getDayOfMonth());
        this.invalidPinInputs = 0;
    }

    public boolean equals(VirtualPermanentCard other) {
        if(other == null) return false;
        return this.number.equals(other.number);
    }

    public String getType() {
        return "VIRTUALPERMANENT";
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public boolean checkPin(int pin) {
        if(this.pin != pin) {
            invalidPinInputs++;
            if(invalidPinInputs == 3) {
                this.block();
            }
            return false;
        }
        invalidPinInputs = 0;
        return true;
    }

    public boolean isBlocked() {
        return this.expirationDate.equals(LocalDate.MIN);
    }

    public void block() {
        this.expirationDate = LocalDate.MIN;
    }

}

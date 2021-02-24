package bg.sofia.uni.fmi.mjt.smartcity.device;

import bg.sofia.uni.fmi.mjt.smartcity.enums.DeviceType;

import java.time.LocalDateTime;

public abstract class Device implements SmartDevice {

    private String name;
    private double powerConsumption;
    private LocalDateTime installationDateTime;

    public Device(String name, double powerConsumption, LocalDateTime installationDateTime) {
        this.name = name;
        this.powerConsumption = powerConsumption;
        this.installationDateTime = LocalDateTime.of(installationDateTime.getYear(),
                                                     installationDateTime.getMonth(),
                                                     installationDateTime.getDayOfMonth(),
                                                     installationDateTime.getHour(),
                                                     installationDateTime.getMinute());
    }

    public boolean equals(Device d) {
        return this.getId().equals(d.getId());
    }

    public abstract String getId();

    public abstract void setRegNumber(int number);

    public abstract int getRegNumber();

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public double getPowerConsumption() {
        return this.powerConsumption;
    }

    @Override
    public LocalDateTime getInstallationDateTime() {
        return this.installationDateTime;
    }

    public abstract DeviceType getType();
}

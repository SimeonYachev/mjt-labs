package bg.sofia.uni.fmi.mjt.smartcity.device;

import bg.sofia.uni.fmi.mjt.smartcity.enums.DeviceType;

import java.time.LocalDateTime;

public class SmartLamp extends Device {

    private String id;
    private static int slCount = 0;
    private int regNumber;

    public SmartLamp(String name, double powerConsumption, LocalDateTime installationDateTime) {
        super(name, powerConsumption, installationDateTime);
        this.id = DeviceType.LAMP.getShortName() + "-" + name
                                                 + "-" + slCount;
        this.slCount++;
    }

    public boolean equals(SmartLamp sl) {
        return super.equals(sl);
    }

    public void setRegNumber(int number) {
        this.regNumber = number;
    }

    public int getRegNumber() { return this.regNumber;}

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public DeviceType getType() {
        return DeviceType.LAMP;
    }
}

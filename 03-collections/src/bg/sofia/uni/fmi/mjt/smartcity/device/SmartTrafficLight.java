package bg.sofia.uni.fmi.mjt.smartcity.device;

import bg.sofia.uni.fmi.mjt.smartcity.enums.DeviceType;

import java.time.LocalDateTime;

public class SmartTrafficLight extends Device {

    private String id;
    private static int stlCount = 0;
    private int regNumber;

    public SmartTrafficLight(String name, double powerConsumption, LocalDateTime installationDateTime) {
        super(name, powerConsumption, installationDateTime);
        this.id = DeviceType.TRAFFIC_LIGHT.getShortName() + "-" + name
                                                          + "-" + stlCount;
        this.stlCount++;
    }

    public boolean equals(SmartTrafficLight stl) {
        return super.equals(stl);
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
        return DeviceType.TRAFFIC_LIGHT;
    }
}

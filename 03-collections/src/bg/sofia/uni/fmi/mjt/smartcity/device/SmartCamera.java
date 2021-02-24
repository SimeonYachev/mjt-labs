package bg.sofia.uni.fmi.mjt.smartcity.device;

import bg.sofia.uni.fmi.mjt.smartcity.enums.DeviceType;

import java.time.LocalDateTime;

public class SmartCamera extends Device {

    private String id;
    private static int scCount = 0;
    private int regNumber;

    public SmartCamera(String name, double powerConsumption, LocalDateTime installationDateTime) {
        super(name, powerConsumption, installationDateTime);
        this.id = DeviceType.CAMERA.getShortName() + "-" + name
                                                   + "-" + scCount;
        this.scCount++;
        this.regNumber = -1;
    }

    public boolean equals(SmartCamera sc) {
        return super.equals(sc);
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
        return DeviceType.CAMERA;
    }
}

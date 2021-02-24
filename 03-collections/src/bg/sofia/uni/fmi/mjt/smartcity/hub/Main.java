package bg.sofia.uni.fmi.mjt.smartcity.hub;

import bg.sofia.uni.fmi.mjt.smartcity.device.SmartCamera;
import bg.sofia.uni.fmi.mjt.smartcity.device.SmartDevice;
import bg.sofia.uni.fmi.mjt.smartcity.device.SmartLamp;
import bg.sofia.uni.fmi.mjt.smartcity.device.SmartTrafficLight;
import bg.sofia.uni.fmi.mjt.smartcity.enums.DeviceType;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {

        LocalDateTime inst1 = LocalDateTime.of(2008, 5,1,13,0);
        LocalDateTime inst2 = LocalDateTime.of(2008, 5,1,15,0);
        LocalDateTime inst3 = LocalDateTime.of(2008, 5,1,17,0);

        SmartDevice s1 = new SmartTrafficLight("svetofar1", 4, inst1);
        SmartDevice s2 = new SmartLamp("lampa1", 1, inst1);
        SmartDevice s3 = new SmartCamera("kamera1", 2, inst1);
        SmartDevice s4 = new SmartTrafficLight("svetofar2", 5, inst2);
        SmartDevice s5 = new SmartLamp("lampa2", 2, inst2);
        SmartDevice s6 = new SmartCamera("kamera2", 3, inst2);
        SmartDevice s7 = new SmartTrafficLight("svetofar3", 8, inst3);
        SmartDevice s8 = new SmartLamp("lampa3", 3, inst3);
        SmartDevice s9 = new SmartCamera("kamera3", 4, inst3);

        SmartCityHub hub = new SmartCityHub();
        hub.register(s1);
        hub.register(s2);
        hub.register(s3);
        hub.register(s4);
        hub.register(s5);
        hub.register(s6);
        hub.register(s7);
        hub.register(s8);
        hub.register(s9);

        System.out.println(hub.getDeviceQuantityPerType(DeviceType.LAMP));
        hub.unregister(s8);
        System.out.println(hub.getDeviceQuantityPerType(DeviceType.LAMP));

        System.out.println(hub.getTopNDevicesByPowerConsumption(0));

        System.out.println(hub.getFirstNDevicesByRegistration(2));

    }
}

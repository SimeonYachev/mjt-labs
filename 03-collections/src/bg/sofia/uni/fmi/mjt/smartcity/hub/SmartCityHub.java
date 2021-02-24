package bg.sofia.uni.fmi.mjt.smartcity.hub;

import bg.sofia.uni.fmi.mjt.smartcity.device.Device;
import bg.sofia.uni.fmi.mjt.smartcity.device.SmartDevice;
import bg.sofia.uni.fmi.mjt.smartcity.enums.DeviceType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Collection;
import java.util.Set;
import java.util.ArrayList;
import java.util.TreeSet;

public class SmartCityHub {

    private Map<String, SmartDevice> devices;
    private static int regNumber = 0;

    public SmartCityHub() {
        this.devices = new HashMap<>();
    }

    /**
     * Adds a @device to the SmartCityHub.
     *
     * @throws IllegalArgumentException in case @device is null.
     * @throws DeviceAlreadyRegisteredException in case the @device is already registered.
     */
    public void register(SmartDevice device) throws DeviceAlreadyRegisteredException {
        if(device == null) {
            throw new IllegalArgumentException();
        }

        if(this.devices.containsValue(device)){
            throw new DeviceAlreadyRegisteredException();
        }

        this.devices.put(device.getId(), device);
        ((Device) device).setRegNumber(regNumber++);
    }

    /**
     * Removes the @device from the SmartCityHub.
     *
     * @throws IllegalArgumentException in case null is passed.
     * @throws DeviceNotFoundException in case the @device is not found.
     */
    public void unregister(SmartDevice device) throws DeviceNotFoundException {
        if(device == null) {
            throw new IllegalArgumentException();
        }

        if(!this.devices.containsValue(device)) {
            throw new DeviceNotFoundException();
        }

        this.devices.remove(device.getId());
    }

    /**
     * Returns a SmartDevice with an ID @id.
     *
     * @throws IllegalArgumentException in case @id is null.
     * @throws DeviceNotFoundException in case device with ID @id is not found.
     */
    public SmartDevice getDeviceById(String id) throws DeviceNotFoundException {
        if(id == null) {
            throw new IllegalArgumentException();
        }

        if(!this.devices.containsKey(id)) {
            throw new DeviceNotFoundException();
        }

        return this.devices.get(id);
    }

    /**
     * Returns the total number of devices with type @type registered in SmartCityHub.
     *
     * @throws IllegalArgumentException in case @type is null.
     */
    public int getDeviceQuantityPerType(DeviceType type) {
        if(type == null) {
            throw new IllegalArgumentException();
        }

        int sum = 0;
        for(Iterator<String> it = this.devices.keySet().iterator(); it.hasNext();) {
            if(it.next().startsWith(type.getShortName())) {
                sum++;
            }
        }
        return sum;
    }

    /**
     * Returns a collection of IDs of the top @n devices which consumed
     * the most power from the time of their installation until now.
     *
     * The total power consumption of a device is calculated by the hours elapsed
     * between the two LocalDateTime-s: the installation time and the current time (now)
     * multiplied by the stated nominal hourly power consumption of the device.
     *
     * If @n exceeds the total number of devices, return all devices available sorted by the given criterion.
     * @throws IllegalArgumentException in case @n is a negative number.
     */
    public static Comparator<SmartDevice> powerConsumptionDecComparator = new Comparator<SmartDevice>() {
        @Override
        public int compare(SmartDevice o1, SmartDevice o2) {
            return (int) (o2.getPowerConsumption() * Duration.between(o2.getInstallationDateTime(), LocalDateTime.now()).toHours()
                           - o1.getPowerConsumption() * Duration.between(o1.getInstallationDateTime(), LocalDateTime.now()).toHours());
        }
    };

    public Collection<String> getTopNDevicesByPowerConsumption(int n) {
        if(n < 0) {
            throw new IllegalArgumentException();
        }

        Set<SmartDevice> sortedDevices = new TreeSet<>(powerConsumptionDecComparator);
        sortedDevices.addAll(this.devices.values());

        Collection<String> result = new ArrayList<>();
        for(Iterator<SmartDevice> it = sortedDevices.iterator(); it.hasNext() && n > 0; --n) {
            result.add(it.next().getId());
        }

        return result;
    }

    /**
     * Returns a collection of the first @n registered devices, i.e the first @n that were added
     * in the SmartCityHub (registration != installation).
     *
     * If @n exceeds the total number of devices, return all devices available sorted by the given criterion.
     *
     * @throws IllegalArgumentException in case @n is a negative number.
     */
    public static Comparator<SmartDevice> registrationOrderComparator = new Comparator<SmartDevice>() {
        @Override
        public int compare(SmartDevice o1, SmartDevice o2) {
            return ((Device)o1).getRegNumber() - ((Device)o2).getRegNumber();
        }
    };
    public Collection<SmartDevice> getFirstNDevicesByRegistration(int n) {
        if(n < 0) {
            throw new IllegalArgumentException();
        }

        Set<SmartDevice> sortedDevices = new TreeSet<>(registrationOrderComparator);
        sortedDevices.addAll(this.devices.values());

        Collection<SmartDevice> result = new ArrayList<>();
        for(Iterator<SmartDevice> it = sortedDevices.iterator(); it.hasNext() && n > 0; --n) {
            result.add(it.next());
        }

        return result;
    }
}
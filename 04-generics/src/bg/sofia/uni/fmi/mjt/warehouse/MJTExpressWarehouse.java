package bg.sofia.uni.fmi.mjt.warehouse;

import bg.sofia.uni.fmi.mjt.warehouse.exceptions.CapacityExceededException;
import bg.sofia.uni.fmi.mjt.warehouse.exceptions.ParcelNotFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/** A class that stores parcels and delivers them.
 * @param <L> type for the label of a parcel
 * @param <P> type for the content of the parcel
 */

public class MJTExpressWarehouse<L, P> implements DeliveryServiceWarehouse<L, P> {

    private int capacity;
    private int retentionPeriod;
    private int parcelCount;

    private Map<L, P> parcels;
    private Map<L, LocalDateTime> expiry;

    public MJTExpressWarehouse(int capacity, int retentionPeriod) {
        this.capacity = capacity;
        this.retentionPeriod = retentionPeriod;
        this.parcelCount = 0;
        this.parcels = new HashMap<>();
        this.expiry = new HashMap<>();
    }

    private boolean containsExpiredParcels() {
        for (LocalDateTime date : this.expiry.values()) {
            if (date.plusDays(this.retentionPeriod).isBefore(LocalDateTime.now())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void submitParcel(L label, P parcel, LocalDateTime submissionDate) throws CapacityExceededException {
        if (label == null || parcel == null || submissionDate == null || submissionDate.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException();
        }

        if (this.parcelCount == this.capacity && !containsExpiredParcels()) {
            throw new CapacityExceededException();
        }

        if (this.parcelCount == this.capacity && containsExpiredParcels()) {
            for (L l : this.expiry.keySet()) {
                if (this.expiry.get(l).plusDays(this.retentionPeriod).isBefore(LocalDateTime.now())) {
                    this.expiry.remove(l);
                    this.parcels.remove(l);
                    this.parcelCount--;
                    break;
                }
            }
        }

        this.parcels.put(label, parcel);
        this.expiry.put(label, submissionDate);
        this.parcelCount++;
    }

    @Override
    public P getParcel(L label) {
        if (label == null) {
            throw new IllegalArgumentException();
        }

        if (!parcels.containsKey(label)) {
            return null;
        }

        return parcels.get(label);
    }

    @Override
    public P deliverParcel(L label) throws ParcelNotFoundException {
        if (label == null) {
            throw new IllegalArgumentException();
        }

        if (!parcels.containsKey(label)) {
            throw new ParcelNotFoundException();
        }

        this.parcelCount--;
        this.expiry.remove(label);
        return parcels.remove(label);
    }

    @Override
    public double getWarehouseSpaceLeft() {
        double value = (double) (this.capacity - this.parcelCount) / (double) this.capacity;
        return (double) Math.round(value * 100d) / 100d;
    }

    @Override
    public Map<L, P> getWarehouseItems() {
        return this.parcels;
    }

    @Override
    public Map<L, P> deliverParcelsSubmittedBefore(LocalDateTime before) {
        if (before == null) {
            throw new IllegalArgumentException();
        }

        Map<L, P> result = new HashMap<>();
        L neededLabel = null;
        for (L l : this.expiry.keySet()) {
            if (this.expiry.get(l).isBefore(before)) {
                neededLabel = l;
                P p = this.parcels.get(l);
                result.put(l, p);
                this.parcels.remove(l);
                this.parcelCount--;
            }
        }

        if (neededLabel == null) {
            return result;
        }

        this.expiry.remove(neededLabel);
        return result;
    }

    @Override
    public Map<L, P> deliverParcelsSubmittedAfter(LocalDateTime after) {
        if (after == null) {
            throw new IllegalArgumentException();
        }

        Map<L, P> result = new HashMap<>();
        L neededLabel = null;
        for (L l : this.expiry.keySet()) {
            if (this.expiry.get(l).isAfter(after)) {
                neededLabel = l;
                P p = this.parcels.get(l);
                result.put(l, p);
                this.parcels.remove(l);
                this.parcelCount--;
            }
        }

        if (neededLabel == null) {
            return result;
        }

        this.expiry.remove(neededLabel);
        return result;
    }
}

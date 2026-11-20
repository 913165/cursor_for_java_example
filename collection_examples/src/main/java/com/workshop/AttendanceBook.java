package com.workshop;

import java.util.HashSet;

/**
 * Unique attendees live in one HashSet. A second HashSet records anyone
 * who tried to check in again. HashSet.add returns false for a duplicate,
 * which is how a repeat arrival is detected.
 */
public class AttendanceBook {
    private final HashSet<Attendee> checkedIn = new HashSet<>();
    private final HashSet<Attendee> repeatArrivals = new HashSet<>();
    private int repeatCheckIns;

    /** @return true when this is the attendee's first check-in */
    public boolean checkIn(Attendee attendee) {
        if (checkedIn.add(attendee)) {
            return true;
        }
        repeatArrivals.add(attendee);
        repeatCheckIns++;
        return false;
    }

    public boolean hasCheckedIn(Attendee attendee) {
        return checkedIn.contains(attendee);
    }

    public int uniqueCount() {
        return checkedIn.size();
    }

    public int repeatCheckInCount() {
        return repeatCheckIns;
    }

    public HashSet<Attendee> attendees() {
        return new HashSet<>(checkedIn);
    }

    public HashSet<Attendee> repeatArrivals() {
        return new HashSet<>(repeatArrivals);
    }
}

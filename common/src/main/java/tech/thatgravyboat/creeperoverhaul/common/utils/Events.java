package tech.thatgravyboat.creeperoverhaul.common.utils;

import java.util.Calendar;
import java.util.function.BooleanSupplier;

public enum Events {
    CHRISTMAS(() -> matchMonth(Calendar.DECEMBER) && matchDays(24, 26)),
    HALLOWEEN(() -> matchMonth(Calendar.OCTOBER) && matchDays(29, 31)),
    ST_PATRICKS_DAY(() -> matchMonth(Calendar.MARCH) && matchDays(16, 18)),
    NONE(() -> false);

    private final BooleanSupplier checker;

    Events(BooleanSupplier checker) {
        this.checker = checker;
    }

    private boolean isDay() {
        return checker.getAsBoolean();
    }

    public static Events getCurrentEvent() {
        for (Events value : Events.values()) {
            if (value.isDay()) return value;
        }
        return Events.NONE;
    }

    private static boolean matchMonth(int month) {
        return Calendar.getInstance().get(Calendar.MONTH) == month;
    }

    private static boolean matchDays(int start, int end) {
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        return day >= start && day <= end;
    }
}

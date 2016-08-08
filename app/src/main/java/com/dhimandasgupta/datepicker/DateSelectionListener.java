package com.dhimandasgupta.datepicker;

/**
 * Created by dhimandasgupta on 09/08/16.
 */
public interface DateSelectionListener {
    void onMonthSelected(Month month);
    void onDaySelected(int day);
    void onYearSelected(int year);
}

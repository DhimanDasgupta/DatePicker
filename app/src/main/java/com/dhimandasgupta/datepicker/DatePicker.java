package com.dhimandasgupta.datepicker;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by murali_m on 08/08/16.
 */
public class DatePicker extends LinearLayout implements DateSelectionListener {
    private static int MONTH_DURATION = 10;
    
    private int mCurrentMonth;
    private int mCurrentDay;
    private int mCurrentYear;

    private int mCurrentSelecedMonth;
    private int mCurrentSelectedDay;
    private int mCurrentSelectedYear;

    private RecyclerView mMonthRecyclerView;
    private TextView mMonthTextView;

    private RecyclerView mDayRecylerView;
    private TextView mDayTextView;

    private RecyclerView mYearRecyclerView;
    private TextView mYearTextView;

    private Month[] mMonths = new Month[12];

    private boolean mDateSelected = false;

    public DatePicker(Context context) {
        super(context);
    }

    public DatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DatePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DatePicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        setupMonthDayYear();
        inflateLayout();
        setupViews();
    }

    private void setupMonthDayYear() {
        final Calendar calendar = Calendar.getInstance();

        mCurrentDay = calendar.get(Calendar.DAY_OF_MONTH);
        mCurrentMonth = calendar.get(Calendar.MONTH);
        mCurrentYear = calendar.get(Calendar.YEAR);
    }

    private void inflateLayout() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View view = inflater.inflate(R.layout.include_date_picker, this, false);
        removeAllViews();
        addView(view);

        mMonthRecyclerView = (RecyclerView) view.findViewById(R.id.month_recycler_view);
        mMonthRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mMonthTextView = (TextView) view.findViewById(R.id.month_text_view);

        mDayRecylerView = (RecyclerView) view.findViewById(R.id.day_recycler_view);
        mDayRecylerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mDayTextView = (TextView) view.findViewById(R.id.day_text_view);

        mYearRecyclerView = (RecyclerView) view.findViewById(R.id.year_recycler_view);
        mYearRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mYearTextView = (TextView) view.findViewById(R.id.year_text_view);
    }

    private void setupViews() {
        setMonthSelectionMode();
    }

    private void setMonthSelectionMode() {
        mMonthTextView.setVisibility(GONE);
        mMonthRecyclerView.setAdapter(new MonthAdapter(getMonths(), this));
        mMonthRecyclerView.setVisibility(VISIBLE);

        mDayTextView.setVisibility(GONE);
        mDayRecylerView.setVisibility(GONE);

        mYearTextView.setVisibility(GONE);
        mYearRecyclerView.setVisibility(GONE);

        mDateSelected = false;
    }

    private void setDaySelectionMode() {
        mMonthTextView.setVisibility(VISIBLE);
        mMonthRecyclerView.setVisibility(GONE);

        mDayTextView.setVisibility(GONE);
        mDayRecylerView.setAdapter(new DayAdapter(getDays(), this));
        mDayRecylerView.setVisibility(VISIBLE);

        mYearTextView.setVisibility(GONE);
        mYearRecyclerView.setVisibility(GONE);

        mDateSelected = false;
    }

    private void setYearSelectionMode() {
        mMonthTextView.setVisibility(VISIBLE);
        mMonthRecyclerView.setVisibility(GONE);

        mDayTextView.setVisibility(VISIBLE);
        mDayRecylerView.setVisibility(GONE);

        mYearTextView.setVisibility(GONE);
        mYearRecyclerView.setAdapter(new YearAdapter(getYears(), this));
        mYearRecyclerView.setVisibility(VISIBLE);
    }

    private Month[] getMonths() {
        for (int i = 0; i < 12; i++) {
            mMonths[i] = new Month(i, getMonthsFromCurrentMonth(i));
        }

        return mMonths;
    }

    private String getMonthsFromCurrentMonth(int monthCode) {
        switch (monthCode) {
            case 0:
                return "JAN";

            case 1:
                return "FEB";

            case 2:
                return "MAR";

            case 3:
                return "APR";

            case 4:
                return "MAY";

            case 5:
                return "JUN";

            case 6:
                return "JUL";

            case 7:
                return "AUG";

            case 8:
                return "SEP";

            case 9:
                return "OCT";

            case 10:
                return "NOV";

            case 11:
                return "DEC";

            default:
                throw new RuntimeException("Invalid Month code");
        }
    }

    private int getNumberOfDaysInMonth() {
        switch (mCurrentSelecedMonth) {
            case 0:
            case 2:
            case 4:
            case 6:
            case 7:
            case 9:
            case 11:
                return 31;

            case 1:
                if (mCurrentYear%4== 0) {
                    return 29;
                } else {
                    return 28;
                }

            case 3:
            case 5:
            case 8:
            case 10:
                return 30;

            default:
                throw new RuntimeException("Invalid Month Index");
        }
    }

    private int[] getDays() {
        if (mCurrentSelecedMonth == mCurrentMonth) {
            final int[] days = new int[getNumberOfDaysInMonth() - mCurrentDay];

            int currentDay = mCurrentDay + 1;
            for (int i = 0; i < getNumberOfDaysInMonth() - mCurrentDay; i++) {
                days[i] = currentDay;
                currentDay++;
            }

            return days;
        } else {
            final int[] days = new int[getNumberOfDaysInMonth()];

            for (int i = 0; i < getNumberOfDaysInMonth(); i++) {
                days[i] = i + 1;
            }

            return days;
        }
    }

    public int getDiffYears() {
        Calendar calendarOne = Calendar.getInstance();
        calendarOne.set(Calendar.MONTH, mCurrentSelecedMonth);

        Calendar calendarTwo = Calendar.getInstance();
        calendarOne.set(Calendar.MONTH, mCurrentSelecedMonth + MONTH_DURATION);

        return calendarTwo.get(Calendar.YEAR) - calendarOne.get(Calendar.YEAR);
    }

    private int[] getYears() {
        if (mCurrentSelecedMonth < mCurrentMonth) {
            final int[] years =  new int[1];
            years[0] = mCurrentYear + 1;
            return years;
        } else {
            final int[] years =  new int[1];
            years[0] = mCurrentYear;
            return years;
        }
    }

    @Override
    public void onMonthSelected(Month month) {
        mCurrentSelecedMonth = month.getCode();
        mMonthTextView.setText(month.getName());
        setDaySelectionMode();
    }

    @Override
    public void onDaySelected(int day) {
        mCurrentSelectedDay = day;
        mDayTextView.setText(String.valueOf(mCurrentSelectedDay));
        setYearSelectionMode();
    }

    @Override
    public void onYearSelected(int year) {
        mCurrentSelectedYear = year;
        mYearTextView.setText(String.valueOf(mCurrentSelectedYear));
        mYearTextView.setVisibility(VISIBLE);
        mYearRecyclerView.setVisibility(GONE);


        mDateSelected = true;

        if (mDateSelected) {
            // Date Selected
        }
    }
}

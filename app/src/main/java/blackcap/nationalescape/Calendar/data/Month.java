package blackcap.nationalescape.Calendar.data;


public class Month {
    public int year;
    public Week[] weeks;
    public int value;
    public int lastDay;

    public Month(int value, int year) {
        this.year = year;
        this.value = value;
        weeks = WeekManager.getWeeks(value, year);
        lastDay = weeks[weeks.length-1].getLastDay();
    }

}


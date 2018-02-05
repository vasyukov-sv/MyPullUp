package home.my.mypullup.obj;

import java.util.List;

public class Analitic {
    private List<Attempt> attemptList;
    private Double avgWeek;
    private Double avgMonth;
    private Double avgAll;

    private Integer maxWeek;
    private Integer maxMonth;
    private Integer maxAll;

    public Analitic() {
    }

    public List<Attempt> getAttemptList() {
        return attemptList;
    }

    public Analitic setAttemptList(List<Attempt> attemptList) {
        this.attemptList = attemptList;
        return this;
    }

    public Double getAvgMonth() {
        return avgMonth;
    }

    public Analitic setAvgMonth(Double avgMonth) {
        this.avgMonth = avgMonth;
        return this;
    }

    public Double getAvgWeek() {
        return avgWeek;
    }

    public Analitic setAvgWeek(Double avgWeek) {
        this.avgWeek = avgWeek;
        return this;
    }

    public Double getAvgAll() {
        return avgAll;
    }

    public Analitic setAvgAll(Double avgAll) {
        this.avgAll = avgAll;
        return this;
    }

    public Integer getMaxMonth() {
        return maxMonth;
    }

    public Analitic setMaxMonth(Integer maxMonth) {
        this.maxMonth = maxMonth;
        return this;
    }

    public Integer getMaxWeek() {
        return maxWeek;
    }

    public Analitic setMaxWeek(Integer maxWeek) {
        this.maxWeek = maxWeek;
        return this;
    }

    public Integer getMaxAll() {
        return maxAll;
    }

    public Analitic setMaxAll(Integer maxAll) {
        this.maxAll = maxAll;
        return this;
    }
}

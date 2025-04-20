package com.skypro.teamwork3.dto;

public class StatisticsListElement {
    private final long ruleId;
    private final int count;

    public StatisticsListElement(long ruleId, int count) {
        this.ruleId = ruleId;
        this.count = count;
    }

    public long getRule_id() {
        return ruleId;
    }

    public int getCount() {
        return count;
    }
}

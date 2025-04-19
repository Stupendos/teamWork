package com.skypro.teamwork3.dto;

public class StatisticsListElement {
    private final long rule_id;
    private final int count;

    public StatisticsListElement(long rule_id, int count) {
        this.rule_id = rule_id;
        this.count = count;
    }

    public long getRule_id() {
        return rule_id;
    }

    public int getCount() {
        return count;
    }
}

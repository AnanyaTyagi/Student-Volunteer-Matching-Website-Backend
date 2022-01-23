package com.cntrl.alt.debt.gyandaan.service;

import lombok.Getter;

@Getter
public enum Days {

    MON, TUES, WED, THURS,FRI,SAT,SUN;

    @Override
    public String toString() {
       String firstLetter = this.name().substring(0,1).toUpperCase();
       String remaining = this.name().substring(1);
       return firstLetter + remaining;
    }
}

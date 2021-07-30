package com.ljy.flightreservation.user.domain.value;

public enum UserState {
    CREATED(0), DELETED(1);

    private final int state;

    UserState(int state){
        this.state = state;
    }

    public boolean isDeleted() {
        return this.state == 1;
    }

    public boolean isCreated() {
        return this.state == 0;
    }
}

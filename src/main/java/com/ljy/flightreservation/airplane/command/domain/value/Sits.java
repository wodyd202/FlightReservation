package com.ljy.flightreservation.airplane.command.domain.value;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Embeddable
public class Sits {
    @ElementCollection
    @CollectionTable(name = "airplane_sits",
                     joinColumns = @JoinColumn(name = "airplaneCode",
                                               referencedColumnName = "code"))
    private List<Sit> list;

    protected Sits(){}

    public Sits(List<Sit> list) {
        Collections.sort(list);
        this.list = list;
    }

    public List<Sit> get() {
        return list;
    }

    public int getTotalSit() {
        return list.size();
    }
}

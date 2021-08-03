package com.ljy.flightreservation.airplane.domain.value;

import com.ljy.flightreservation.airplane.domain.exception.InvalidSitException;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Embeddable
public class Sits {
    @ElementCollection
    @CollectionTable(name = "airplane_sits",
                     joinColumns = @JoinColumn(name = "airplaneCode",
                                               referencedColumnName = "code"))
    private List<Sit> list;

    protected Sits(){}

    public Sits(List<Sit> list) {
        sitListValidation(list);
        Collections.sort(list);
        this.list = list;
    }

    private void sitListValidation(List<Sit> list) {
        verifyNotEmptySits(list);
        verifyCheckDupSitCode(list);
        list.forEach(this::sitCodeValidation);
    }

    private void verifyNotEmptySits(List<Sit> list) {
        if(Objects.isNull(list) || list.isEmpty()){
            throw new InvalidSitException("sit list must not be empty");
        }
    }

    private void verifyCheckDupSitCode(List<Sit> list) {
        Set<Sit> setSits = list.stream().collect(Collectors.toSet());
        if(setSits.size() != list.size()){
            throw new InvalidSitException("not allowed dup sit code");
        }
    }

    private void sitCodeValidation(Sit sit){
        String code = sit.getCode();
        verifyNotEmptySitCode(code);
        verifyUpperCaseAlphabetFirstChar(code);
        verifyIsNumberOtherFirstChar(code);
    }

    private void verifyNotEmptySitCode(String code) {
        if(!StringUtils.hasText(code)){
            throw new InvalidSitException("sit code must not be empty");
        }
    }

    private void verifyUpperCaseAlphabetFirstChar(String code) {
        char firstChar = code.charAt(0);
        if(firstChar < 'A' || firstChar > 'Z'){
            throw new InvalidSitException("first char of sit code must be uppercase alphabet");
        }
    }

    private void verifyIsNumberOtherFirstChar(String code) {
        String backCode = code.substring(1, code.length());
        try{
            Integer.parseInt(backCode);
        }catch (NumberFormatException e){
            throw new InvalidSitException("back char of sit code must be number");
        }
    }


    public List<Sit> get() {
        return list;
    }

    public int getTotalSit() {
        return list.size();
    }
}

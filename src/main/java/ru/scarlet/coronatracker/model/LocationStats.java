package ru.scarlet.coronatracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationStats {
    private String State;
    private String country;
    private int latest;
    private int diffFromYest;
    private int delta;
}

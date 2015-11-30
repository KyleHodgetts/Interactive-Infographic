package com.kylehodgetts.interactiveinfographic.model;

/**
 * @author Kyle Hodgetts
 * @version 1.0
 * Models Employment data entry for one year
 */
public class EmploymentEntry extends DataEntry {

    /**
     * Default Constructor
     * @param indicator     Data's indicator
     * @param value         Value for amount invested in education in a given year
     * @param year          Corresponding year
     * @param country       The country the data is related to
     * @param countryCode   Shorthand code for the given country
     */
    public EmploymentEntry(String indicator, String countryCode, String country, int year, double value) {
        super(indicator, countryCode, country, year, value);
    }
}
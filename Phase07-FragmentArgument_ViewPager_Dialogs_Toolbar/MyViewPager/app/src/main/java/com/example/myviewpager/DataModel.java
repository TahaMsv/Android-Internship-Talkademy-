package com.example.myviewpager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

class DataModel {

    private static DataModel single_instance = null;
    private static Map<String, CountryModel> allCountries;
    private static Map<String, CountryModel> asianCountries;
    private static Map<String, CountryModel> africanCountries;
    private static Map<String, CountryModel> americanCountries;
    private static Map<String, CountryModel> australianCountries;
    private static Map<String, CountryModel> europeanCountries;

    private static boolean isAsianAdded = false;
    private static boolean isAmericanAdded = false;
    private static boolean isAfricanAdded = false;
    private static boolean isAustralianAdded = false;
    private static boolean isEuropeanAdded = false;

    public static boolean isIsAsianAdded() {
        return isAsianAdded;
    }

    public static void setIsAsianAdded(boolean isAsianAdded) {
        DataModel.isAsianAdded = isAsianAdded;
    }

    public static boolean isIsAmericanAdded() {
        return isAmericanAdded;
    }

    public static void setIsAmericanAdded(boolean isAmericanAdded) {
        DataModel.isAmericanAdded = isAmericanAdded;
    }

    public static boolean isIsAfricanAdded() {
        return isAfricanAdded;
    }

    public static void setIsAfricanAdded(boolean isAfricanAdded) {
        DataModel.isAfricanAdded = isAfricanAdded;
    }

    public static boolean isIsAustralianAdded() {
        return isAustralianAdded;
    }

    public static void setIsAustralianAdded(boolean isAustralianAdded) {
        DataModel.isAustralianAdded = isAustralianAdded;
    }

    public static boolean isIsEuropeanAdded() {
        return isEuropeanAdded;
    }

    public static void setIsEuropeanAdded(boolean isEuropeanAdded) {
        DataModel.isEuropeanAdded = isEuropeanAdded;
    }

    private DataModel() {
        allCountries = new HashMap<String, CountryModel>() {{
            put("1", new CountryModel("Iran", "1", Continent.Asia));
            put("2", new CountryModel("America", "2", Continent.America));
            put("3", new CountryModel("Germany", "3", Continent.Europe));


        }};
        europeanCountries = new HashMap<String, CountryModel>() {{
            put("5", new CountryModel("Spain", "5", Continent.Asia));
        }};
        asianCountries = new HashMap<String, CountryModel>() {{
            put("6", new CountryModel("China", "6", Continent.Asia));
        }};
        africanCountries = new HashMap<String, CountryModel>() {{
            put("7", new CountryModel("Egypt", "7", Continent.Asia));
        }};
        americanCountries = new HashMap<String, CountryModel>() {{
            put("7", new CountryModel("Canada", "8", Continent.Asia));
        }};
        australianCountries = new HashMap<String, CountryModel>() {{
            put("4", new CountryModel("Australia", "4", Continent.Australia));
        }};

    }


    public static DataModel getInstance() {
        if (single_instance == null)
            single_instance = new DataModel();

        return single_instance;
    }

    public static void addAfricans() {
        allCountries.putAll(africanCountries);
    }

    public static void addAmericans() {
        allCountries.putAll(americanCountries);
    }

    public static void addEuropeans() {
        allCountries.putAll(europeanCountries);
    }

    public static void addAustralians() {
        allCountries.putAll(australianCountries);
    }

    public static void addAsians() {
        allCountries.putAll(asianCountries);
    }

    public static void removeAfricans() {
        allCountries.keySet().removeAll(africanCountries.keySet());
    }

    public static void removeAmericans() {
        allCountries.keySet().removeAll(americanCountries.keySet());
    }

    public static void removeEuropeans() {
        allCountries.keySet().removeAll(europeanCountries.keySet());
    }

    public static void removeAustralians() {
        allCountries.keySet().removeAll(australianCountries.keySet());
    }

    public static void removeAsians() {
        allCountries.keySet().removeAll(asianCountries.keySet());
    }

    public static List<CountryModel> getAllCountries() {
        List<CountryModel> listToReturn = new ArrayList<>();
        Set<String> keys = allCountries.keySet();
        for (String key : keys) {
            listToReturn.add(allCountries.get(key));
        }
        return listToReturn;
    }

    public static CountryModel getCountry(String id) {
        return allCountries.get(id);
    }
}
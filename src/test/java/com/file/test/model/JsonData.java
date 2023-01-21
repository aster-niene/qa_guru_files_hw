package com.file.test.model;

import java.util.ArrayList;


public class JsonData {

    public int id;
    public String name;
    public boolean work;
    public ArrayList<Object> topics;
    public Address address;

    public static class Address {
        public String city;
        public String country;
    }
}
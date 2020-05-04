package com.example.loanandrepay.company;

import com.example.loanandrepay.StatusPending;

import java.io.Serializable;

public class Request implements Serializable {

    private int Id;
    private String Company;
    private String FirstName;
    private String LastName;
    private String Email;
    private int Age;
    private String Phone;
    private String StreetName;
    private String HouseNumber;
    private String CityName;
    private int PostCode;
    private double Amount;
    private String PayWithIn;
    private double MonthlyPayment;
    private int Status;

    public Request(int id, String company, String firstName, String lastName, String email, int age, String phone, String streetName, String houseNumber, String cityName, int postCode, double amount, String payWithIn, double monthlyPayment, int status) {
        Id = id;
        Company = company;
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        Age = age;
        Phone = phone;
        StreetName = streetName;
        HouseNumber = houseNumber;
        CityName = cityName;
        PostCode = postCode;
        Amount = amount;
        PayWithIn = payWithIn;
        MonthlyPayment = monthlyPayment;
        Status = status;

    }

    public int getId() {
        return Id;
    }

    public String getCompany() {
        return Company;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getEmail() {
        return Email;
    }

    public int getAge() {
        return Age;
    }

    public String getPhone() {
        return Phone;
    }

    public String getStreetName() {
        return StreetName;
    }


    public String getHouseNumber() {
        return HouseNumber;
    }


    public String getCityName() {
        return CityName;
    }

    public int getPostCode() {
        return PostCode;
    }

    public double getAmount() {
        return Amount;
    }

    public String getPayWithIn() {
        return PayWithIn;
    }

    public double getMonthlyPayment() {
        return MonthlyPayment;
    }

    public int getStatus() {
        return Status;
    }

    public String toString() {

        return "To: " + Company + "\n" + "From: " + FirstName + " " + LastName;
    }
}
// return "To: " + Company + "\n" +"From: " + FirstName + " " +LastName + "\n" + Email + "\n" + Phone + "\n" + StreetName + "\n" + HouseNumber + "\n" + CityName + "\n" + PostCode + "\n" + Amount + "\n" + PayWithIn + "\n" + MonthlyPayment;

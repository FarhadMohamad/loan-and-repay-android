package com.example.loanandrepay.client;

import java.io.Serializable;

public class StatusPending  implements Serializable {


    //For getting lender's requests
    //private String Token;
    public String Company;
    public String Status;



    public StatusPending (String company, String status)

    {
        // this.Token = token;
        this.Company = company;
        this.Status = status;


    }

    public StatusPending(){
    }

//        public String getCompanyName()
//        {
//            return CompanyName;
//        }

//        public String getRepayWithin() { return repayWithin; }
//        public String getStartingDate() { return startingDate; }
//        public String getDateCreated() { return dateCreated; }



    public String toString() {

        return "Company Name: " + Company + "\n" + "Status: " + Status;
    }



}

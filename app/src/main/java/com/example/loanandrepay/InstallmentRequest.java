package com.example.loanandrepay;

import java.io.Serializable;

public class InstallmentRequest implements Serializable{



        //For getting lender's requests
        //private String Token;
        private int Status;
        private String Company;


        public InstallmentRequest(String company, int status)

        {
           // this.Token = token;
            this.Status = status;
            this.Company = company;

        }

        public InstallmentRequest(){
        }
        public String getStatus()
        {
            if (Status == 0) {

                return "Pending";
            }
            else if (Status == 1)
            {

                return "Accepted";
            }
            else if (Status == 2)
            {

                return "Rejected";
            }

             return "";
        }
//        public String getCompanyName()
//        {
//            return CompanyName;
//        }

//        public String getRepayWithin() { return repayWithin; }
//        public String getStartingDate() { return startingDate; }
//        public String getDateCreated() { return dateCreated; }



        public String toString() {

            return "Company Name: " + Company + "\n";
        }



    }



package com.example.taski;

public class UserHelperClassEvent {

    private String mail;
    private String ename;
    private String edesc;
    private String edate;
    private String eloc;
    private String timein;
    private String timeout;
    private String ecode;


    public UserHelperClassEvent(String id, String fn, String add, String db, String pn, String nn, String np, String ec) {


        this.mail = id;
        this.ename = fn;
        this.edesc = add;
        this.edate = db;
        this.eloc = pn;
        this.timein = nn;
        this.timeout = np;
        this.ecode = ec;
    }



    public void setId(String id) {
        this.mail = id;
    }
    public String getId() {
        return mail;
    }


    public void setFname(String fname) {
        this.ename = fname;
    }
    public String getFname() {
        return ename;
    }

    public void setAddrs(String addrs) {
        this.edesc = addrs;
    }
    public String getAddrs() { return edesc; }

    public void setDob(String dob) {
        this.edate = dob;
    }
    public String getDob() { return edate; }

    public void setPnum(String pnum) {
        this.eloc = pnum;
    }
    public String getPnum() { return eloc; }

    public void setNextn(String nextn) {
        this.timein = nextn;
    }
    public String getNextn() { return timein; }

    public void setNextp(String nextp) {
        this.timeout = nextp;
    }
    public String getNextp() { return timeout; }

    public void setEcode(String ec) {
        this.ecode = ec;
    }
    public String getEcode() { return ecode; }

    public  UserHelperClassEvent(String ecode /*,String fname*/){
        this.ecode= ecode;
      //  this.ename= fname;
    }
    public UserHelperClassEvent(){

    }
    public String toString(){
        return this.ecode + "___";
    }


}


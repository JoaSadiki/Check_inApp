package com.example.taski;

public class UserHelperClassp {

    private String idnum;
    private String fname;
    private String addrs;
   private String tin;
    private String pnum;
    private String nextn;
    private String nextp;
    private String email;
    private String seat;
    private String code;
    public UserHelperClassp(String id, String fn, String add, String pn, String nn, String np, String mail, String st, String tii, String co) {


        this.idnum = id;
        this.fname = fn;
        this.addrs = add;
        this.tin = tii;
        this.pnum = pn;
        this.nextn = nn;
        this.nextp = np;
        this.seat = st;
        this.email = mail;
        this.code = co;

    }



    public void setId(String id) {
        this.idnum = id;
    }
    public String getId() {
        return idnum;
    }


    public void setFname(String fname) {
        this.fname = fname;
    }
    public String getFname() {
        return fname;
    }

    public void setAddrs(String addrs) {
        this.addrs = addrs;
    }
    public String getAddrs() { return addrs; }


    public void setPnum(String pnum) {
        this.pnum = pnum;
    }
    public String getPnum() { return pnum; }

    public void setNextn(String nextn) {
        this.nextn = nextn;
    }
    public String getNextn() { return nextn; }

    public void setNextp(String nextp) {
        this.nextp = nextp;
    }
    public String getNextp() { return nextp; }

    public void setSeat(String seat) {
        this.seat = seat;
    }
    public String getSeat() {
        return seat;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() { return email; }

    public void setTime(String time) {
        this.tin = time;
    }
    public String getTime() { return tin; }

    public void setcode(String cod) {
        this.code = cod;
    }
    public String getCode() { return code; }






}





package com.example.taski;

public class UserHelperClass {

    private String idnum;
    private String fname;
    private String title;
    private String addrs;
    private String dob;
    private String pnum;
    private String nextn;
    private String nextp;
    private String email;

    public UserHelperClass(String id, String fn, String add, String db, String pn, String nn, String np, String tp, String mail) {


        this.idnum = id;
        this.fname = fn;
        this.addrs = add;
        this.dob = db;
        this.pnum = pn;
        this.nextn = nn;
        this.nextp = np;
        this.title = tp;
        this.email = mail;

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

    public void setDob(String dob) {
        this.dob = dob;
    }
    public String getDob() { return dob; }

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

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() { return email; }






}



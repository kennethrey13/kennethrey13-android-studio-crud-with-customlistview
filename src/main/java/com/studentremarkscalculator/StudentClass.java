package com.studentremarkscalculator;

/**
 * Created by Kenneth Rey on 12/1/2016.
 */
public class StudentClass {

    private int sid;
    private String firstName;
    private String lastName;
    private int PG;
    private int MG;
    private int FG;
    private int average;
    private String remarks;
    private String program;
    public StudentClass() {

        this.sid = 0;
        this.firstName = "";
        this.lastName = "";
        this.PG = 0;
        this.MG = 0;
        this.FG = 0;
        this.average = 0;
        this.remarks = "";
        this.program ="";
    }

    public StudentClass(int sid, String firstName, String lastName, int PG, int MG, int FG, int average, String remarks, String program) {
        this.sid = sid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.PG = PG;
        this.MG = MG;
        this.FG = FG;
        this.average = average;
        this.remarks = remarks;
        this.program = program;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getPG() {
        return PG;
    }

    public void setPG(int PG) {
        this.PG = PG;
    }

    public int getMG() {
        return MG;
    }

    public void setMG(int MG) {
        this.MG = MG;
    }

    public int getFG() {
        return FG;
    }

    public void setFG(int FG) {
        this.FG = FG;
    }

    public int getAverage() {
        return average;
    }

    public void setAverage(int average) {
        this.average = average;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "StudentClass{" +
                "sid=" + sid +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", PG=" + PG +
                ", MG=" + MG +
                ", FG=" + FG +
                ", average=" + average +
                ", remarks='" + remarks + '\'' +
                ", program='" + program + '\'' +
                '}';
    }
}

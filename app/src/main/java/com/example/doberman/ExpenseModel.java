package com.example.doberman;

import java.util.Date;

public class ExpenseModel {
    //dateasid, no, multiple of the same day. add id.

    private int id;
    private String expenseName;
    private String expensedate;
    private Float expenseamount;

    public ExpenseModel(int id, String expenseName, String expensedate, float expenseamount) {
        this.id = id;
        this.expenseName = expenseName;
        this.expensedate = expensedate;
        this.expenseamount = expenseamount;
    }

    public ExpenseModel() {
    }

    @Override
    public String toString() {
        return "ExpenseModel{" +
                "id=" + id +
                ", expenseName='" + expenseName + '\'' +
                ", expensedate=" + expensedate +
                ", expenseamount=" + expenseamount +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public String getExpensedate() {
        return expensedate;
    }

    public void setExpensedate(String expensedate) {
        this.expensedate = expensedate;
    }

    public float getExpenseamount() {
        return expenseamount;
    }

    public void setExpenseamount(float expenseamount) {
        this.expenseamount = expenseamount;
    }
}

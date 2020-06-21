package com.lyk.data.model;

public class Quarter {
    private String year;//年份
    private String qFull;//季度
    private int qShort;//季度简写

    public Quarter(String data) {
        try {
            String[] arr = data.split("-");
            year = arr[0];
            qFull = arr[1];
            qShort = Integer.parseInt(qFull.charAt(1)+"");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getYear() {
        return year;
    }

    public String getQFull() {
        return qFull;
    }

    public int getQShort() {
        return qShort;
    }

}

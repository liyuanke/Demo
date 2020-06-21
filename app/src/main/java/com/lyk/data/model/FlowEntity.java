package com.lyk.data.model;

public class FlowEntity implements Comparable<FlowEntity> {
    private String year;
    private double[] data = new double[4];
    private Double total = null;

    public FlowEntity(String year) {
        this.year = year;
    }

    /**
     * 给这一年各个季度赋值
     *
     * @param q
     * @param val
     */
    public void setValue(int q, double val) {
        if (q >= 0 && q < data.length) {
            data[q] = val;
        }
    }

    /**
     * 获取总流量
     *
     * @return
     */
    public double getTotal() {
        if (total == null) {
            total = new Double(0);
            for (double val : data) {
                total += val;
            }
        }
        return total;
    }

    public String getYear() {
        return year;
    }

    /**
     * 是否存在下降的季度
     *
     * @return
     */
    public boolean isExistDecline() {
        for (int i = 1; i < data.length; i++) {
            double preQ = data[i - 1];
            double curQ = data[i];
            if (Double.compare(preQ, curQ) > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int compareTo(FlowEntity o) {
        return year.compareTo(o.year);
    }
}

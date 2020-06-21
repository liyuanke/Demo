package com.lyk.data.model;

public class Result<T> {

    /**
     * help : https://data.gov.sg/api/3/action/help_show?name=datastore_search
     * success : true
     * result : {"resource_id":"a807b7ab-6cad-4aa6-87d0-e283a7353a0f","fields":[{"type":"int4","id":"_id"},{"type":"text","id":"quarter"},{"type":"numeric","id":"volume_of_mobile_data"}],"records":[{"volume_of_mobile_data":"0.000384","quarter":"2004-Q3","_id":1},{"volume_of_mobile_data":"0.000543","quarter":"2004-Q4","_id":2},{"volume_of_mobile_data":"0.00062","quarter":"2005-Q1","_id":3},{"volume_of_mobile_data":"0.000634","quarter":"2005-Q2","_id":4},{"volume_of_mobile_data":"0.000718","quarter":"2005-Q3","_id":5}],"_links":{"start":"/api/action/datastore_search?limit=5&resource_id=a807b7ab-6cad-4aa6-87d0-e283a7353a0f","next":"/api/action/datastore_search?offset=5&limit=5&resource_id=a807b7ab-6cad-4aa6-87d0-e283a7353a0f"},"limit":5,"total":59}
     */

    private String help;
    private boolean success;
    private T result;

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}

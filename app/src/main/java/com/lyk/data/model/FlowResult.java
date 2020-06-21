package com.lyk.data.model;

import java.util.List;

public class FlowResult {

    /**
     * resource_id : a807b7ab-6cad-4aa6-87d0-e283a7353a0f
     * fields : [{"type":"int4","id":"_id"},{"type":"text","id":"quarter"},{"type":"numeric","id":"volume_of_mobile_data"}]
     * records : [{"volume_of_mobile_data":"0.000384","quarter":"2004-Q3","_id":1},{"volume_of_mobile_data":"0.000543","quarter":"2004-Q4","_id":2},{"volume_of_mobile_data":"0.00062","quarter":"2005-Q1","_id":3},{"volume_of_mobile_data":"0.000634","quarter":"2005-Q2","_id":4},{"volume_of_mobile_data":"0.000718","quarter":"2005-Q3","_id":5},{"volume_of_mobile_data":"0.000801","quarter":"2005-Q4","_id":6},{"volume_of_mobile_data":"0.00089","quarter":"2006-Q1","_id":7},{"volume_of_mobile_data":"0.001189","quarter":"2006-Q2","_id":8},{"volume_of_mobile_data":"0.001735","quarter":"2006-Q3","_id":9},{"volume_of_mobile_data":"0.003323","quarter":"2006-Q4","_id":10},{"volume_of_mobile_data":"0.012635","quarter":"2007-Q1","_id":11},{"volume_of_mobile_data":"0.029992","quarter":"2007-Q2","_id":12},{"volume_of_mobile_data":"0.053584","quarter":"2007-Q3","_id":13},{"volume_of_mobile_data":"0.100934","quarter":"2007-Q4","_id":14},{"volume_of_mobile_data":"0.171586","quarter":"2008-Q1","_id":15},{"volume_of_mobile_data":"0.248899","quarter":"2008-Q2","_id":16},{"volume_of_mobile_data":"0.439655","quarter":"2008-Q3","_id":17},{"volume_of_mobile_data":"0.683579","quarter":"2008-Q4","_id":18},{"volume_of_mobile_data":"1.066517","quarter":"2009-Q1","_id":19},{"volume_of_mobile_data":"1.357248","quarter":"2009-Q2","_id":20}]
     * _links : {"start":"/api/action/datastore_search?limit=20&resource_id=a807b7ab-6cad-4aa6-87d0-e283a7353a0f","next":"/api/action/datastore_search?offset=20&limit=20&resource_id=a807b7ab-6cad-4aa6-87d0-e283a7353a0f"}
     * limit : 20
     * total : 59
     */

    private String resource_id;
    private LinksBean _links;
    private int limit;
    private int total;
    private List<FieldsBean> fields;
    private List<RecordsBean> records;

    public String getResource_id() {
        return resource_id;
    }

    public void setResource_id(String resource_id) {
        this.resource_id = resource_id;
    }

    public LinksBean get_links() {
        return _links;
    }

    public void set_links(LinksBean _links) {
        this._links = _links;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<FieldsBean> getFields() {
        return fields;
    }

    public void setFields(List<FieldsBean> fields) {
        this.fields = fields;
    }

    public List<RecordsBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsBean> records) {
        this.records = records;
    }

    public static class LinksBean {
        /**
         * start : /api/action/datastore_search?limit=20&resource_id=a807b7ab-6cad-4aa6-87d0-e283a7353a0f
         * next : /api/action/datastore_search?offset=20&limit=20&resource_id=a807b7ab-6cad-4aa6-87d0-e283a7353a0f
         */

        private String start;
        private String next;

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getNext() {
            return next;
        }

        public void setNext(String next) {
            this.next = next;
        }
    }

    public static class FieldsBean {
        /**
         * type : int4
         * id : _id
         */

        private String type;
        private String id;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class RecordsBean {
        /**
         * volume_of_mobile_data : 0.000384
         * quarter : 2004-Q3
         * _id : 1
         */

        private double volume_of_mobile_data;
        private String quarter;
        private int _id;

        public boolean moreAndEqual(String year) {
            return quarter.compareTo(year) >= 0;
        }

        public Quarter getQuarter() {
            return new Quarter(quarter);
        }

        public double getVolume_of_mobile_data() {
            return volume_of_mobile_data;
        }

        public void setVolume_of_mobile_data(double volume_of_mobile_data) {
            this.volume_of_mobile_data = volume_of_mobile_data;
        }

        public int get_id() {
            return _id;
        }

        public void set_id(int _id) {
            this._id = _id;
        }
    }
}

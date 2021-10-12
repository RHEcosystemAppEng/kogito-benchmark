package com.redhat.fsi.kogito.benchmark;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderItem {
    @JsonIgnore
    public long id;

    public String approver;
    public Order order;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Order {
        public java.lang.String orderNumber;
        public java.lang.Boolean shipped;
        public java.lang.Double total;

        @Override
        public String toString() {
            return "Order{" +
                    "orderNumber='" + orderNumber + '\'' +
                    ", shipped=" + shipped +
                    ", total=" + total +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "approver='" + approver + '\'' +
                ", order=" + order +
                '}';
    }
}

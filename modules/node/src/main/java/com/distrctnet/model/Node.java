package com.distrctnet.model;

import com.distrctnet.Enum.Auth_type;

public class Node {

    private String hostname;
    private Auth_type typeAuth;
    private String authKey;
    private String ipAddress;
    private String os;
    private double cpuUsage;
    private double memoryUsage;
    private double diskUsage;

    private Node(Builder builder) {
        this.hostname = builder.hostname;
        this.typeAuth = builder.typeAuth;
        this.authKey = builder.authKey;
        this.ipAddress = builder.ipAddress;
        this.os = builder.os;
        this.cpuUsage = builder.cpuUsage;
        this.memoryUsage = builder.memoryUsage;
        this.diskUsage = builder.diskUsage;
    }

    public static class Builder {
        private String hostname;
        private Auth_type typeAuth;
        private String authKey;
        private String ipAddress;
        private String os;
        private double cpuUsage;
        private double memoryUsage;
        private double diskUsage;

        public Builder hostname(String hostname) {
            this.hostname = hostname;
            return this;
        }

        public Builder typeAuth(Auth_type typeAuth) {
            this.typeAuth = typeAuth;
            return this;
        }

        public Builder authKey(String authKey) {
            this.authKey = authKey;
            return this;
        }

        public Builder ipAddress(String ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        public Builder os(String os) {
            this.os = os;
            return this;
        }

        public Builder cpuUsage(double cpuUsage) {
            this.cpuUsage = cpuUsage;
            return this;
        }

        public Builder memoryUsage(double memoryUsage) {
            this.memoryUsage = memoryUsage;
            return this;
        }

        public Builder diskUsage(double diskUsage) {
            this.diskUsage = diskUsage;
            return this;
        }

        public Node build() {
            return new Node(this);
        }
    }

}

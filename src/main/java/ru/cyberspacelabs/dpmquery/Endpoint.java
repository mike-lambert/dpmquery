package ru.cyberspacelabs.dpmquery;

import io.swagger.annotations.ApiModel;

/**
 * Created by mike on 01.09.17.
 */
@ApiModel
public class Endpoint {
    private String address;
    private int port;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}

package ru.cyberspacelabs.dpmquery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by mike on 01.09.17.
 */
@ApiModel(description = "Designator class for network address in format \"ip:port\" e.g. \"127.0.0.1:65300\"")
public class Endpoint {
    private String address;
    private int port;

    @ApiModelProperty(value = "IP address or domain name", required = true, dataType = "string")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @ApiModelProperty(value = "Port", required = true, dataType = "int")
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Endpoint endpoint = (Endpoint) o;

        if (port != endpoint.port) return false;
        return address.equals(endpoint.address);

    }

    @Override
    public int hashCode() {
        int result = address.hashCode();
        result = 31 * result + port;
        return result;
    }

    @Override
    public String toString() {
        return address + ':' + port;
    }
}

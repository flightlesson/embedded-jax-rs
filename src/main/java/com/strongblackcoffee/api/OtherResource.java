package com.strongblackcoffee.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

@Path("/xxx")
@Produces("application/json")
public class OtherResource extends Application {

    @GET
    public Response getIndex() {
        //System.out.println("get: container="+container+", item="+item);
        Response.ResponseBuilder rb = Response.ok("xxxxxx","text/html");
        return rb.build();
    }
}
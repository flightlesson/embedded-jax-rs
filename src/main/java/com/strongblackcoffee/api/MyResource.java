package com.strongblackcoffee.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

@Path("/api")
public class MyResource extends Application {

    @GET
    public Response getIndex() {
        //System.out.println("get: container="+container+", item="+item);
        ResponseBuilder rb = Response.ok("help me","text/html");
        return rb.build();
    }
    
    @GET
    @Path("{id}")
    public Response getThing(@PathParam("id") String id) {
        ResponseBuilder rb = Response.ok("me too id="+id,"text/html");
        return rb.build();   
    }
}

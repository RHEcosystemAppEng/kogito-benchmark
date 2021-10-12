package com.redhat.kogito.benchmark.stub;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/greeting")
public class GreetingResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Greetings hello() {
        Greetings greetings = new Greetings();
        greetings.setMessage("Quarkus RestEasy without Mongo");
        return greetings;
    }

    @POST
    @Path("/post")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Greetings postMessage(Greetings greetings) {
        greetings.setMessage("Your message is updated - "+greetings.getMessage());
        return greetings;
    }

}
package com.redhat.fsi.kogito.benchmark;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@RegisterRestClient(configKey = "process-api")
public interface BenchmarkService {
    @GET
    @Path("/greeting")
    String hello();

    @POST
    @Path("/orders")
    @Consumes("application/json")
    String newOrderItem(OrderItem orderItem);

    @POST
    @Path("/notPersistedProcess")
    @Consumes("application/json")
    String notPersistedProcess(OrderItem orderItem);
}

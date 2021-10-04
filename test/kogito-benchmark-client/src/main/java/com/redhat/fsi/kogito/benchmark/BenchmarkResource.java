package com.redhat.fsi.kogito.benchmark;

import com.fasterxml.jackson.core.JsonProcessingException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/benchmark")
public class BenchmarkResource {
    @Inject
    BenchmarkRunner benchmarkRunner;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{testType}/{noOfRequests}/{noOfThreads}")
    public String run(@PathParam("testType") String testType, @PathParam("noOfRequests") int noOfRequests,
                      @PathParam("noOfThreads") int noOfThreads) throws JsonProcessingException {
        return benchmarkRunner.run(testType, noOfRequests, noOfThreads);
    }
}
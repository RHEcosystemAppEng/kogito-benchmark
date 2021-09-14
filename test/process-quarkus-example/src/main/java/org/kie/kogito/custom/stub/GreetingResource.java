/*
 * Copyright 2020 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
        greetings.setMessage("Your message is updated - " + greetings.getMessage());
        return greetings;
    }

}
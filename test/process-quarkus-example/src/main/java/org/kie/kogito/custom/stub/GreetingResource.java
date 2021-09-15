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
package org.kie.kogito.custom.stub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;

@Path("/greeting")
public class GreetingResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingResource.class);
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Greetings hello() {
        LOGGER.info("inside GET dummy API...{}", new Date());
        Greetings greetings = new Greetings();
        greetings.setMessage("Quarkus RestEasy without Mongo");
        return greetings;
    }

    @POST
    @Path("/post")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Greetings postMessage(Greetings greetings) {
        LOGGER.info("inside POST dummy API...{}", new Date());
        greetings.setMessage("Your message is updated - " + greetings.getMessage());
        return greetings;
    }

}
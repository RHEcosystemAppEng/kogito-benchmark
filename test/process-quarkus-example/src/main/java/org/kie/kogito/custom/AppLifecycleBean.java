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
package org.kie.kogito.custom;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import org.eclipse.microprofile.config.ConfigProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.configuration.*;



@ApplicationScoped
public class AppLifecycleBean {

    private static final Logger LOGGER = LoggerFactory.getLogger("ListenerBean");

    void onStart(@Observes StartupEvent ev) {
        String activeProfile = ProfileManager.getActiveProfile();
        LOGGER.info("The application is starting with the profile - [{}]", activeProfile);

        if ("infinispan".equalsIgnoreCase(activeProfile)) {
            LOGGER.info(" Infinispan config - [quarkus.infinispan-client.server-list] - [{}]", ConfigProvider.getConfig().getValue("quarkus.infinispan-client.server-list", String.class));
        }

        if ("mongo".equalsIgnoreCase(activeProfile)) {
            LOGGER.info(" Mongo Database config - [quarkus.mongodb.database] - [{}]", ConfigProvider.getConfig().getValue("quarkus.mongodb.database", String.class));
            LOGGER.info(" Mongo Database config - [quarkus.mongodb.connection-string] - [{}]", ConfigProvider.getConfig().getValue("quarkus.mongodb.connection-string", String.class));
        }
    }
}

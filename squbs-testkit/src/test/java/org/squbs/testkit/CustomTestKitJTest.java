/*
 * Licensed to Typesafe under one or more contributor license agreements.
 * See the AUTHORS file distributed with this work for
 * additional information regarding copyright ownership.
 * This file is licensed to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.squbs.testkit;

import akka.actor.ActorRef;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.squbs.unicomplex.UnicomplexBoot;

import java.util.HashMap;
import java.util.Map;

public class CustomTestKitJTest extends CustomTestKitJ {

    static UnicomplexBoot boot;

    static {
        Map<String, Object> configMap = new HashMap<>();
        configMap.put("squbs.actorsystem-name", "myTest");
        configMap.put("squbs.external-config-dir", "actorCalLogTestConfig");

        Config testConfig = ConfigFactory.parseMap(configMap);
        boot = UnicomplexBoot.apply(testConfig).start();
    }



    @BeforeClass
    public static void beforeAll() {
        beforeClass(boot);
    }

    @Test
    public void testIt() {
        new DebugTimingTestKit(actorSystem) {{
            ActorRef testActor = actorSystem.actorOf(Props.create(TestActorJ.class));
            testActor.tell("Ping", getRef());
            Object response = receiveOne(duration("10 seconds"));
            Assert.assertEquals("Pong", response);
        }};

    }
}

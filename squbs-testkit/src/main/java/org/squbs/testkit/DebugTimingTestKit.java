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

import akka.actor.ActorSystem;
import akka.testkit.JavaTestKit;
import scala.concurrent.duration.Duration;

public class DebugTimingTestKit extends JavaTestKit{
    public DebugTimingTestKit(ActorSystem system) {
        super(system);
    }

    @Override
    public Object receiveOne(Duration max) {
        if (DebugTiming$.MODULE$.debugMode()) {
            return super.receiveOne(DebugTiming$.MODULE$.debugTimeout());
        } else {
            return super.receiveOne(max);
        }
    }
}

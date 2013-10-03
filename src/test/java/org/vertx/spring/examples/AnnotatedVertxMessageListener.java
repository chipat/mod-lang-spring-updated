/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.vertx.spring.examples;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.spring.EventBusHandler;

/**
 * @author swilliams
 *
 */
@Component
public class AnnotatedVertxMessageListener {

  @Autowired
  private EventBus eventBus;

  @EventBusHandler("test.annotation.receive")
  public void onMessage(String payload) {
    System.out.println("AnnotatedVertxMessageListener.payload(" + payload + ")");
    eventBus.send("test.annotation.receive.callback", payload);
  }

}

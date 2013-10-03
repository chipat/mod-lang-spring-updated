/*
 * Copyright 2012 the original author or authors.
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
package org.vertx.java.deploy.impl.spring;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.ApplicationListener;
import org.vertx.java.platform.Container;
import org.vertx.java.platform.Verticle;
import org.vertx.java.core.Vertx;

/**
 * @author swilliams
 * @since 1.0
 *
 */
public class SpringVerticle extends Verticle {

  private final ClassLoader loader;

  private final String springConfig;

  private final Container container;

  private VertxApplicationContext context;

  public SpringVerticle(Vertx vertx, ClassLoader loader, Container container, String springConfig) {
    this.loader = loader;
    this.container = container;
    this.springConfig = springConfig;
    this.vertx = vertx;
  }

  @Override
  public void start() {

    ClassLoader tccl = Thread.currentThread().getContextClassLoader();
    Thread.currentThread().setContextClassLoader(loader);

    this.context = new VertxApplicationContext(loader, vertx, container);
    context.createContext(springConfig);

    Set<ApplicationListener<?>> listeners = new HashSet<>();
    // TODO discover configured listeners

    for (ApplicationListener<?> listener : listeners) {
      context.addApplicationListener(listener);
    }

    context.refresh();
    context.start();

    Thread.currentThread().setContextClassLoader(tccl);
  }

  @Override
  public void stop() {

    if (context != null) {
      context.stop();
    }

    super.stop();
  }

}

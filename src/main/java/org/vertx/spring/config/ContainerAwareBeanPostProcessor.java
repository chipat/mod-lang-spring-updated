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
package org.vertx.spring.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.vertx.java.platform.Container;
import org.vertx.spring.ContainerAware;

/**
 * @author swilliams
 * @since 1.0
 */
public class ContainerAwareBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter {

  private final Container container;

  public ContainerAwareBeanPostProcessor(Container container) {
    this.container = container;
  }

  /**
   * Detects beans that implement VertxAware.
   */
  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {

    if (bean instanceof ContainerAware) {
      ContainerAware vertxContainer = (ContainerAware) bean;
      vertxContainer.setContainer(container);
      return vertxContainer;
    }

    return bean;
  }

}

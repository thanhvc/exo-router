/*
 * Copyright (C) 2003-2012 eXo Platform SAS.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.exoplatform.ks.router.impl.handler;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Provider;

import org.exoplatform.ks.router.event.BaseEvent;

/**
 * Created by The eXo Platform SAS Author : eXoPlatform exo@exoplatform.com Apr
 * 24, 2012
 */
public class HandlerFactory {
  private static Map<String, Provider<BaseEventHandler>> handlers = new HashMap<String, Provider<BaseEventHandler>>();

  static {
    handlers.put("forum.home", new BuilderProvider<BaseEventHandler>(ForumHandler.class.getName()));
    handlers.put("forum.search", new BuilderProvider<BaseEventHandler>(SearchForumHandler.class.getCanonicalName()));
    handlers.put("forum.tag", new BuilderProvider<BaseEventHandler>(ForumTagHandler.class.getCanonicalName()));
    handlers.put("forum.topic.show", new BuilderProvider<BaseEventHandler>(TopicHandler.class.getCanonicalName()));
    handlers.put("forum.topic.reply", new BuilderProvider<BaseEventHandler>(TopicReplyHandler.class.getCanonicalName()));
    handlers.put("forum.topic.quote", new BuilderProvider<BaseEventHandler>(TopicQuoteHandler.class.getCanonicalName()));
    handlers.put("forum.topic.page", new BuilderProvider<BaseEventHandler>(TopicPageShowHandler.class.getCanonicalName()));
    handlers.put("forum.topic.post.show", new BuilderProvider<BaseEventHandler>(TopicPostShowHandler.class.getCanonicalName()));
    handlers.put("category.show", new BuilderProvider<BaseEventHandler>(CategoryShowHandler.class.getCanonicalName()));
  }

  /**
   * Gets EventHandler bases on EventType 
   * @param event
   * @return
   */
  public static BaseEventHandler getHandler(BaseEvent event) {
    Provider<BaseEventHandler> providerHandler = handlers.get(event.getType());
    return providerHandler.get();
  }
  
  
  static class BuilderProvider<T extends BaseEventHandler> implements Provider<T> {
    T instance = null;
    String className;

    public BuilderProvider(String className) {
      this.className = className;
    }
    
    public T get() {
      if (instance != null) {
        return instance;
      }
      try {
        Class<T> builderClass = (Class<T>) Thread.currentThread().getContextClassLoader().loadClass(this.className);
        instance = (T) builderClass.newInstance();
      } catch (Exception e) {

      }
      return instance;
    }

  }
}

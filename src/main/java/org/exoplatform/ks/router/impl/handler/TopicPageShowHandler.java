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

import org.exoplatform.ks.router.event.BaseEvent;
import org.exoplatform.ks.router.event.HandleException;

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * Apr 24, 2012  
 */
public class TopicPageShowHandler extends BaseEventHandler {
  @Override
  public void handle(BaseEvent event) throws HandleException {
    System.out.println("TopicPageShowHandler::" + event.toString() + "::arguments[topicID = " + event.getArgs().get("topicID") + ", pageNo" + event.getArgs().get("pageNo") +"]");
  }
}

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
package org.exoplatform.ks.router.event;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * Apr 24, 2012  
 */
@SuppressWarnings("serial")
public abstract class BaseEvent extends EventObject {
  private String type;
  final Map<String, String> args;
  
  public BaseEvent(Object source, String type, Map<String, String> args) {
    super(source);
    this.type = type;
    
    this.args = (args == null) ? new HashMap<String, String>() : args;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
  
  @Override
  public String toString() {
    return "BaseEvent[type ::" + type + "]";
  }

  public Map<String, String> getArgs() {
    return args;
  }
  
  
  
}

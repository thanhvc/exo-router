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
package org.exoplatform.ks.router;

import java.util.Map;

import junit.framework.TestCase;

import org.exoplatform.ks.router.ExoRouter.Route;
import org.exoplatform.ks.router.event.BaseEvent;
import org.exoplatform.ks.router.event.HandleException;
import org.exoplatform.ks.router.impl.handler.BaseEventHandler;
import org.exoplatform.ks.router.impl.handler.HandlerFactory;

/**
 * Created by The eXo Platform SAS
 * Author : thanh_vucong
 *          thanh_vucong@exoplatform.com
 * Sep 5, 2012  
 */
public abstract class ExoRouterBaseTest extends TestCase {

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    initRouter();
  }
  
  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
    ExoRouter.reset();
  }
  
  private void initRouter() {
    //{selectedNode}/ForumService
    ExoRouter.prependRoute("/{pageID}/ForumService", "forum.home");
    //{selectedNode}/SearchForum
    ExoRouter.addRoute("/{pageID}/SearchForum", "forum.search");
    //{selectedNode}Tag
    ExoRouter.addRoute("/{pageID}/Tag", "forum.tag");
    //{selectedNode}/topic/{topicId}
    ExoRouter.addRoute("/{pageID}/topic/{topicID}", "forum.topic.show");
    //{selectedNode}/topic/topic{topicId}/true
    ExoRouter.addRoute("/{pageID}/topic/{topicID}/reply", "forum.topic.reply");
    //{selectedNode}/topic/{topicId}/false
    ExoRouter.addRoute("/{pageID}/topic/{topicID}/quote", "forum.topic.quote");
    //{selectedNode}/topic/{topicID}/{postID}
    ExoRouter.addRoute("/{pageID}/topic/{topicID}/post/{postID}", "forum.topic.post.show");
    //{selectedNode}/topic/topic{topicID}/
    ExoRouter.addRoute("/{pageID}/topic/{topicID}/page/{pageNo}", "forum.topic.page");
    //{selectedNode}/forum/forum{forumID}
    ExoRouter.addRoute("/{pageID}/forum/{forumID}", "forum.show");
    //{selectNode}/forum/{forumID}/{number}
    ExoRouter.addRoute("/{pageID}/forum/{forumID}/page/{pageNo}", "forum.show.page");
    
    //{selectNode}/category/{categoryID}
    ExoRouter.addRoute("/{pageID}/category/{categoryID}", "category.show");
    
    //social activity
    ExoRouter.addRoute("/activity/{activityID}", "activity.show");
    
    
  }
  
  public void doExecute(BaseEvent event) throws HandleException {
    //
    BaseEventHandler handler = HandlerFactory.getHandler(event);
    assertNotNull(handler);
    handler.handle(event);
  }
  
  public void assertRouter(Route route, String actionName, Map<String, String> expectedArgs) {
    assertNotNull(route);
    assertEquals(actionName, route.action);
    for(Map.Entry<String , String> entry : expectedArgs.entrySet()) {
      assertEquals(entry.getValue(), route.localArgs.get(entry.getKey()));
    }
  }
  
}

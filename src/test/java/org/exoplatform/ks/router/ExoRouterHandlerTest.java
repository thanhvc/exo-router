package org.exoplatform.ks.router;

import org.exoplatform.ks.router.ExoRouter.Route;
import org.exoplatform.ks.router.event.BaseEvent;
import org.exoplatform.ks.router.impl.handler.ForumEvent;

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

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * Apr 23, 2012  
 */
public class ExoRouterHandlerTest extends ExoRouterBaseTest {
  @Override
  protected void setUp() throws Exception {
    super.setUp();
  }
  
  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }
 
  public void testRouterHandlerForForumHome() throws Exception {
    Route route = ExoRouter.route("/12345/ForumService");
    assertNotNull(route);
    assertEquals("forum.home", route.action);
    assertEquals("12345", route.localArgs.get("pageID"));
    BaseEvent event = new ForumEvent(this, route.action, route.localArgs);
    doExecute(event);
    
  }
  
  public void testRouterForSearch() throws Exception {
    Route route = ExoRouter.route("/12345/SearchForum");
    assertNotNull(route);
    assertEquals("forum.search", route.action);
    assertEquals("12345", route.localArgs.get("pageID"));
    
    BaseEvent event = new ForumEvent(this, route.action, route.localArgs);
    doExecute(event);
    
  }
  
  public void testRouterForTag() throws Exception {
    Route route = ExoRouter.route("/12345/Tag");
    assertNotNull(route);
    assertEquals("forum.tag", route.action);
    assertEquals("12345", route.localArgs.get("pageID"));
    BaseEvent event = new ForumEvent(this, route.action, route.localArgs);
    doExecute(event);
  }
  /**
   * Handler =>OK
   * @throws Exception
   */
  public void testRouterForShowTopic() throws Exception {
    Route route = ExoRouter.route("/12345/topic/topic987654321");
    assertNotNull(route);
    assertEquals("forum.topic.show", route.action);
    assertEquals("12345", route.localArgs.get("pageID"));
    assertEquals("topic987654321", route.localArgs.get("topicID"));
    
    BaseEvent event = new ForumEvent(this, route.action, route.localArgs);
    doExecute(event);
  }
  /**
   * Handler =>OK
   * @throws Exception
   */
  public void testRouterForShowTopicReply() throws Exception {
    //ExoRouter.addRoute("/{pageID}/topic/{topicID}/{reply}", "forum.tag.show.true");
    Route route = ExoRouter.route("/12345/topic/topic987654321/reply");
    assertNotNull(route);
    assertEquals("forum.topic.reply", route.action);
    assertEquals("12345", route.localArgs.get("pageID"));
    assertEquals("topic987654321", route.localArgs.get("topicID"));
    
    //
    BaseEvent event = new ForumEvent(this, route.action, route.localArgs);
    doExecute(event);
  }
  
  /**
   * Handler =>OK
   * @throws Exception
   */
  public void testRouterForShowTopicQuote() throws Exception {
    //ExoRouter.addRoute("/{pageID}/topic/{topicID}/{reply}", "forum.tag.show.true");
    Route route = ExoRouter.route("/12345/topic/topic987654321/quote");
    assertNotNull(route);
    assertEquals("forum.topic.quote", route.action);
    assertEquals("12345", route.localArgs.get("pageID"));
    assertEquals("topic987654321", route.localArgs.get("topicID"));
    //
    BaseEvent event = new ForumEvent(this, route.action, route.localArgs);
    doExecute(event);
  }
  
  public void testRouterForShowTopicWrong() throws Exception {
    //ExoRouter.addRoute("/{pageID}/topic/{topicID}/{reply}", "forum.tag.show.true");
    Route route = ExoRouter.route("/12345/fail/topic987654321");
    assertNull(route);
  }
  /**
   * Handler =>OK
   * @throws Exception
   */
  public void testRouterForShowTopicPage() throws Exception {
    //ExoRouter.addRoute("/{pageID}/topic/{topicID}/{[0-9]}", "forum.topic.page");
    Route route = ExoRouter.route("/12345/topic/topic987654321/page/3");
    assertNotNull(route);
    assertEquals("forum.topic.page", route.action);
    assertEquals("12345", route.localArgs.get("pageID"));
    assertEquals("topic987654321", route.localArgs.get("topicID"));
    assertEquals("3", route.localArgs.get("pageNo"));
    
    BaseEvent event = new ForumEvent(this, route.action, route.localArgs);
    doExecute(event);
  }
  
  /**
   * Handler =>OK
   * @throws Exception
   */
  public void testRouterForTopicPostShow() throws Exception {
    //ExoRouter.addRoute("/{pageID}/topic/{topicID}/post/{postID}", "forum.topic.post.show");
    Route route = ExoRouter.route("/12345/topic/topic987654321/post/post987654321");
    assertNotNull(route);
    assertEquals("forum.topic.post.show", route.action);
    assertEquals("12345", route.localArgs.get("pageID"));
    assertEquals("topic987654321", route.localArgs.get("topicID"));
    assertEquals("post987654321", route.localArgs.get("postID"));
    //
    BaseEvent event = new ForumEvent(this, route.action, route.localArgs);
    doExecute(event);
  }
  
  public void testRouterForCategoryShow() throws Exception {
    //ExoRouter.addRoute("/{pageID}/category/{categoryID}", "category.show");
    Route route = ExoRouter.route("/12345/category/category987654321");
    assertNotNull(route);
    assertEquals("category.show", route.action);
    assertEquals("12345", route.localArgs.get("pageID"));
    assertEquals("category987654321", route.localArgs.get("categoryID"));
    //
    BaseEvent event = new ForumEvent(this, route.action, route.localArgs);
    doExecute(event);
  }
}

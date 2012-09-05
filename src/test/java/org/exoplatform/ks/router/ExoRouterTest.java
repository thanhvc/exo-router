package org.exoplatform.ks.router;

import java.util.HashMap;

import org.exoplatform.ks.router.ExoRouter.Route;

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
public class ExoRouterTest extends ExoRouterBaseTest {
  
  @Override
  protected void setUp() throws Exception {
    super.setUp();
  }
  
  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  public void testRouterForActivityShow() throws Exception {
    Route route = ExoRouter.route("/activity/4437hg2121");
    
    assertRouter(route, "activity.show", new HashMap<String,String>(){{
      put("activityID","4437hg2121");
    }});
    
  }
  
  public void testRouterForForumHome() throws Exception {
    Route route = ExoRouter.route("/12345/ForumService");
    
    assertRouter(route, "forum.home", new HashMap<String,String>(){{
      put("pageID","12345");
    }});
  }
  
  public void testRouterForSearch() throws Exception {
    Route route = ExoRouter.route("/12345/SearchForum");
    
    //
    assertRouter(route, "forum.search", new HashMap<String,String>(){{
      put("pageID","12345");
    }});
    
  }
  
  public void testRouterForTag() throws Exception {
    Route route = ExoRouter.route("/12345/Tag");
    
    //
    assertRouter(route, "forum.tag", new HashMap<String,String>(){{
      put("pageID","12345");
    }});
  }
  
  public void testRouterForShowTopic() throws Exception {
    Route route = ExoRouter.route("/12345/topic/topic987654321");
    
    //
    assertRouter(route, "forum.topic.show", new HashMap<String,String>(){{
      put("pageID","12345");
      put("topicID","topic987654321");
    }});
  }
  
  public void testRouterForShowTopicTrue() throws Exception {
    //ExoRouter.addRoute("/{pageID}/topic/{topicID}/{reply}", "forum.tag.show.true");
    Route route = ExoRouter.route("/12345/topic/topic987654321/reply");
    
    //
    assertRouter(route, "forum.topic.reply", new HashMap<String,String>(){{
      put("pageID","12345");
      put("topicID","topic987654321");
    }});
    
  }
  
  public void testRouterForShowTopicFalse() throws Exception {
    //ExoRouter.addRoute("/{pageID}/topic/{topicID}/{reply}", "forum.tag.show.true");
    Route route = ExoRouter.route("/12345/topic/topic987654321/quote");
    
    //
    assertRouter(route, "forum.topic.quote", new HashMap<String,String>(){

    {
      put("pageID","12345");
      put("topicID","topic987654321");
    }});
  }
  
  public void testRouterForShowTopicWrong() throws Exception {
    //ExoRouter.addRoute("/{pageID}/topic/{topicID}/{reply}", "forum.tag.show.true");
    Route route = ExoRouter.route("/12345/fail/topic987654321");
    assertNull(route);
  }
  
  public void testRouterForShowTopicPage() throws Exception {
    //ExoRouter.addRoute("/{pageID}/topic/{topicID}/{[0-9]}", "forum.topic.page");
    Route route = ExoRouter.route("/12345/topic/topic987654321/page/3");
    
    //
    assertRouter(route, "forum.topic.page", new HashMap<String,String>(){{
      put("pageID","12345");
      put("topicID","topic987654321");
      put("pageNo","3");
    }});
  }

}

# eXo Forum Router mechanism

## Abstract
  * Currently, we are handling with complexity condition(See more detail in https://github.com/exodev/ks/blob/master/eXoApplication/forum/webapp/src/main/java/org/exoplatform/forum/webui/UIForumPortlet.java#calculateRenderComponent method), so totally difficult to maintainance if you have any change with forum's URI.

  * This component which uses to improve the way to parse URI's path in eXo Forum, it also made more easier to handle and manage forum's URI and then map to forum's handler.

### Default build

Use this command to build project:

    mvn clean install

By default, it will run only unit tests.

## How to use this library

Sample code:
 * Router configuration

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

  * Gets Route from path like that: forum's path= "/12345/ForumService");"
    + Route route = ExoRouter.route("/12345/ForumService");
    + route.action is "forum.home"
    + pageID is "12345"
   Base on these got data, it's easier to map with forum's handler.




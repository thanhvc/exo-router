# eXo Forum Router mechanism

## Abstract
  * Currently, we are handling with complexity condition(See more detail in https://github.com/exodev/ks/blob/master/eXoApplication/forum/webapp/src/main/java/org/exoplatform/forum/webui/UIForumPortlet.java#calculateRenderComponent method), so totally difficult to maintainance if you have any change with forum's URI.

<table>
  <tr>
    <td>No</td>
    <td>URI path</td>
    <td>Comment</td>
  </tr>
  <tr>
    <td>1</td>
    <td>{selectNode}/ForumService</td>
    <td>Accesses to Forum home page</td>
  </tr>
  <tr>
    <td>2</td>
    <td>{selectNode}/SearchForum</td>
    <td>Render for Advanced Search UI form</td>
  </tr>
  <tr>
    <td>3</td>
    <td>{selectNode}/Tag</td>
    <td>Handles for rendering list topics in the Tag</td>
  </tr>
  <tr>
    <td>4</td>
    <td>{selectNode}/topic/topic{random id}</td>
    <td>Handles for rendering to show with topicID</td>
  </tr>
  <tr>
    <td>5</td>
    <td>{selectNode}/topic/topic{random id}/true</td>
    <td>Handles for rendering to reply this topic</td>
  </tr>
  <tr>
    <td>6</td>
    <td>{selectNode}/topic/topic{random id}/false</td>
    <td>Handles for rendering to qoute this topic</td>
  </tr>
  <tr>
    <td>7</td>
    <td>{selectNode}/topic/topic{random id}/post{ramdom id}</td>
    <td>Handles for rendering to show postID which belong to given topicID</td>
  </tr>
  <tr>
    <td>8</td>
    <td>{selectNode}/topic/topic{ramdom id}/{number}</td>
    <td>Handles for rendering to go to page of topicID</td>
  </tr>

  <tr>
    <td>9</td>
    <td>{selectNode}/forum/forum{random id}</td>
    <td>Handles for rendering forum container</td>
  </tr>
  <tr>
    <td>10</td>
    <td>{selectNode}/forum/forum{random id}/{number}</td>
    <td>Handles for rendering forum container and goto page number</td>
  </tr>
  <tr>
    <td>11</td>
    <td>{selectNode}/category/forumCategory{random id}</td>
    <td>Handles for rendering category UI Form </td>
  </tr>
</table>

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

 * Gets Route from path like that:

        forum's path= "/12345/ForumService");"
        Route route = ExoRouter.route("/12345/ForumService");
        route.action is "forum.home"
        pageID is "12345"
        
   Base on these got data, it's easier to map with forum's handler.




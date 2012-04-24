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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import jregex.Matcher;
import jregex.Pattern;
import jregex.REFlags;

/**
 * Created by The eXo Platform SAS Author : eXoPlatform exo@exoplatform.com Apr
 * 23, 2012
 */
public class ExoRouter {

  static Pattern routePattern = new Pattern("^({path}.*/[^\\s]*)\\s+({action}[^\\s(]+)({params}.+)?(\\s*)$");

  /**
   * All the loaded routes.
   */
  public static List<Route> routes = new CopyOnWriteArrayList<Route>();

  public static void reset() {
    routes.clear();
  }

  /**
   * Add new route which loaded from route configuration file.
   * 
   * @param path /{pageID}/ForumService
   * @param action the action which appends to patch after "ForumService"
   *          string.
   */
  public static void addRoute(String path, String action) {
    addRoute(path, action, null);
  }

  /**
   * Add new route which loaded from route configuration file.
   * 
   * @param path /{pageID}/ForumService
   * @param action /{pageID}/ForumService
   * @param params the action which appends to patch after "ForumService" string
   *          ex: /{pageID}/{ForumService|}/{action} =>/{pageID}/ForumService/{}
   */
  public static void addRoute(String path, String action, String params) {
    appendRoute(path, action, params);
  }

  public static void appendRoute(String path, String action, String params) {
    int position = routes.size();
    routes.add(position, getRoute(path, action, params));
  }

  public static Route getRoute(String path, String action, String params) {
    return getRoute(path, action, params, null, 0);
  }

  public static Route getRoute(String path, String action) {
    return getRoute(path, action, null, null, 0);
  }

  public static Route getRoute(String path, String action, String params, String sourceFile, int line) {
    Route route = new Route();
    route.path = path.replace("//", "/");
    route.action = action;
    route.routesFile = sourceFile;
    route.routesFileLine = line;
    route.addParams(params);
    route.compute();
    return route;
  }

  /**
   * Add a new route at the beginning of the route list
   */
  public static void prependRoute(String path, String action, String params) {
    routes.add(0, getRoute(path, action, params));
  }

  /**
   * Add a new route at the beginning of the route list
   */
  public static void prependRoute(String path, String action) {
    routes.add(0, getRoute(path, action));
  }

  public static Route route(String path) {
    for (Route route : routes) {
      Map<String, String> args = route.matches(path);
      if (args != null) {
        route.localArgs = args;
        return route;
      }
    }

    return null;
  }

  /**
   * Generates Action base on the action name and arguments
   * 
   * @param action
   * @param args
   * @return
   */
  public static ActionBuilder reverse(String action, Map<String, Object> args) {
    Map<String, Object> argsbackup = new HashMap<String, Object>(args);
    // Add routeArgs
    for (Route route : routes) {
      if (route.actionPattern != null) {
        Matcher matcher = route.actionPattern.matcher(action);
        if (matcher.matches()) {
          for (String group : route.actionArgs) {
            String v = matcher.group(group);
            if (v == null) {
              continue;
            }
            args.put(group, v.toLowerCase());
          }
          List<String> inPathArgs = new ArrayList<String>(16);
          boolean allRequiredArgsAreHere = true;

          for (Route.Arg arg : route.args) {
            inPathArgs.add(arg.name);
            Object value = args.get(arg.name);
            if (value != null) {
              if (!value.toString().startsWith(":") && !arg.constraint.matches(value.toString())) {
                allRequiredArgsAreHere = false;
                break;
              }
            }
          }
          if (allRequiredArgsAreHere) {
            StringBuilder queryString = new StringBuilder();
            String path = route.path;
            if (path.endsWith("/?")) {
              path = path.substring(0, path.length() - 2);
            }
            for (Map.Entry<String, Object> entry : args.entrySet()) {
              String key = entry.getKey();
              Object value = entry.getValue();
              if (inPathArgs.contains(key) && value != null) {
                path = path.replaceAll("\\{(<[^>]+>)?" + key + "\\}", value.toString().replace("$", "\\$").replace("%3A", ":").replace("%40", "@"));
              } else if (value != null) {
                try {
                  queryString.append(URLEncoder.encode(key, "UTF-8"));
                  queryString.append("=");
                  if (value.toString().startsWith(":")) {
                    queryString.append(value.toString());
                  } else {
                    queryString.append(URLEncoder.encode(value.toString() + "", "UTF-8"));
                  }
                  queryString.append("&");
                } catch (UnsupportedEncodingException ex) {
                }

              }
            }
            String qs = queryString.toString();
            if (qs.endsWith("&")) {
              qs = qs.substring(0, qs.length() - 1);
            }
            ActionBuilder actionDefinition = new ActionBuilder();
            actionDefinition.url = qs.length() == 0 ? path : path + "?" + qs;
            actionDefinition.action = action;
            actionDefinition.args = argsbackup;
            return actionDefinition;
          }
        }
      }
    }
    return null;
  }

  public static class ActionBuilder {

    /**
     * The domain/host name.
     */
    public String host;

    /**
     * The HTTP method, e.g. "GET".
     */
    public String method;

    /**
     * @todo - what is this? does it include the domain?
     */
    public String url;

    /**
     * Whether the route contains an astericks *.
     */
    public boolean star;

    /**
     * @todo - what is this? does it include the class and package?
     */
    public String action;

    /**
     * @todo - are these the required args in the routing file, or the query
     *       string in a request?
     */
    public Map<String, Object> args;

    public ActionBuilder add(String key, Object value) {
      args.put(key, value);
      return reverse(action, args);
    }

    public ActionBuilder remove(String key) {
      args.remove(key);
      return reverse(action, args);
    }

    public ActionBuilder addRef(String fragment) {
      url += "#" + fragment;
      return this;
    }

    @Override
    public String toString() {
      return url;
    }

  }

  public static class Route {

    public String path;

    public String action;

    Pattern actionPattern;

    List<String> actionArgs = new ArrayList<String>(3);

    Pattern pattern;

    public String routesFile;

    List<Arg> args = new ArrayList<Arg>(3);

    Map<String, String> staticArgs = new HashMap<String, String>(3);

    Map<String, String> localArgs = null;

    public int routesFileLine;

    static Pattern customRegexPattern = new Pattern("\\{([a-zA-Z_][a-zA-Z_0-9]*)\\}");

    static Pattern argsPattern = new Pattern("\\{<([^>]+)>([a-zA-Z_0-9]+)\\}");

    static Pattern paramPattern = new Pattern("([a-zA-Z_0-9]+):'(.*)'");

    public void compute() {
      String patternString = this.path;
      patternString = customRegexPattern.replacer("\\{<[^/]+>$1\\}").replace(patternString);
      Matcher matcher = argsPattern.matcher(patternString);
      while (matcher.find()) {
        Arg arg = new Arg();
        arg.name = matcher.group(2);
        arg.constraint = new Pattern(matcher.group(1));
        args.add(arg);
      }

      patternString = argsPattern.replacer("({$2}$1)").replace(patternString);
      this.pattern = new Pattern(patternString);
      // Action pattern
      patternString = action;
      patternString = patternString.replace(".", "[.]");
      for (Arg arg : args) {
        if (patternString.contains("{" + arg.name + "}")) {
          patternString = patternString.replace("{" + arg.name + "}", "({" + arg.name + "}" + arg.constraint.toString() + ")");
          actionArgs.add(arg.name);
        }
      }
      actionPattern = new Pattern(patternString, REFlags.IGNORE_CASE);
    }

    public void addParams(String params) {
      if (params == null || params.length() < 1) {
        return;
      }
      params = params.substring(1, params.length() - 1);
      for (String param : params.split(",")) {
        Matcher matcher = paramPattern.matcher(param);
        if (matcher.matches()) {
          staticArgs.put(matcher.group(1), matcher.group(2));
        } else {
          System.out.println("Ignoring %s (static params must be specified as key:'value',...)");
        }
      }
    }

    public Map<String, String> matches(String path) {
      Matcher matcher = pattern.matcher(path);
      // Extract the host variable
      if (matcher.matches()) {
        Map<String, String> localArgs = new HashMap<String, String>();
        for (Arg arg : args) {
          if (arg.defaultValue == null) {
            localArgs.put(arg.name, matcher.group(arg.name));
          }
        }
        return localArgs;
      }

      return null;
    }

    static class Arg {
      String name;

      Pattern constraint;

      String defaultValue;
    }
  }

}

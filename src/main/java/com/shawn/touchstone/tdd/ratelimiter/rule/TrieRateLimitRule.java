package com.shawn.touchstone.tdd.ratelimiter.rule;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import static java.util.Collections.emptyList;

public class TrieRateLimitRule implements RateLimitRule {

    private volatile ConcurrentHashMap<String, TrieRateLimitRule> limitRules =
            new ConcurrentHashMap<>();
    private Node root;

    public TrieRateLimitRule() {
        root = new Node("/");
    }

    public RuleConfig.ApiLimit getLimit(String appId, String urlPath) {
        if (StringUtils.isEmpty(appId) || StringUtils.isEmpty(urlPath)) {
            return null;
        }

        TrieRateLimitRule appUrlRateLimitRule = limitRules.get(appId);
        if (appUrlRateLimitRule == null) {
            return null;
        }

        RuleConfig.ApiLimit apiLimit = appUrlRateLimitRule.getLimitInfo(urlPath);
        return apiLimit;
    }

    public void addLimit(String appId, RuleConfig.ApiLimit apiLimit) {
        if (StringUtils.isEmpty(appId) || appId == null) {
            return;
        }

        TrieRateLimitRule newTrie = new TrieRateLimitRule();
        TrieRateLimitRule trie = limitRules.putIfAbsent(appId, newTrie);
        if (trie == null) {
            newTrie.addApiLimit(apiLimit);
        } else {
            trie.addApiLimit(apiLimit);
        }
    }

    public void addLimits(String appId, List<RuleConfig.ApiLimit> limits) {
        TrieRateLimitRule newTrie = new TrieRateLimitRule();
        TrieRateLimitRule trie = limitRules.putIfAbsent(appId, newTrie);
        if (trie == null) {
            trie = newTrie;
        }
        for (RuleConfig.ApiLimit apiLimit : limits) {
            trie.addApiLimit(apiLimit);
        }
    }

    public void addApiLimit(RuleConfig.ApiLimit apiLimit) {
        String urlPath = apiLimit.getApi();
        if (!urlPath.startsWith("/")) {
            throw new RuntimeException("api is invalid " + urlPath);
        }
        if (apiLimit.getApi().equals("/")) {
            root.apiLimit = apiLimit;
            return;
        }
        List<String> pathDirs;
        pathDirs = tokenizeUrlPath(urlPath);
        if (pathDirs == null || pathDirs.isEmpty()) {
            return;
        }

        Node p = root;
        for (String path: pathDirs) {
            ConcurrentHashMap<String, Node> children = p.edges;

            String pathDirPattern = path;
            boolean isPattern = false;
            if (isUrlTemplateVariable(path)) {
                pathDirPattern = getPathDirPatten(path);
                isPattern = true;
            }
            Node newNode = new Node(pathDirPattern, isPattern);
            Node existingNode = children.putIfAbsent(pathDirPattern, newNode);
            if (existingNode != null) {
                p = existingNode;
            } else {
                p = newNode;
            }
        }
        p.apiLimit = apiLimit;
    }

    public RuleConfig.ApiLimit getLimitInfo(String urlPath) {
        if (StringUtils.isBlank(urlPath)) {
            return null;
        }

        if (urlPath.equals("/")) {
            return root.apiLimit;
        }

        List<String> pathDirs;
        pathDirs = tokenizeUrlPath(urlPath);
        if (pathDirs == null || pathDirs.isEmpty()) {
            return null;
        }
        Node p = root;
        RuleConfig.ApiLimit current = null;
        if (p.apiLimit != null) {
            current = p.apiLimit;
        }
        for (String pathDir : pathDirs) {
            ConcurrentHashMap<String, Node> children = p.edges;
            Node matchNodes = children.get(pathDir);
            if (matchNodes == null) {
                for (Map.Entry<String, Node> entry : children.entrySet()) {
                    Node n = entry.getValue();
                    if (n.isPattern) {
                        boolean matched = Pattern.matches(n.pathDir, pathDir);
                        if (matched){
                            matchNodes = n;
                        }
                    }
                }
            }
            if (matchNodes != null) {
                p = matchNodes;
                if (matchNodes.apiLimit != null) {
                    current = matchNodes.apiLimit;
                }
            } else {
                break;
            }
        }
        return current;
    }

    public static List<String> tokenizeUrlPath(String urlPath) {
        if (StringUtils.isBlank(urlPath)) {
            return emptyList();
        }

        if (!urlPath.startsWith("/")) {
            throw new RuntimeException("UrlParser tokenize error, invalid urls: " + urlPath);
        }

        String[] dirs = urlPath.split("/");
        List<String> dirlist = new ArrayList<String>();
        for (int i = 0; i < dirs.length; ++i) {
            if ((dirs[i].contains(".") || dirs[i].contains("?") || dirs[i].contains("*"))
                    && (!dirs[i].startsWith("{") || !dirs[i].endsWith("}"))) {
                throw new RuntimeException("UrlParser tokenize error, invalid urls: " + urlPath);
            }

            if (!StringUtils.isEmpty(dirs[i])) {
                dirlist.add(dirs[i]);
            }
        }
        return dirlist;
    }

    private boolean isUrlTemplateVariable(String pathDir) {
        return pathDir.startsWith("{") && pathDir.endsWith("}");
    }

    private String getPathDirPatten(String pathDir) {
        StringBuilder patternBuilder = new StringBuilder();
        int colonIdx = pathDir.indexOf(":");
        if (colonIdx == -1) {
            patternBuilder.append("(^[0-9]*$)");
        } else {
            int endIdx = pathDir.length() - 1;
            String variablePattern = pathDir.substring(colonIdx + 1, endIdx);
            patternBuilder.append('(');
            patternBuilder.append(variablePattern);
            patternBuilder.append(')');
        }
        return patternBuilder.toString();
    }
    private static class Node {
        private String pathDir;
        private boolean isPattern;

        private ConcurrentHashMap<String, Node> edges = new ConcurrentHashMap<>();

        private RuleConfig.ApiLimit apiLimit;

        Node() {}

        Node(String pathDir) {
            this(pathDir, false);
        }

        Node(String pathDir, boolean isPattern) {
            this.pathDir = pathDir;
            this.isPattern = isPattern;
        }
    }
}

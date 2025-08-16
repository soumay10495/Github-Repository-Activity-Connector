package org.github.constants;

public class GitHubConstants {
    public static final String ACCESS_TOKEN = "";
    public static final String BASE_URL = "https://api.github.com";
    public static final String REPOS_URL = BASE_URL + "/users/%s/repos?per_page=5&page=%d";
    public static final String COMMITS_URL = BASE_URL + "/repos/%s/commits?per_page=20";
}

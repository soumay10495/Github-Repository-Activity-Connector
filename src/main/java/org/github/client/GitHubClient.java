package org.github.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.github.exception.CommitInfoFetchException;
import org.github.exception.RepositoryInfoFetchException;
import org.github.model.CommitInfo;
import org.github.model.RepositoryInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.github.constants.GitHubConstants.*;

public class GitHubClient {
    private final OkHttpClient client;
    private final ObjectMapper objectMapper;
    private final String token;

    public GitHubClient(String token) {
        this.token = token;
        this.client = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    private Request.Builder requestBuilder(String url) {
        return new Request.Builder()
                .url(url)
                .header("Authorization", "token " + token)
                .header("Accept", "application/vnd.github.v3+json");
    }

    public List<RepositoryInfo> getUserRepos(String username) throws IOException {
        List<RepositoryInfo> repos = new ArrayList<>();
        int page = 1;

        while (page < 3) {
            String url = String.format(REPOS_URL, username, page);
            Request request = requestBuilder(url).build();
            List<Map<String, Object>> data;
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new RepositoryInfoFetchException("Error fetching repos: " + response);
                }
                if (response.body() == null) {
                    break;
                }
                data = objectMapper.readValue(response.body().string(), List.class);
            }
            if (data.isEmpty()) {
                break;
            }
            data.forEach(repo ->
                    repos.add(new RepositoryInfo(
                            (String) repo.get("name"),
                            (String) repo.get("full_name"))));
            page++;
        }
        return repos;
    }

    public List<CommitInfo> getRecentCommits(String fullRepoName) throws IOException {
        List<CommitInfo> commits = new ArrayList<>();

        String url = String.format(COMMITS_URL, fullRepoName);
        Request request = requestBuilder(url).build();

        List<Map<String, Object>> data;
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new CommitInfoFetchException("Error fetching commits: " + response);
            }
            if (response.body() == null) {
                return Collections.emptyList();
            }
            data = objectMapper.readValue(response.body().string(), List.class);
        }

        for (Map<String, Object> item : data) {
            Map<String, Object> commit = (Map<String, Object>) item.get("commit");
            String message = (String) commit.get("message");
            String author = ((Map<String, Object>) commit.get("author")).get("name").toString();
            String date = ((Map<String, Object>) commit.get("author")).get("date").toString();

            commits.add(new CommitInfo(message, author, date));
        }
        return commits;
    }
}


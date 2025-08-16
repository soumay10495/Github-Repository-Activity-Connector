package org.github.service;

import org.github.client.GitHubClient;
import org.github.model.CommitInfo;
import org.github.model.RepositoryInfo;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.github.constants.GitHubConstants.ACCESS_TOKEN;

@Service
public class GitHubService {
    private final GitHubClient gitHubClient;

    public GitHubService() {
        this.gitHubClient = new GitHubClient(ACCESS_TOKEN);
    }

    public Map<String, List<CommitInfo>> getUserRepoCommits(String username) {
        Map<String, List<CommitInfo>> result = new HashMap<>();
        try {
            List<RepositoryInfo> repos = gitHubClient.getUserRepos(username);
            for (RepositoryInfo repo : repos) {
                List<CommitInfo> commits = gitHubClient.getRecentCommits(repo.fullName());
                result.put(repo.name(), commits);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error retrieving data from GitHub", e);
        }
        return result;
    }
}


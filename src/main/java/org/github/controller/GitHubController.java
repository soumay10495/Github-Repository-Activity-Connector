package org.github.controller;

import org.github.service.GitHubService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/github")
public class GitHubController {
    private final GitHubService service;

    public GitHubController(GitHubService service) {
        this.service = service;
    }

    @GetMapping("/activity")
    public ResponseEntity<?> getUserActivity(@RequestParam("username") String username) {
        return ResponseEntity.ok(service.getUserRepoCommits(username));
    }
}

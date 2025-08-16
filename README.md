PROBLEM

You are tasked with building a GitHub Repository Activity Connector. This connector should authenticate
using a personal access token and retrieve a list of public repositories for a given GitHub user or organization.
For each repository, fetch the last 20 commits along with the commit message, author, and timestamp.

Requirements:

●	Accept a GitHub username as input.

●	Paginate through repositories if needed.

●	For each repo, fetch the 20 most recent commits (handle pagination if required).

●	Handle rate limits and errors gracefully.

●	Output the data as structured Java objects (POJOs).

●	Bonus: Provide a basic CLI or REST endpoint to trigger the fetch.





SOLUTION

A Spring Boot application has been created to accomplish the problem at hand. Once the server is up and running,
the application can be accessed at http://localhost:8080/. The username needs to be input in the text box and 
after clicking button "Fetch Commit Info", the requested information will be displayed in JSON format.


Technologies used

-> Java

-> Javascript

-> Spring Boot

-> OkHttp

-> Thymeleaf

-> Jackson



GitHub APIs used

-> https://api.github.com/users/{username}/repos

-> https://api.github.com/repos/{repo_name}/commit


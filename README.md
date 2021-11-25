[![CircleCI](https://circleci.com/gh/oddy-bassey/HatchwaysBlogApp.svg?style=svg)](https://circleci.com/gh/oddy-bassey/HatchwaysBlogApp)
# Blog Post Microservice
Blog Post Microservice is a SpringBoot backend application developed for the HatchWays assessment. It illustrates a simple
microservice which provide API routes to enable a client application register users, login, create blog posts, edit blog posts,
find blog posts, comment on blog posts, find comments and vote up or down (like / dislike) on any blog post.

# Architecture
This service is developed using a microservice architecture. An ideal blog application definitely will feature more specific units of micro-services as compared to this.
TDD (Test Driven Development) approach was used to improve : quality of code, debugging and code maintainability.

# Features
below are features utilised in the application </br>

| Feature type | name | Port | url | Description | 
| --------| -----| --------| -------| ------|
| Database | H2 | 8080 | http://localhost:8080/h2-console/ | The application utilizes an in memory database (h2) for data storage
| Documentation | Swagger | 8080 | http://localhost:8080/swagger-ui.html | Swagger provides documentation on all API routes in the application
| Security | JWT (JSON Web Token) | 8080 | | All routes except (/api/auth/**) routes

# Application Requirement
below are requirements needed for the application to run </br>

| name | version | Description |
| --------| -----| -----|
| Java | 11 | the application requires a minimum of Java 11 to run

# Running HatchWaysBlogApp
Running the application involves few easy steps and they are:
* navigate to the target directory(Hatchways Assessment/HatchwaysBlogApp/target) within the project folder where the application jar
  file(HatchwaysBlogApp-0.0.1-SNAPSHOT.jar) is located
* run the application by executing the below command
* execute command: **java -jar HatchwaysBlogApp-0.0.1-SNAPSHOT.jar**

# Application Hint
* After the application must have been successfully started, you can access the **swagger documentation** at the provided route stated above to build your requests or
  simply import a postman collection provided in /resources/postman collection/ folder in the application project folder to easily get 
  started testing the API end points.... or .
* The application uses JWT to provide security on the API routes. Apart from /api/auth/sigup and /api/auth/sigin routes, all other API routes
provided by this application requires an "Authorization" header to authenticate the user. The format of a valid token appends 
"Bearer " string before the token. 
* Example: "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbW1hIiwicm9sZXMiOiJVU0VSIiwiaWF0IjoxNjM3ODU5ODc5LCJleHAiOjE2Mzc4Njg4Nzl9.NQ7-AStK__UtfUT1CRUuamN5CkBzfmpt5NiZTPLg25g"
* Get started by making a requests to register a user, sigin with the user credentials, create blog posts, edit blog posts, find blog posts, comment on blog posts and 
up-vote or down-vote blog posts (like / dislike).
* To up-vote (like) a blog post, the voteType is 1 and to down-vote(dislike) a blog posts, the voteType is 0
* **Please note** that all data stored are only available during runtime because the application uses an in-memory db(H2) spawned only when the application 
is running and hence killed when application is stopped.



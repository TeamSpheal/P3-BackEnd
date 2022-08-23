# Title: RevaSphere Social Media Website (Back-End)

## Table of Contents

[Description](#description)

[Links](#links)

[Installation](#installation)

[Usage](#usage)

[Licenses](#licenses)

[Contribution](#contribution)

[Contact](#contact)

### Description
A blockchain social media application, in which a User can create an account, follow users whose posts they want to see on their feed, and make their own posts. Every user also has a profile which displays that user's statistics, allows the current user to follow another user, or allows the current user to edit their profile settings when on their own page. A user can also like a post, leave a comment on a post, or reply to a previous comment. Users can also toggle dark mode, and their choice will be persisted after logging out. If a user forgets their password, they can request a reset via email.

### Links
Github Link: https://github.com/TeamSpheal
Deployed Application: ***Replace w deployed!!!***

### Installation
Maven, Spring, Angular
#### Dependencies
* Java 11
* Maven
* Spring Boot
* Any database (we used PostgreSQL on AWS RDS)

Maven will download and compile any dependencies needed for Spring Boot so all you need is to install Java 11 and Maven.

To install Java 11 and Maven with Scoop:
```
scoop install ojdkbuild11
scoop install maven
```

#### Set Up
Assuming you're in a terminal, do the following to build a Spring .jar file:

Clone this repository:
```
git clone https://github.com/TeamSpheal/P3-BackEnd.git
```
Move into the directory and build a .jar file:
```
mvn clean package
```
Run the jar file:
```
java -jar social-media-0.0.1-SNAPSHOT.jar
```

Add the following fields to your environment variables and set the fields according to your environment:
```
JWT_SECRET: # a secret key for JWT
REVASPHERE_FRONTEND_URL: http://localhost:4200/

# The following fields are only for application-test.yml, these are not required for local testing:
DB_URL: jdbc:postgresql://#DATABASE_URL#:5432/postgres?currentSchema=#SCHEMA_NAME
DB_USER: postgres
DB_PWD: #DATABASE_PASS
SPRING_PROFILES_ACTIVE: # dev or test to switch between application.yml files
AWS_BUCKET_NAME: # name of the bucket on S3
```

### Usage
Professional skill development.

### Licenses
![GitHub license](https://img.shields.io/badge/license-MIT-blue.svg)

### Contribution
Create a personal branch named [feature]-[your_name] e.g. user-colby would be a branch for user from colby. 
Please make sure to run your build and update tests to make sure everything works before making a pull request to the feature branch.

To rename a branch while pointed to any branch:
```
git branch -m <oldname> <newname>
```
To rename the current branch:
```
git branch -m <newname>

### Contact
Developer's Name: Michael Bollinger
Developer's Profile (https://github.com/NewPagodi)

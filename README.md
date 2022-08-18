# Project 3 - RevaSphere Web Application (Backend)
This social media web application is set up with Java 11 and Spring Boot. The database is being hosted on Amazon Web RDS but it can be hosted locally with Spring's local in-memory H2 database.

## Dependencies
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

## Installation
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


## Contributing
Create a personal branch named [feature]-[your_name] e.g. user-colby would be a branch for user from colby. 
Please make sure to run your build and update tests to make sure everything works before making a pull request to the feature branch.

To rename a branch while pointed to any branch:
```
git branch -m <oldname> <newname>
```
To rename the current branch:
```
git branch -m <newname>
```

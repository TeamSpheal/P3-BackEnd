# Building the Back End

The project is uses the maven build system and you should be able to import
it into an IDE such as eclipse.

The following environment variables need to be set. The backend can support 
a number of configurations:
1) H2 database of PostgreSQL database.
2) Upload images to local storage or to an S3 bucket. 


To use the PostgreSQL database, set the following environment variable
SPRING_PROFILES_ACTIVE=test

In this case, the following environment variables must be set as well:
DB_URL
DB_USER
DB_PWD

To use the H2 database, simply set the 
SPRING_PROFILES_ACTIVE=dev

To use an S3 bucket for image uploads, set the following to the S3 bucket name
AWS_BUCKET_NAME

This must be set to the password to be used for encoding and decoding  the JWTs.
JWT_SECRET 

For CORS configuration, the following should be set. If the frontend is 
deployed online, it should be changed to that URL instead.
REVASPHERE_FRONTEND_URL=http://localhost:4200

If you are providing an email for password to be reset, the following must be
set as well.
REV_EMAIL
REV_PASS





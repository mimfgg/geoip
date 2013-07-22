A simple geoip webservice built on dropwizard

Installation
======

build with maven clean:install

deploy the schema using the db.sql file and fetch the latest geoip database with update.sh

edit the config.yml file to fit your database 

start with java -jar geoip.jar server config.yml

build the initial database with http://yourhost:port/rebuild

How to use
======

the service will deploy 3 resources:

To rebuild the database (this requires the csv files to be available at the root of the service)

GET     /rebuild 

To query the service (long and short format):

GET     /{ip} 

GET     /{ip}/small


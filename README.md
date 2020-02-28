# flight-search-engine
The project was developed for university course _Techniques of Social Networks Analysis_. The purpose of this project was 
to create flight search engine, where search result is based on two criteria:
* distance
* airports / airlines opinions 

## System architecture
[System architecture](resources/sys_architecture.jpg)

## Data sources 
Airports and airlines data comes from three files:
* airlines.dat - airline name with three-letter code
* airports.dat - airport name with three-letter code 
* routes.dat - routes with geographical coordinates

Opinions come from https://www.airlinequality.com/ site which is scraped.

## Web scraper
Scraper was implemented in multi-agent manner using Akka framework. Using actors allowed for implementing scalable 
solution which can be distributed to many servers. 

Data scraped(for each comment):
* content
* star rate
* date

Additionally, scraper application computes sentiment of comments using https://nlp.stanford.edu/ library.
Scraped data is then saved to database.  

## Backend
Backend was implemented using Scala Play framework. The main purpose of this sub-project is to compute weighted routes.
Weights are computed by joining comments rates and distances between airports. Additionally, backend serves REST API with 
computed data for frontend application.

## Technologies used
* Scraper - Scala / Akka
* Sentiment analysis - StanfordNLP 
* Backend - Scala Play framework
* Frontend - Angular framework
* Database - MongoDB

## Building & Running
Scraper project should be run with option: `-Dhttps.protocols=TLSv1.2` to avoid `handshake_failure` error.

## Example 
[Example search](resources/ex_result.PNG)
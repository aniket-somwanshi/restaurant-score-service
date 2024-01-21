# Restaurant Score Service
There are Authorities which conduct inspections at Restaurants from time to time and report the corresponding scores. This is used to deduce the health score for restaurants. Moreover, the restaurants that not inspected for a long time ie. more thatn 1 year, are required to be identified. 

![Screenshot 2024-01-21 at 12 14 20 AM](https://github.com/aniket-somwanshi/restaurant-score-service/assets/53231464/0a1336fd-dba1-4d44-bb3d-57c7596e6689)


## Terminology
Business: The restaurants that the authorities inspect and add scores for. (Eg. McDonalds, 3rd Street, New York)  
Authorities: The certified bodies that conduct inspections on restaurants and add scores. (Eg. Weld County, Colorado)  
Inspection: A visit by an authority to a restaurant, having a corresponding score.  

## Functional Requirements
- Create, view, update, delete Restaurant
- Create, view, update, delete Authority
- Add Inspection(score) for a Restaurant by an Authority
- Get all Inspections for a Restaurant
- Get Health score for a Restaurant
- Get all restaurants which are not scored for over 1 year.
- Check if a restaurant's status is outdated or not

## Considerations
- System may need to be extensible for Cafes, Bars, etc.
- The policy of a restaurant being outdated is '1 year+ of inactivity'. This policy might change in the future to say 6 months. The system should be extensible for this change without the need of a complete redesign of application/data.
- Health Score calculation strategy may need to be able to change independently. Different strategies of health score calculation can be: Most recent score, Average of all the scores, Custom logic factoring in penalties for each instance of <30 scores.

## Primary Entities
![Screenshot 2024-01-21 at 11 13 26 AM](https://github.com/aniket-somwanshi/restaurant-score-service/assets/53231464/6d06daa0-7783-4e8f-874a-b96c44c6190c)


## Decisions and tradeOffs
### Outdated Status of Restaurants

Following are the approaches along with the pros and cons:

**1) Cron job to update status of each restaurant periodically:** After every x unit of time, run a crob job that iterates over each restaurant, finds the latest date of inspection, if it's 1 year older than today, update the restaurant's flag as "outdated"; else update it as "updated".

**Pros:** Status readily available with a getStatus request. No extra operations for create inspection operation.  
**Cons:** Potential in-correct result upto x units of time, depending on the frequency of cron job. If criteria for outdated restaurants changes from 1 year to 6months/2years, the application is not extensible. High load on the system at a time, when the cron job runs, directly proportional to the number of restaurants and inspections. 

**2) Compute at Runtime:** Whenever outdated restaurants are needed, iterate over each restaurant, find the latest date of inspection, it's 1 year older than today, return it.  

**Pros:** No extra operations for create inspection operation.  
**Cons:** High load on the system, directly proportional to the number of restaurants and inspections.  

**3) Maintain last_scored_at for each restaurant(Implemented):** Whenever an inspection is added, update last_scored_at for the restaurant. Whenever outdated restaurants are needed, iterate over each restaurant, if `last_scored_at` is x unit older than today, return. 

**Pros:** Efficient getStatus operation. If criteria for outdated restaurants changes from 1 year to 6months/2years, the application is extensible.

### Health Score Strategy
For a restaurant, there are scores added by authorities. There can be many ways to calculate a single health score based on all these scores. eg. Most recent score, Average score, custom logic based on penalties for severely bad scores, etc. These strategies can vary independently. Thus, HealthStrategy interface is created and different concrete implementations provide different strategies. We can inject one of these strategies. In future, a new strategy can be created without the caller requiring any change.

![Screenshot 2024-01-21 at 11 14 05 AM](https://github.com/aniket-somwanshi/restaurant-score-service/assets/53231464/a1a08ac8-1ca6-4a5e-b59d-bd2371de4e30)

### Outdated Restaurants policy
The policy of the time period after which the restaurants are considered "outdated" can change in future according to regulations, business requirements, etc. Moreover, in future different business types, cities might be subject to different expiration time period. Thus, to be able to vary the expiration months independently, application property is used. Also, the business entity keeps track of the last_scored_at timestamp, so that we can query for 'outdated criteria' with a custom expiration time.

```
>application.properties
app.business-status-strategy.expiration-months = 12
```

Used by the service to compute outdated restaurants
```
@Value("${app.business-status-strategy.expiration-months}")
private int expirationMonths;
```
Compute all outdated restaurants according to given expirationMonths criteria
```
@Query("SELECT * FROM `score-service`.`business` " +
	           "WHERE last_scored_at < DATE_SUB(CURRENT_TIMESTAMP, INTERVAL :expirationMonths MONTH)")
List<Business> getAllOutdatedBusinesses(@Param("expirationMonths") int expirationMonths);
```

### Codebase overview
There are 3 Controllers pertaining to specific entities

![Screenshot 2024-01-21 at 11 39 11 AM](https://github.com/aniket-somwanshi/restaurant-score-service/assets/53231464/2a99ed07-5da2-4b00-9041-e1205ca22534)

They have a dependency on corresponding services. These are service contract interfaces thus the callers do not depend on concrete service implementations.

![Screenshot 2024-01-21 at 11 39 32 AM](https://github.com/aniket-somwanshi/restaurant-score-service/assets/53231464/94bc77ab-9862-4995-84c3-ba7ffc15c856)

Each service contracts have their implementation classes.

![Screenshot 2024-01-21 at 11 41 21 AM](https://github.com/aniket-somwanshi/restaurant-score-service/assets/53231464/e15bdb8b-d825-4151-8854-13630550ab57)

These services communicate with the repositories for access to and from the persistant data store.

![Screenshot 2024-01-21 at 11 42 42 AM](https://github.com/aniket-somwanshi/restaurant-score-service/assets/53231464/2f04f1a8-d42d-4ff0-96c4-c39881a1afb2)

The data store entities have representation models.

![Screenshot 2024-01-21 at 11 44 53 AM](https://github.com/aniket-somwanshi/restaurant-score-service/assets/53231464/19c91b3c-766b-4753-9ca6-a85644827f3f)

Services make use of strategies by injection.

![Screenshot 2024-01-21 at 11 46 53 AM](https://github.com/aniket-somwanshi/restaurant-score-service/assets/53231464/748d4945-a47a-4bea-972c-942eb1bf79fe)

Services make use of custom exception classes.

![Screenshot 2024-01-21 at 11 48 11 AM](https://github.com/aniket-somwanshi/restaurant-score-service/assets/53231464/92093c3b-0661-4bbd-a10f-79b5aed765de)

Configuration classes provide bean specifications.

![Screenshot 2024-01-21 at 11 49 17 AM](https://github.com/aniket-somwanshi/restaurant-score-service/assets/53231464/f4d3864d-808f-4c0d-825f-a41bac8f4ae8)


## Things Not addressed
These are the points that are not addressed in the solution and a gist of how they can be implemented.
- Auth: Authorities and Businesses can have their authentication and authorization. A JWT based client side auth solution can be implemented.
- Validations: There are several places in the application where validations can be made. eg. Score range to be [0-100], etc.
- Testing: Tests can be added to insure robustness, and can lead to even more validation being put in place.
- Dockerisation: The application and the Db can be dockerised to be able be run together with a single docker-compose up command.
 
## Setup
- Clone the repository.
- Install Java.
- Install Maven.
- Run `mvn install` at project root directory.
- Run `java -jar target/score_service-0.0.1-SNAPSHOT.jar`
- Alternatively, run the cloned project in an IDE viz. Eclipse
- Install MySql and Start server
- Import the ScoreService.sql file from the project root directory.
- Configure the application.properties file with MySql credentials.
```
spring.datasource.url=jdbc:mysql://localhost:{mysql_server_port}/score-service
spring.datasource.username= {mysql_server_username}
spring.datasource.password= {mysql_server_password}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver 
```

### Postman Collection
Import the `Restaurant Score Service.postman_collection.json` file present at root directory and access all the APIs

![Screenshot 2024-01-21 at 2 02 57 PM](https://github.com/aniket-somwanshi/restaurant-score-service/assets/53231464/34897cbb-4c8b-4018-bd79-e73a0e2780b9)

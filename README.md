# Restaurant Score Service
There are Authorities which conduct inspections at Restaurants from time to time and report the corresponding scores. This is used to deduce the health score for restaurants. Moreover, the restaurants that not inspected for a long time ie. 1 year+ are required to be identified. 

![Screenshot 2024-01-21 at 12 14 20 AM](https://github.com/aniket-somwanshi/restaurant-score-service/assets/53231464/cf3d8154-0b00-400a-9983-6330843c3136)

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
![Screenshot 2024-01-21 at 10 48 18 AM](https://github.com/aniket-somwanshi/restaurant-score-service/assets/53231464/966dd91f-6bb5-4fdf-9548-0e2d73830175)

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

![Screenshot 2024-01-21 at 10 19 37 AM](https://github.com/aniket-somwanshi/restaurant-score-service/assets/53231464/694a4d8c-2b76-46b0-84c9-95649a2906c6)

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

## Things not addressed
These are the points that are not addressed in the solution and a gist of how they can be implemented.
- Auth: Authorities and Businesses can have their authentication and authorization. A JWT based client side auth solution can be implemented.
- Validations: There are several places in the application where validations can be made. eg. Score range to be [0-100], etc.
- Testing: Tests can be added to insure robustness, and can lead to even more validation being put in place.
 
## Setup

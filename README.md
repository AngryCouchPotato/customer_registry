# Cuisines Registry

## The story:

Cuisines Registry is an important part of Book-That-Table Inc. backend stack. It keeps in memory customer preferences for restaurant cuisines and is accessed by a bunch of components to register and retrieve data. 


The first iteration of this component was implemented by rather inexperienced developer and now may require some cleaning while new functionality is being added. But fortunately, according to his words: "Everything should work and please keep the test coverage as high as I did"


## Your tasks:
1. **[Important!]** Adhere to the boy scout rule. Leave your code better than you found it.
It is ok to change any code as long as the CuisinesRegistry interface remains unchanged.
2. Make is possible for customers to follow more than one cuisine (return multiple cuisines in de.quandoo.recruitment.registry.api.CuisinesRegistry#customerCuisines)
3. Implement de.quandoo.recruitment.registry.api.CuisinesRegistry#topCuisines - returning list of most popular (highest number of registered customers) ones
4. Create a short write up on how you would plan to scale this component to be able to process in-memory billions of customers and millions of cuisines (Book-That-Table is already planning for galactic scale). (100 words max)

## Submitting your solution

+ Fork it to a **[!]**private**[!]** gitlab repository (go to `Settings -> General -> Visibility, project features, permissions -> Project visibility`).
+ Put the write up mentioned in point 4. into the end of this file.
+ Share the project with gitlab user *quandoo_recruitment_task* (go to `Settings -> Members -> Invite member`, find the user in `Select members to invite` and set `Choose a role permission` to `Developer`)
+ Send us an **ssh** clone link to the repository.


## Result:
1) Code was cleaned.
2) Customers can follow more than one cuisine.
customerCuisines method works with O(n) time and can return more then 1 cuisine
cuisineCustomers method works with O(n) time
3) topCuisines method was implemented with using mergesort algorithm
4) Just need to:
    - replace ConcurrentHashMap with inMemory database
    - configure sharding.
    - add load balancing
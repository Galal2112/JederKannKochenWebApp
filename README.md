# Jeder Kann Kochen

### Project : Jeder Kann Kochen

```
Gruppe 18
```
**Security – OWASP Top 10**

### Feature 1 : Login

- **Cross-site Request Forgery in Login Form**

If there is a page that's different for every user (such as "edit my profile") and vulnerable to XSS
(Cross-site Scripting) then normally it cannot be exploited. However if the login form is
vulnerable, an attacker can prepare a special profile, force victim to login as that user which will
trigger the XSS exploit. Again attacker is still quite limited with this XSS as there is no active
session. However the attacker can leverage this XSS in many ways such as showing the same
login form again but this time capturing and sending the entered username/password to the
attacker.
![CSRF](https://user-images.githubusercontent.com/27289097/112873099-79b62480-90c1-11eb-987b-a25a809f917f.png)


## Remediation:

You need to implement a token system in your code to prevent Login CSRF.
The important thing is to make sure the token is something the user has (but not the
attacker), so that you can make sure it really is the user submitting a login request.


## Using SQL Injection to Bypass Authentication :

To check for potential SQL injection vulnerabilities we have entered a single quote in to the
"Username" field and submitted the request using the "Login" button.

SELECT * FROM users WHERE username = '' OR 1=1-- ' AND password = 'foo'

Because the comment sequence (--) causes the remainder of the query to be ignored, this is equivalent.


## Unrestricted File Upload :

![fileupload](https://user-images.githubusercontent.com/27289097/112877422-057e7f80-90c7-11eb-8df8-bf9f210e88a5.png)


Uploaded files represent a significant risk to applications. The first step in many attacks is to
get some code to the system to be attacked. Then the attack only needs to find a way to get
the code executed. Using a file upload helps the attacker accomplish the first step.

So in our Project we check the type of the file, which is the images type “jpg” are only allowed.

The file types allowed to be uploaded should be restricted to only those that are necessary for
business functionality.

Enter some appropriate syntax to modify the SQL query into the "Name" input.In this example we used ' or 1=1 --.

Enter some appropriate syntax to modify the SQL query into the "Name" input.

This causes the application to perform the query:

SELECT * FROM users WHERE username = ' ' OR 1=


## Profile - Input Validation : Cross-site scripting (XSS)

![profile](https://user-images.githubusercontent.com/27289097/112877509-234be480-90c7-11eb-884e-35fe645bb6f8.png)


Input validation is performed to ensure only properly formed data is entering the
workflow in an information system, preventing malformed data from persisting in
the database and triggering malfunction of various downstream components.
Input validation should happen as early as possible in the data flow, preferably as
soon as the data is received from the external party.

Input validation strategies

Input validation should be applied on both syntactical and Semantic level.

Syntactic: validation should enforce correct syntax of structured fields (e.g. SSN,
date, currency symbol).

Semantic: validation should enforce correctness of their values in the specific
business context (e.g. start date is before end date, price is within expected
range).

It is always recommended to prevent attacks as early as possible in the
processing of the user's (attacker's) request. Input validation can be used to
detect unauthorized input before it is processed by the application.


## Recipes CRUD, Create new recipe, Rezept:

![CRUD](https://user-images.githubusercontent.com/27289097/112877593-3fe81c80-90c7-11eb-9c24-e4807f82f80f.png)


We checked also the input validation for these features, so the attacker will not be allowed to
pretend Cross site scripting, XSS vulnerabilities most often happen when user input is
incorporated into a web server's response (i.e., an HTML page) without proper escaping or
validation.

## Notifications and Send E-mail : rate limiting and lack of resources

**rate limiting** is used to control the **rate** of traffic sent or received by a network interface controller and
is used to prevent DoS attacks. **No rate limit** means there is **no** mechanism to protect against the
requests you made in a short frame of time.

# How to prevent

- Define proper rate limiting.
- Limit payload sizes.
- Tailor the rate limiting to be match what API methods, clients, or addresses need
or should be allowed to get.
- Add checks on compression ratios.
- Define limits for container resources.


**Tools :**

- OWASP : Auto Scanner
- Burp Suite : Request and Response check
- Dirbuster : Directorys check
- Nmap : scan Open Ports

@Mahmoud Barakat


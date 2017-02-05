## CyberSecurityBase-course guidelines

Introduction:
    There are five vulnerabilities in my project which are xss, sql-injection,
    csrf, Broken Authentication and Session Management and Data exposure

### Step by step to reproduce xss
1. Run the application
2. Go to website [http://localhost:8080/form](http://localhost:8080/form)
3. Type ```<script>alert("Hello");</script>``` to name-field
4. Type anything to address-field
5. Press 'Submit'-button
6. Goto website [http://localhost:8080/signups](http://localhost:8080/signups)
7. You've executed XSS-Attack

## How to fix XSS
Go to "stats.html" and change utext to text. This fixes the issue because
utext is deprecated and no longer in use.

### Step by step to reproduce Injection
1. Run the application
2. Go to website [http://localhost:8080/search](http://localhost:8080/search)
3. Insert into input ' or 1='1
4. You've executed injection

## How to fix injection
Use Prepared statement as in submitform method.

### Step by step to reproduce CSRF
1. Run the application
2. Go to website [http://localhost:8080/form](http://localhost:8080/form)
3. Type ```<iframe href="www.google.com"></iframe>``` to name-field
4. Type anything to address-field
5. Press 'Submit'-button
6. Goto website [http://localhost:8080/signups](http://localhost:8080/signups)
7. You've executed CSRF-Attack

## How to fix CRSF-Attack
Go to SecurityConfiguration.java file and edit configure method as follows
```java
@Override
protected void configure(HttpSecurity http) throws Exception {
    // no real security at the moment
    http.authorizeRequests()
            .anyRequest().permitAll().and().crsf().disable();
}
```

### Step by step to reproduce Broken Authentication and Session Management
1. Run the application
2. Go to website [http://localhost:8080/search?admin=1](http://localhost:8080/search?admin=1)
3. You've by passed the authentication protocol.

## How to fix broken authentication and session Management
Configure authentication manager

### Step by step to reproduce Data exposure
1. Run the application
2. Go to website http://localhost:8080/stats
3. You can see all the data that are not encrypted
4. You've found the data exposure

## How to fix data exposure
Encrypt the data.

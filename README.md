# City-Bus-Tracking-Android-App

This app is made with the intension of showing the passengers that travel by city bus, in real time what all buses will be arriving at a particular bus stop. The idea is that when the user starts the app, it detects the nearest bus stop and lists down the buses that are currently running which will pass through the detected stop. This gives one of the best user experiences as the user only needs to start the app and the rest is done by the app. 

There is also a conductor side in which condutor needs to start a bus from the app at the starting point of the bus. This will allow a bus to go "live" and the passengers can then see that bus coming. Users will know exactly which buses will be arriving and which stop is it serving currently. Therefore, they will be able to plan their journey accordingly. 

As I was in Mumbai while I made this project, I have made some prototype examples based on places in Mumbai. So the dummy routes that I have made and the locations that I have entered in the database are based on Mumbai.



# Quick and dirty way 

If you dont care about the working or dont want to make any changes and just want to get the output this is what you can do.

Clone this repo and open "MyApplication" using android studio and run the app on a physical device and hopefully everything will work. I had used a free hosting service for database and server side programs, so if at the time the servers are functioning it will all be good and working. 

If you want to make changes, change the prototype routes, add extra features, design new UI, etc, you can go through the Working section to see how the app has been build. This way you will completely understand what part you need to work on to get the app running your way. 

# Contents of this repo

MyApplication folder contains the android side of the code.
PHP files folder contains all the server side programs that were used to perform insert, update, delete on database.
Database State folder contains .sql file of the database tables that I had used for my app.

# Working

To know the working better we will first look at how to database has been set up then we will go in detail as how everything works.

## DATABASE DESIGN:

### Conductors Table:
First table in the database is the "Conductors" table. It contains information regarding the conductors that have been hired by the bus agency(BEST). It has the username and password and the name in the table required for the signing in of the conductor to start a bus. We can add new rows to this table or delete row if we hire or fire any of the conductor. There also a conductor_id used to point out uniquely any of the conductors in the table. For prototyping purpose we have added 2 conductors for now.


![](https://github.com/oppasource/City-Bus-Tracking-Android-App/blob/master/images%20for%20this%20repo/Conductors%20Table.png)

### Live Table:
This table is like the heart of the whole project. As soon as the conductor hits start, a row gets added to this table indicating that that bus is live now and is going to complete the route. all the information regarding the live buses can be fetched from this table. Whenever we want to show the users about arriving buses we will use this table to extract data from. Where a conductor reaches its destination stop, that row gets deleted.


![](https://github.com/oppasource/City-Bus-Tracking-Android-App/blob/master/images%20for%20this%20repo/Live%20Table.png)

### loc2stop Table:
This table will contain information regarding the bus stops and their location in terms of latitude and longitude. This is useful to detect the nearest bus stop by taking the latitude and longitude of the user and comparing with all the locations in this table.


![](https://github.com/oppasource/City-Bus-Tracking-Android-App/blob/master/images%20for%20this%20repo/loc2stop%20table.png)


### next_distance table:
This table works closely with the live table. This table is used to keep track of the distance form the current conductors(buses) location to the next stop location. After a certain period of time we keep tracking the conductor, so we calculate the distance every single time and compare it with the previous distance. If the distance is reducing then it implies the bus is still moving towards the next stop and we update this new distance in this table. If the distance has increased then that implies that the next has been crossed by the bus and we need to update the next stop in the live table and also here in this table with next stops latitude and longitude.


![](https://github.com/oppasource/City-Bus-Tracking-Android-App/blob/master/images%20for%20this%20repo/next_distance%20table.png)


### Routes:
This table contains information regarding which bus goes to what all bus stops in what order. It can be better explained by the example shown below. As we can see bus number 201 starts from Goregaon and goes till Vile Parle via Jogeshwari and Andheri. And 202 starts from Goregaon and goes till Juhu via Jogeshwari and Andheri. Note that we can also go in the descending order i.e. in the reverse direction.

![](https://github.com/oppasource/City-Bus-Tracking-Android-App/blob/master/images%20for%20this%20repo/Routes%20Table.png)

Now, the we have a better understanding about the database design we will look more into the app as to how it works with this database to complete the required task i.e. to show the arrival of buses. 


## ANDROID APPLICATION:
The location of the phone must be on first of all. Now as soon as the user starts the app, the location (latitude and longitude) of the user is sent to the server and there from our database we compare this location with all the locations of the bus stops present there from the "loc2stop" table and we print out the nearest bus stop at the top. In this case it is Goregaon. It also perfoms another action i.e. to see what all live buses going to arrive at Goregaon(nearest bus stop) but this will be clearly explained further.

<img src="https://github.com/oppasource/City-Bus-Tracking-Android-App/blob/master/images%20for%20this%20repo/1screen.png" width="250">

So, as we see there is nothing much shown except for the nearest bus stop. There is no information regarding the buses that will be arriving. This is because no conductor has yet started any bus. You can think of it as there is no bus which will go through Goregaon currently.
So lets act as a conductor and start a bus. All the conductors need to open the same app before they start any bus. So when we click on the "CONDUCTOR LOGIN" button we are asked for the username and password which will be provided to all the conductors so that no one other than conductor can access or start their own bus. this username is password is checked and conductor is logged in using the "Conductors" table. Currently as shown in the database design, we only have 2 conductors so lets log in one of them.



<img src="https://github.com/oppasource/City-Bus-Tracking-Android-App/blob/master/images%20for%20this%20repo/2screen.png" width="250">

We are then taken to a screen with conductors name printed and here we are asked to select which bus we want to start. this is the list we get from the "Routes" table where we just form a list of the unique route numbers and print it out as options to the conductor. As we have only 2 buses that we can play with, we are provided with 2 options. Lets say we selected 201 for example.


<img src="https://github.com/oppasource/City-Bus-Tracking-Android-App/blob/master/images%20for%20this%20repo/3screen.png" width="250">

Now based on what we select, we are asked for 2 option for the starting point based on the bus route. So for 201, we had first stop as Goregaon and last Stop as Vile Parle. So we can either start the bus from Goregaon or Vile Parle. We are asked to enter the vehicle number as well and then we can start the bus. Note that here the starting point option is fetched from the "Routes" table itself. As soon as we hit start, all this information is added to the "Live" table as a row and also with some extra information like direction (used to calculate next stop). A row getting added to the "Live" table indicates that the bus has started. So that is how we can see the "Live" table and tell which all buses and where exactly and we can show this to the users accordingly.

<img src="https://github.com/oppasource/City-Bus-Tracking-Android-App/blob/master/images%20for%20this%20repo/4screen.png" width="250">


When the conductor hits START, he is taken to the next screen where we just pull information from the "Live" table to display to the conductor and this is where conductor's phone starts getting tracked. His location is sent every 15 sec (for test purpose we can increase or decrease this time) to the server and we calculate the remaining distance for the next stop from the "next_distance" table. This distance is compared with the distance already present in that table i.e. the distance of previous 15 sec. and now if the current distance is less than the previous distance, that means the bus is still moving towards the next stop and this current distance is updated in this table. If the current distance is more than that would implies that the bus has crossed the stop and moving away from the next stop so the next stop is updated by increasing the next stop order by the direction from the "Live" table. Thus every 15sec when we pull data from there to display on this screen, next stop gets updated.

<img src="https://github.com/oppasource/City-Bus-Tracking-Android-App/blob/master/images%20for%20this%20repo/5screen.png" width="250">


Now that we are done starting a bus, i.e. we have a bus going from Goregaon to Vile Parle let us  check if this bus can be seen by a user standing at the bus stop. Here we will use a fake GPS app that can engage a fake location for our phone. We have used the fake GPS app to set our location to somewhere near Andheri. Think of it as a user standing at Andheri bus stop trying to see what buses will be arriving. As soon as he starts the app, first of all the nearest bus stop is detected which is Andheri. We can also see the bus that will be arriving at Andheri!!! 201. And it shows that the bus is reaching Jogeshwari and the final destination is Vile Parle.

<img src="https://github.com/oppasource/City-Bus-Tracking-Android-App/blob/master/images%20for%20this%20repo/6screen.png" width="250">    

<br/>
<br/>

# Final Changes

Here I have uploaded all the PHP files and set up the database on a free host. You can set up your own server with all the changes you want in the database, you need to update the domain name [here](https://github.com/oppasource/City-Bus-Tracking-Android-App/blob/master/MyApplication/app/src/main/java/com/example/yash/myapplication/MainActivity.java#L28) at this line. And if you are going to change the PHP file names, you would have to change the names by going into each individual Bgtask for that activity in the android side. Also you would have to change [this file](https://github.com/oppasource/City-Bus-Tracking-Android-App/blob/master/PHP%20files/init.php) according to your database parameters.



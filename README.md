<h1>WePack4U</h1>

https://user-images.githubusercontent.com/83853449/215983970-831880e6-e55b-48c6-a676-78cdc18e20d2.mp4

<h2>BACKGROUND</h2>
Our inspiration behind the WePack4U app stems from our common experience in the SUTD canteen where long queues and the lack of proper management of resources, especially during peak hours, leaves little to no time for students to be able to peacefully enjoy their meals, and results in the development of unhealthy eating habits as an quick and easy alternative.
This is particularly common during lunch time hours where students are frequently seen rushing to the canteen only to find it overcrowded with long waiting times for each stall. Furthermore, even if they do manage to buy their meal on time, they are only left with a few minutes to eat before the next lesson begins. Additionally, there is also a concern regarding flouting of safe distancing measures as multiple students are often seen cramming in queues to get their food on time.
WePack4U therefore provides an innovative solution to solve precisely this, by offering order-in-advance and self-pick up services for campus food thereby prolonging lunch breaks and reducing the risk of Covid-19 transmission. With WePack4U, you may order anytime and anywhere you want and enjoy a pleasant, crowd-free lunch time break.

<h2>SYSTEM ARCHITECTURE AND IMPLEMENTATION</h2>

<h3>System Architecture</h3>

![alt text](https://github.com/annmaryalen/WePack4U/blob/main/System%20Architecture.png)

<h3>Technical Overview</h3>

![alt text](https://github.com/annmaryalen/WePack4U/blob/main/Technical%20Overview.png)

We decided to put both the Login and Register page as an activity, as those are instances that don’t appear often. The Home, Check Out and Profile page on the other hand uses fragments as they are navigated through a navigation bar at the bottom of our app and widely used.
Majority of the data is also stored in our database (Firestore) such that major updates in the database will directly be reflected on the app. This prevents the need of users updating the app for any minor changes such as when adding an extra store thereby providing data persistence globally.

<h2>IMPLEMENTED FEATURES</h2>

1. <h4><i>Login Page</i><h4>
<ul>
  <li>ValidateInput() method to validate inputs</li>
  <li>Toasts are displayed if the user attempts to login with empty or invalid credentials</li>
  <li>Explicit Intent to Register Page</li>
</ul>

2. <h4><i>Register Page</i><h4>
<ul>
  <li>ValidateInput() method to validate inputs</li>
  <li>Toasts are displayed if the user attempts to register with empty or invalid inputs</li>
  <li>Finishes Activity onClick</li>
  <li>Parent set to Login Page in Android.XML</li>
  <li>Dropdown Menu implementation with AutoCompleteTextView & AdapterView</li> 
</ul>

3. <h4><i>Food Choice Page</i><h4>
<ul>
  <li>Designed as a Fragment to improve user experience</li>
  <li>Adopts RecyclerView to create views based on Database</li>
  <li>Serializing Database Data for RecyclerView (FoodItem, FoodMenu, FoodStore)</li>
  <li>Implementation of RecyclerItemClickListener to listen for click on RecyclerView</li>
</ul>


4. <h4><i>Payment and Confirmation Pages</i><h4>
<ul>
  <li>Designed as Fragments to improve user experience</li>
  <li>Adopted nested RecyclerViews to display the user’s cart in a flexible manner; users are able to tap on “Remove” to remove an item from the cart, and the RecyclerViews will update the display accordingly</li>
  <li>Used BigDecimal for accurate price calculations</li>
  <li>Toasts are displayed if the user attempts to checkout with an empty cart or without selecting a payment method</li>
   <li>When the user has not started a new order, pressing the cart icon on the bottom navigation bar brings the user to the confirmation page, showing their previous order(if any)</li>
  <li>If a user has added an item to the cart, pressing the cart icon brings the user to the payment page instead</li>
</ul>

1. <h4><i>About You Page</i><h4>
<ul>
  <li>ValidateInput() method to validate inputs</li>
  <li>Dropdown Menu implementation with AutoCompleteTextView & AdapterView</li>
  <li>Explicit Intent to Register Page</li>
</ul>

<h2>POSSIBLE FUTURE WORK</h2>

Possible future work could include the implementation of the following features 
-	Favorites Menu containing the most frequently ordered menu items from each stall
-	Chat options so stall owners can inform students about unexpected delays, unavailability of menu items due the the lack of ingredients etc.
-	Seat tracking mechanisms so students can check if there are available seats in the canteen where they can eat after having received their food 
-	Nutritional infographics such as calorie intake as per part of the meal information to promote healthy eating habits
Further, improvements can also be made to the UI such as making the app swipeable so users can intuitively switch between pages as an alternative to having to use the Navigation Bar.










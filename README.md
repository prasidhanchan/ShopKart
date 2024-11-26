## ShopKart

This is an Android shopping application built using Android Jetpack Compose with a Firebase backend. The app follows the MVVM (Model-View-ViewModel) architecture and has a minimum supported Android version of Android 10.

## Features
1) User authentication: The app allows users to create accounts, log in, and log out using Firebase Authentication.
2) Product listing: Users can view a list of products available for purchase.
3) Product details: Users can view detailed information about a specific product, including its price, description, and availability.
4) Shopping cart: Users can add products to their shopping cart and view the items they have selected for purchase.
5) Order placement: Users can place orders for the items in their shopping cart and receive confirmation of their purchase.
6) Order history: Users can view their order history, including details of past purchases.
7) Real-time updates: The app leverages Firebase Realtime Database or Firestore to provide real-time updates for product availability and order status.

## Screenshots

<div align="center">
<div>
<img src="https://github.com/prasidhanchan/ShopKart/assets/92362239/fb162bcd-0fda-4fbc-b22f-ac952f857bf3" width="30%"  alt="Login"/> <!-- Login -->
<img src="https://github.com/prasidhanchan/ShopKart/assets/92362239/6973e13e-9622-43dd-9ab4-7eb44e4b97ab" width="30%"  alt="SignUp"/> <!-- SignUp -->
<img src="https://github.com/prasidhanchan/ShopKart/assets/92362239/abdafeff-b6e9-4afe-a747-bd626ea4d96a" width="30%"  alt="Google Login"/> <!-- Google Login -->
<img src="https://github.com/prasidhanchan/ShopKart/assets/92362239/fcc26a12-a4d6-4bf8-9a24-4f775acc3f49" width="30%"  alt="Forgot Password"/> <!-- Forgot Password -->
<img src="https://github.com/prasidhanchan/ShopKart/assets/92362239/3c98b16b-8a24-48f2-b4cd-867c516c017b" width="30%"  alt="Splash"/> <!-- Splash -->
<img src="https://github.com/prasidhanchan/ShopKart/assets/92362239/de14c493-186f-40ba-b540-4c0f221b59b0" width="30%"  alt="Home"/> <!-- Home -->
<img src="https://github.com/prasidhanchan/ShopKart/assets/92362239/85af39a1-f8c0-4395-8025-cf97fd1cd3d9" width="30%"  alt="Details"/> <!-- Details -->
<img src="https://github.com/prasidhanchan/ShopKart/assets/92362239/f7caf91f-294b-4a9d-ae83-5a6f6c1eeea5" width="30%"  alt="Search"/> <!-- Search -->
<img src="https://github.com/prasidhanchan/ShopKart/assets/92362239/2ddcdb57-1877-4ea5-a0a4-36835fcde3c4" width="30%"  alt="My Orders"/> <!-- My Orders -->
<img src="https://github.com/prasidhanchan/ShopKart/assets/92362239/2d300ba9-11ea-49bd-9eda-96f511785f2d" width="30%"  alt="Order Details"/> <!-- Orders Details -->
<img src="https://github.com/prasidhanchan/ShopKart/assets/92362239/ab9da81d-f813-49c3-93a1-9b000585b3e3" width="30%"  alt="My Cart"/> <!-- My Cart -->
<img src="https://github.com/prasidhanchan/ShopKart/assets/92362239/b60db7d2-6688-4448-8349-5c787215b291" width="30%"  alt="My Profile"/> <!-- My Profile -->
<img src="https://github.com/prasidhanchan/ShopKart/assets/92362239/63b163d3-7cd3-4088-9334-6cdd3a4d7ebb" width="30%"  alt="Admin Profile"/> <!-- Admin Profile -->
<img src="https://github.com/prasidhanchan/ShopKart/assets/92362239/cdd1d7b0-7164-4f60-8690-ffa3bc3b11ab" width="30%"  alt="Admin"/> <!-- Admin -->
<img src="https://github.com/prasidhanchan/ShopKart/assets/92362239/00c488a9-d8da-4e3b-90e1-214578a048ee" width="30%"  alt="Employee Profile"/> <!-- Employee Profile -->
<img src="https://github.com/prasidhanchan/ShopKart/assets/92362239/c1032c42-21f3-485e-a802-ae111d076f81" width="30%"  alt="Employee"/> <!-- Employee -->
<img src="https://github.com/prasidhanchan/ShopKart/assets/92362239/036695e2-8013-451b-a445-4a184e43ae31" width="30%"  alt="Edit Profile"/> <!-- Edit Profile -->
<img src="https://github.com/prasidhanchan/ShopKart/assets/92362239/3ed19c52-991d-4b9c-b69e-908aff0fcdb7" width="30%"  alt="Logout"/> <!-- Logout -->
</div>
</div>

## Prerequisites
Before running the app, make sure you have the following:

1) Android Studio Flamingo 2022.2.1 or later.
2) Android SDK with a minimum API level of 29 (Android 10).
3) A Firebase project with the following enabled:
    - **Authentication**: Enable Email/Password and Google sign-in methods in the Firebase Authentication section.
    - **Firestore**: Set up Firestore in Native mode for database storage.
    - **Storage**: Enable Firebase Storage for storing user and product-related files (Have to upgrade your plan now to enable this option).
4) Google services JSON file (google-services.json) placed in the app module.

# Getting Started
1) Clone the repository: git clone https://github.com/Kawaki22/ShopKart.git Or Download the Zip file
2) Open the project in Android Studio.
3) Create a Firebase project and configure Firestore or Realtime Database.
4) Add your google-services.json file from your firebase console to the app module.
5) Enable Email/Password and Google sign-in method from Authentication section in firebase.
6) Add your Google Web Client Id in ShopKartUtils for Google Sign In to work. 

# Accounts and Login
This app has 3 types of logins
1) Users
2) Admin
3) Employee

Create an admin account from firebase console in the format admin.example@gmail.com

## Dependencies
The project uses the following dependencies:

1) Jetpack Compose: A modern UI toolkit for building native Android apps.
2) Firebase: Provides backend services for authentication, real-time updates, and database storage.
3) Coroutines: Provides asynchronous programming capabilities.
4) ViewModel: Part of the Jetpack library that provides a lifecycle-aware container for UI-related data.
5) Navigation: Handles navigation between different screens and features in the app.
6) Hilt: Used for dependency injection.

For a complete list of dependencies, refer to the build.gradle file in the app module.

## License
The project is licensed under the GPLv3 License. See the LICENSE file for more information.

## Acknowledgements
1) The Android Jetpack Compose team for their excellent work on the framework.
2) The Firebase team for providing a powerful backend infrastructure.

 ---

⭐ **If you like this project or find it useful, please give it a star! It helps to support my work and encourages me to create more.** 😊


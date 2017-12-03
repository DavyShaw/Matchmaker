# MatchMaker version 1.0.0

This application can only be run on Android Devices running Android version 6.0 (Marshmallow) or higher.

GENERAL USAGE NOTES
--------------------
This application requires a users to login before they can search for, create or join matches.
A sample login is therefore supplied.
  - Username: david@matchmakerapp.com
  - Password: android

This application uses the Firebase API for its functionality. Firebase needs a strong network connection to function. It is therefore recommended that when using the application that a Wi-Fi connection is used over a moblile connection.

Furthermore, due to the nature of Firebase, Google accounts are required for registration in the app. If you wish to test the register activity, you can use a dummy email and password, as long as the email ends in gmail.com. 

Minimum Requirements:
----------------------
1. The app makes use of the following interface elements: buttons, spinners, lists, dialogues, messages, toasts etc. 
2. The app includes the following distinctive activies: mainActivity, ProfileActivity, LoginActivity, SearchResults, RegisterScreen, MatchPreferencesActivity and MatchDetailsActivity. 
3. The app stores data locally in an SQLite database - this can be tested by creating an event, joining an event or updating user preferences and then returning to the user's profile page - the new information will now be displayed. 
4. Two web services are used in the application: The Google Maps API and the Geocoding API. 
5. The application is connected to the Google Maps application externally. This can be checked by clicking on the map button at the top right hand corner when in the match details page, this button will then display a map fragment with the location of the event marked. If you click the Google Maps icon at the bottom right of the fragment it will bring you to the external application. 

### Authors:
 - Emma Byrne
 - Pamela Kelly
 - Cara Delorey
 - Andrew Cameron
 - Davy Shaw

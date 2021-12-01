# CakeShop
CakeShop application for Waracle

# Overview Details of implimentation

Android studio Version : Arctic Fox 2020.3.1

MinSdk version : 23

compile Sdk version : 31

Architectural design pattern used: MVVM

Following clean architecture design principles

For unit tests I have implemented both Mock it/Fake it approach just for demonstration purpose.

Used Dagger Hilt for dependecy injection.

Used Retrofit2, OkHttp3 for making API calls

Used coroutines and Flows for running background tasks and filtering/transforming the data.

Assumption1: Sort in ascending order based on title.

Assumption2: Filter unique items, based on title.

Used Glide for loading image from url to ImageView

TODO1 : Better error handling by implementing Interceptor for the client. Doing so we can pass custom exceptions based on the scenario and with clear custom error messages.

TODO2 : Dint spend much time on UI, like maintaining image aspect ration, beautifying actual layout for landscape and portrait

TODO3 : improve implementation of showing details and error dialogs (Would have one custom dialog)

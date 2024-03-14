# LaunchPad Google Utils

LaunchPad Google Utils is a Kotlin Multiplatform library for iOS and Android. It provides convenience functions to quickly access various Google APIs.

## Services

Currently, the library includes two services:

1. **Air Quality API Service**: 
   - `getCurrentAqiConditions(latitude: Double, longitude: Double)` : Returns the current air quality index conditions based on the provided latitude and longitude.

2. **Places API Service**:
   - `placesAutocomplete(textInput: String)`: Returns a flow of lists of autocomplete predictions based on the provided text input.
   - `getPlaceLocationByPlaceId(placeId: String)`: Returns a Flow of the latitude and longitude based on the provided place ID.

More API services are coming soon!

## Prerequisites

To use this library, you need a Google Maps API key with billing enabled. For information on creating API keys, please follow the steps on [this link](https://developers.google.com/maps/documentation/android-sdk/get-api-key) or [this link](https://developers.google.com/maps/documentation/ios-sdk/get-api-key).

## Installation

To install the library, add the following dependency to your `libs.versions.toml` file:

```toml
[versions]
...
launchpad-google-utils = "0.0.2"

[libraries]
...
launchpad-google-utils = { group = "com.github.bottlerocketstudios", name = "kmp-launchpad-google-utils", version.ref = "launchpad-google-utils" }
```

and this to the module level `build.gradle.kts` file:
```kotlin
commonMain.dependencies {
    ...
    implementation(libs.launchpad.google.utils)
}
```

## Usage

Here's an example of how to use the Air Quality API service:

```kotlin
val airQualityApiService = AirQualityApiService(MAPS_API_KEY)

// Get current air quality index 
airQualityApiService.getCurrentAqiConditions(37.7749, -122.4194)
// returns
CurrentConditionsResponseDto(
   dateTime = "2024-03-14T17:00:00Z",
   regionCode = "us",
   aqiConditions = listOf(
      AqiConditionsDto(
         code = "uaqi",
         displayName = "Universal AQI",
         aqi = 76,
         aqiDisplay = "76",
         color = ColorDto(
            red = 0.4117647,
            green = 0.77254903,
            blue = 0.20392157,
            alpha = null
         ),
         category = "Good air quality",
         dominantPollutant = "o3"
      ), AqiConditionsDto(
         code = "usa_epa",
         displayName = "AQI(US)",
         aqi = 36,
         aqiDisplay = "36",
         color = ColorDto(
            red = null,
            green = 0.89411765,
            blue = null,
            alpha = null
         ),
         category = "Good air quality",
         dominantPollutant = "pm25"
      )
   )
)
```

And here's an example of how to use the Places API service:

```kotlin
val placesService = PlacesApiService(MAPS_API_KEY)

// Places Autocomplete
placesService.placesAutocomplete("San Francisco")
// returns
listOf(
   PlaceAutocompleteResultModel(
      primaryText = "San Francisco",
      secondaryText = "CA, USA",
      placeId = "ChIJIQBpAG2ahYAR_6128GcTUEo"
   ),
   PlaceAutocompleteResultModel(
      primaryText = "San Francisco International Airport (SFO)",
      secondaryText = "San Francisco, CA, USA",
      placeId = "ChIJVVVVVYx3j4ARP-3NGldc8qQ"
   ),
   PlaceAutocompleteResultModel(
      primaryText = "San Francisco del Rincón",
      secondaryText = "Guanajuato, Mexico",
      placeId = "ChIJ3bwFNq7DK4QR1ZorLLDndeA"
   ),
   PlaceAutocompleteResultModel(
      primaryText = "San Francisco",
      secondaryText = "Córdoba Province, Argentina",
      placeId = "ChIJZ9DBiyIoy5UR9AA3jP6rBI8"
   ),
)

// Get places location (lat/long) by Place ID
placesService.getPlaceLocationByPlaceId("ChIJVVVVVYm3j4ARjk_Y3dQzH2I")
// returns
LatLngDto(
   latitude=40.75,
   longitude=-73.99
)
```

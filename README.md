## Building a Restaurant Dataset for Stuttgart, Germany  

This collection of Jupyter Notebooks obtains a data set about restaurants in the area of Stuttgart, Germany. They are part of a prototype for a Voice Assistant that lives inside a smartphone app and gives recommendations for places to eat and drink that the user might like.

The dataset includes information such as restaurant name, address, location, (Yelp) categories describing the food or restaurant type, as well as up to 50 user reviews. Further, additional characterizing keywords such as 'Panorama', 'Weinkarte' or 'exzellent' are retrieved from the user reviews using NLP. 

The data are gathered from different sources (Yelp, Google Places), under the restriction that from Google Places only the restaurant IDs are saved permanentely, to comply with the Places API policies. (In contrast, Yelp allows to store content for non-commercial purposes.) In the current version, the reviews are downloaded via Wextractor, which is to be replaced by a regular Yelp API access, as soon as our application is granted access to Yelp Fusion VIP.

To run the notebooks, API keys for Google Places, Yelp and Wextractor are required.

![Overview](https://github.com/jpeltason/create-restaurant-dataset/blob/main/restaurant_download.png)


### explore_google_places_search_params

Some exploration of different search centers and ranking parameters, to understand which configuration is most suitable for our purpose.  

### download_google_places_restaurant_ids

Downloads restaurant data from GooglePlaces within a given rectangle. 

The aim of this notebook is to obtain **all** relevant restaurants (not the **most prominent ones**) within the given area. To overcome the limit of 20 (or 3x20) results, we do multiple queries with different search centers, using Places `nearby_search` with distance-based ranking.

Uses python-google-places as a Python wrapper around the Google Places API.\
https://github.com/slimkrazy/python-google-places \
https://developers.google.com/maps/documentation/places/web-service/ 

**Output**: DataFrame with Google IDs, written to google_restaurant_ids.csv.

### download_yelp_restaurant_data

This notebook starts with a set of Google IDs, extends this set with restaurant IDs found at Yelp and augments it with additional restaurant information from Yelp. The following steps are performed:
- Reads in Google IDs, lookup name and geolocation at Google Places and find their correspondance at Yelp (based on name and geolocation)
- Since only for part of the IDs a matching can be found, extends the list with results from Yelp. To obtain a good spatial coverage, multiple Yelp `search_query`s around different search centers are performed.
- Merges both lists and looks up the required restaurat information such as name, location, categories at Yelp.
- Saves the Yelp restaurant information and Google ID. 

Uses yelpapi as a Python wrapper around the Yelp Fusion API.\
https://pypi.org/project/yelpapi/ \
https://www.yelp.com/fusion

**Input**: DataFrame with Google IDs (google_restaurant_ids.csv)\
**Output**: DataFrame with Yelp IDs based on Yelp search (yelp_ids_based_on_yelp_search.csv), Merged DataFrame with Yelp IDs (yelp_ids.csv), DataFrame with restaurant information from Yelp (yelp_restaurants.csv)

### download_google_reviews

Downloads user reviews for a given set of Google Places restaurant IDs using Wextractor (up to 50 reviews per restaurarant). This part is planned to be replaced with a regular Yelp access via Yelp Fusion VIP. User names are pseudonymized.

**Input**: DataFrame with restaurants, each one refering its Google ID (yelp_restaurants.csv) \
**Output**: DataFrame with up to 50 user reviews per Google ID (reviews.csv)

### calculate_additional_restaurant_features

This notebooks extracts additional keywords (features) from the user reviews that might be relevant for charactarizing restaurants further, such as 'Panorama', 'Weinkarte' or 'exzellent'.

This is done by assigning each restaurant a `tf_idf score` (based on its reviews) for a set of given keywords. For this purposed, reviews are preprocessed with normalization, punctuation and stopword removal, and lemmatization. To be able to count synonyms and word with similar meaning as one common feature, synonym handling is done by providing synonym lists (mapping from literals to canonical). Also, a simple form of negation handling is realized by taking into account only postitive reviews for features analysis, to make sure that the mentioned term actually refers to the restaurant in a positive way.

**Input**: DataFrame with restaurants (yelp_restaurants.csv), DataFrame with user reviews (reviews.csv) \
**Output**: DataFrame with restaurants, augmented with newly derived features as columns (restaurants_df_augmented.csv)

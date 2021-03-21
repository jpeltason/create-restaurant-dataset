## Downloading Restaurant Data and Reviews from GooglePlaces 

Two Jupyter notebooks to obtain a data set about restaurants in a given area.

### download_google_places_restaurant_data.ipynb

Downloads restaurants data from GooglePlaces within a given rectangle. This requires a Google API key.

We want to cover **all** restaurants within a given quadrangle, instead of the **most prominent ones**. Therefore, the Notebook does nearby queries, ranked by distance. 

To overcome the limit of 20 (or 3x20) results, we do multiple such queries for several locations (specified by a given step size), then merging and de-duplicating the results.


### download_google_places_restaurant_reviews.ipynb

For a given data set of Google Places restaurant IDs, downloads user reviews for the resulting restaurant list via Wextractor. 
The number of reviews to download per restaurant can be chosen as required (specified by a given offset number).

Makes use of Wextractor (https://wextractor.com/), therefore requires a valid Wextractor API key.


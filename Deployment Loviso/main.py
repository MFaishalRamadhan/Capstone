from flask import Flask, jsonify, request
from sklearn.preprocessing import LabelEncoder
import joblib
import numpy as np
import pandas as pd
import math
import tensorflow as tf
import random

app = Flask(__name__)

location_model = joblib.load('model.joblib')
places_dataset = pd.read_excel('Places_Dataset.xlsx')
collaborative_model = tf.keras.models.load_model('model.h5')
users_dataset = pd.read_excel('Users_Dataset.xlsx')

@app.route('/locations', methods=['POST'])
def predicts():
    # Get the JSON data from the request
    user_latitude = request.json.get('user_latitude')
    user_longitude = request.json.get('user_longitude')
    user_location = (user_latitude, user_longitude)

    # Create a feature vector from the longitude and latitude
    features = np.array([user_longitude, user_latitude]).reshape(1, -1)

    # Predict the label using the K-means model
    label = location_model.predict(features)

    # Filter locations in the same cluster as the predicted label
    cluster_locations = places_dataset[places_dataset['cluster_labels'] == label[0]]

    cluster_locations.loc[:, 'distance'] = cluster_locations.apply(
        lambda row: calculate_distance(user_location, (row['Latitude'], row['Longitude'])), axis=1)

    # Sort locations by distance and number of ratings
    location_recommendations = cluster_locations.sort_values(['distance', 'Num_Rating'], ascending=[True, False])

    # Filter out any duplicate places
    location_recommendations = location_recommendations.drop_duplicates(subset=['Name'])

    # Filter recommendations for each category
    restaurant_recommendations = location_recommendations[
        location_recommendations['Category'] == 'Tempat Makan'].head(2)
    cafe_recommendation = location_recommendations[
        location_recommendations['Category'] == 'Kafe'].head(1)
    souvenir_recommendation = location_recommendations[
        location_recommendations['Category'] == 'Toko Suvenir'].head(1)
    oleh_oleh_recommendation = location_recommendations[
        location_recommendations['Category'] == 'Toko Oleh-Oleh'].head(1)

    # Combine all the recommendations
    all_location_recommendations = pd.concat([restaurant_recommendations,
                                              cafe_recommendation,
                                              souvenir_recommendation,
                                              oleh_oleh_recommendation])

    # Create a list of recommended place dictionaries
    location_recommended = []
    for _, row in all_location_recommendations.iterrows():
        result = {
            'place_id': row['Place_ID'],  # Add the place ID to the result
            'name': row['Name'],
            'rating': row['Rating'],
            'address': row['Address'],
            'num_ratings': row['Num_Rating'],
            'category': row['Category'],
            'image': row['Image'],
            'url': row['URL'],
            'distance': f"{round(row['distance'], 2)} km"
        }
        location_recommended.append(result)
    # Create a JSON response object
    response_location = {
        'locationrecommendations': location_recommended
    }
    # Return the recommended places as a JSON response
    return jsonify(response_location)

@app.route('/collaborative', methods=['POST'])
def get_recommendations():
    # Get the JSON data from the request
    user_latitude = request.json.get('user_latitude')
    user_longitude = request.json.get('user_longitude')
    user_location = (user_latitude, user_longitude)

    # Get all unique places
    unique_places = users_dataset['Place_ID'].unique()

    # Encode the categorical variables
    user_encoder = LabelEncoder()
    place_encoder = LabelEncoder()
    users_dataset['user_encoded'] = user_encoder.fit_transform(users_dataset['User_ID'])
    users_dataset['place_encoded'] = place_encoder.fit_transform(users_dataset['Place_ID'])

    # Generate a random user ID
    user_id = random.choice(users_dataset['User_ID'].unique())

    # Get recommendations for the specific user
    user_id_encoded = user_encoder.transform([user_id])[0]
    user_input = np.full(len(unique_places), user_id_encoded)
    place_input = np.arange(len(unique_places))

    # Predict ratings for the user and sort them in descending order
    predictions = collaborative_model.predict([user_input, place_input]).flatten()
    sorted_indices = np.argsort(predictions)[::-1]

    # Get the top 5 recommendations
    top_5_collaborative_places = unique_places[sorted_indices[:5]]

    # Get the recommended places' details
    collaborative_recommendations = users_dataset[users_dataset['Place_ID'].isin(top_5_collaborative_places)][
        ['Place_ID', 'Name', 'Rating_y', 'Num_Rating', 'Latitude', 'Longitude',
         'Category', 'Address', 'Image', 'URL']]. \
        sort_values('Rating_y', ascending=False)

    # Calculate and add the distance for each recommended place
    collaborative_recommendations['distance'] = collaborative_recommendations.apply(
        lambda row: calculate_distance(user_location, (row['Latitude'], row['Longitude'])), axis=1)
    collaborative_recommendations['distance'] = collaborative_recommendations['distance'].apply(lambda d: f"{round(d, 2)} km")

    # Filter out any duplicate places
    collaborative_recommendations = collaborative_recommendations.drop_duplicates(subset=['Name'])

    # Create a list of recommended place dictionaries
    collaborative_recommended = []
    for _, row in collaborative_recommendations.iterrows():
        distance_str = row['distance'].split(' ')[0]
        distance = float(distance_str)
        place = {
            'place_id': row['Place_ID'],  # Add the place ID to the result
            'name': row['Name'],
            'rating': row['Rating_y'],
            'num_ratings': row['Num_Rating'],
            'category': row['Category'],
            'address': row['Address'],
            'image': row['Image'],
            'url': row['URL'],
            'distance': f"{round(distance, 2)} km"
        }
        collaborative_recommended.append(place)
    # Create a JSON response object
    response_collaborative = {
        'collaborativerecommendations': collaborative_recommended
    }
    # Return the recommended places as a JSON response
    return jsonify(response_collaborative)

def calculate_distance(location1, location2):
    lat1, lon1 = location1
    lat2, lon2 = location2
    # Calculate the distance using the Haversine formula
    dlat = math.radians(lat2 - lat1)
    dlon = math.radians(lon2 - lon1)
    a = math.sin(dlat / 2) ** 2 + math.cos(math.radians(lat1)) * math.cos(math.radians(lat2)) * math.sin(dlon / 2) ** 2
    c = 2 * math.atan2(math.sqrt(a), math.sqrt(1 - a))
    distance = 6371 * c  # Earth's radius is approximately 6371 km

    return distance

if __name__ == '__main__':
    app.run()

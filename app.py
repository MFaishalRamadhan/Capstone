from flask import Flask, jsonify, request
import joblib
import numpy as np
import pandas as pd

app = Flask(__name__)

kmeans = joblib.load('modelv2.joblib')
places = pd.read_excel('Cluster_Dataset.xlsx')

@app.route('/predicts', methods=['POST'])
def predicts():
    data = request.get_json()  # Get the JSON data from the request
    user_longitude = data['longitude']
    user_latitude = data['latitude']
    # Create a feature vector from the longitude and latitude
    features = np.array([user_longitude, user_latitude]).reshape(1, -1)

    # Predict the label using the K-means model
    label = kmeans.predict(features)
    # Filter locations in the same cluster as the predicted label
    cluster_locations = places[places['cluster_labels'] == label[0]]

    cluster_locations.loc[:, 'distance'] = ((cluster_locations['Longitude'] - user_longitude) ** 2 +
                                     (cluster_locations['Latitude'] - user_latitude) ** 2) ** 0.5
    # Sort locations by distance and select the top 5 recommendations
    recommendations = cluster_locations.sort_values('distance').head(5)

    # Create a list of recommended place dictionaries
    recommended_places = []
    for _, row in recommendations.iterrows():
        place = {
            'name': row['Name'],
            'rating': row['Rating'],
            'address': row['Address'],
            'num_ratings': row['Num_Rating'],
            'category': row['Category'],
            'image': row['Image'],
            'url': row['URL'],
            'label_kecamatan': row['Label_Kecamatan']
        }
        recommended_places.append(place)

    # Return the recommended places as a JSON response
    return jsonify(recommended_places)

if __name__ == '__main__':
    app.run()

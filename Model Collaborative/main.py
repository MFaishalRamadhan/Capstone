from flask import Flask, jsonify
from sklearn.preprocessing import LabelEncoder
import random
import numpy as np
import pandas as pd
import tensorflow as tf

app = Flask(__name__)

model = tf.keras.models.load_model('model.h5')
merged_df = pd.read_excel('Dataset.xlsx')

# Define the route for the recommendations
@app.route('/recommendations', methods=['GET'])
def get_recommendations():
    # Get all unique places
    unique_places = merged_df['Place_ID'].unique()

    # Encode the categorical variables
    user_encoder = LabelEncoder()
    place_encoder = LabelEncoder()
    merged_df['user_encoded'] = user_encoder.fit_transform(merged_df['User_ID'])
    merged_df['place_encoded'] = place_encoder.fit_transform(merged_df['Place_ID'])

    # Generate a random user ID
    user_id = random.choice(merged_df['User_ID'].unique())

    # Get recommendations for the specific user
    user_id_encoded = user_encoder.transform([user_id])[0]
    user_input = np.full(len(unique_places), user_id_encoded)
    place_input = np.arange(len(unique_places))

    # Predict ratings for the user and sort them in descending order
    predictions = model.predict([user_input, place_input]).flatten()
    sorted_indices = np.argsort(predictions)[::-1]

    # Get the top 5 recommendations
    top_5_places = unique_places[sorted_indices[:5]]

    # Get the recommended places' details
    recommended_places = merged_df[merged_df['Place_ID'].isin(top_5_places)][['Name', 'Rating_y', 'Num_Rating',
                                                                              'Category', 'Address', 'Image', 'URL']]. \
        sort_values('Rating_y', ascending=False)

    # Remove duplicates based on Place_ID
    recommended_places = recommended_places.drop_duplicates(subset='Name')

    # Convert recommended places to JSON
    recommendations_json = recommended_places.to_dict(orient='records')

    # Return the recommendations as JSON response
    return jsonify(recommendations_json)

if __name__ == '__main__':
    app.run()

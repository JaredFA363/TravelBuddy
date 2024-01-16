from flask import Blueprint, render_template, request, flash, session
import requests, json

views = Blueprint('views', __name__)

@views.route('/', methods = ['GET', 'POST'])
def home():        
    if request.method == 'POST':
        trip_id = int(request.form.get('interest'))
        json_id = get_proposed_user_id(trip_id)
        proposed_id = json_id.get('ans', '')
        jsonuser_id = get_user_id(session.get('username'))
        user_id = jsonuser_id.get('ans', '')

        if proposed_id != -1 and user_id != -1:
            server_url = f'http://localhost:8080/TravelBuddyServer/webresources/Trip/expressInterest'
            data = {'trip_id':trip_id, 'proposed_user_id': proposed_id, 'new_user_id': user_id}
            json_data = json.dumps(data)
            headers = {'Content-Type': 'application/json'}
            response = requests.post(server_url, data=json_data, headers=headers)
            flash('Success Expressed Interest on trip', 'Success')
        else:
            flash('No Trip/User found', 'error')


    server_url = 'http://localhost:8080/TravelBuddyServer/webresources/Trip/getAllTrips'
    response = requests.get(server_url)
    if response.status_code == 200:
        # Parse the JSON response
        trips_data = json.loads(response.text)
        processed_trips_data = process_weather_data(trips_data)
        return render_template('home.html', trips_data=processed_trips_data)
    else:
        return render_template('home.html')


def process_weather_data(trips_data):
    processed_data = []
    for trip in trips_data:
        trip_data = {
            'trip_id': trip['trip_id'],
            'date_from': trip['date_from'],
            'date_to': trip['date_to'],
            'location_from': trip['location_from'],
            'location_to': trip['location_to'],
            'weather': json.loads(trip['weather'])['filteredForecastDays']
        }
        processed_data.append(trip_data)
    return processed_data


@views.route('/search', methods = ['POST'])
def search():
    if request.method == 'POST':
        server_url = 'http://localhost:8080/TravelBuddyServer/webresources/Trip/queryTrip'
        location_from = request.form.get('fromSearch')
        location_to = request.form.get('toSearch')
        params = {'location_from': location_from, 'location_to': location_to}
        json_data = json.dumps(params)
        try:
            response = requests.post(server_url, data=json_data)
            if response.status_code == 200:
            # Parse the JSON response
                #trips_data = json.loads(response.text)
                json_data = response.json()
                inner_json_str = json_data.get("ans", "")
                trips_data = json.loads(inner_json_str)
                #trips_data = response.json()
                processed_trips_data = process_weather_data(trips_data)
                return render_template('home.html', trips_data=processed_trips_data)
            else:
                return render_template('home.html')
        except Exception as e:
            print(f'Request failed: {e}')

def get_proposed_user_id(trip_id):
    server_url = 'http://localhost:8080/TravelBuddyServer/webresources/User/getProposedID'
    params = {'ans': str(trip_id)}
    json_data = json.dumps(params)
    print(json_data)
    try:
        response = requests.post(server_url, data=json_data)
        if response.status_code == 200:
            return response.json() 
        else:
            print(f'Error: {response.status_code}')
    except Exception as e:
        print(f'Request failed: {e}')

def get_user_id(username):
    server_url = 'http://localhost:8080/TravelBuddyServer/webresources/User/getID'
    params = {'ans': username}
    json_data = json.dumps(params)
    try:
        response = requests.post(server_url, data=json_data)
        if response.status_code == 200:
            return response.json() 
        else:
            print(f'Error: {response.status_code}')
    except Exception as e:
        print(f'Request failed: {e}')


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

        if proposed_id != -1 and user_id != 1:
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
        return render_template('home.html', trips_data=trips_data)
    else:
        return render_template('home.html')

@views.route('/search')
def search():
    if request.method == 'GET':
        server_url = 'http://localhost:8080/TravelBuddyServer/webresources/Trip/queryTrip'
        location_from = request.args.get('fromSearch')
        location_to = request.args.get('toSearch')
        params = {'location_from': location_from, 'location_to': location_to}
        try:
            response = requests.get(server_url, params=params)
            if response.status_code == 200:
            # Parse the JSON response
                #trips_data = json.loads(response.text)
                json_data = response.json()
                inner_json_str = json_data.get("ans", "")
                trips_data = json.loads(inner_json_str)
                #trips_data = response.json()
                return render_template('home.html', trips_data=trips_data)
            else:
                return render_template('home.html')
        except Exception as e:
            print(f'Request failed: {e}')

def get_proposed_user_id(trip_id):
    server_url = 'http://localhost:8080/TravelBuddyServer/webresources/User/getProposedID'
    params = {'TripId': trip_id}

    try:
        response = requests.get(server_url, params=params)
        if response.status_code == 200:
            return response.json() 
        else:
            print(f'Error: {response.status_code}')
    except Exception as e:
        print(f'Request failed: {e}')

def get_user_id(username):
    server_url = 'http://localhost:8080/TravelBuddyServer/webresources/User/getID'
    params = {'username': username}

    try:
        response = requests.get(server_url, params=params)
        if response.status_code == 200:
            return response.json() 
        else:
            print(f'Error: {response.status_code}')
    except Exception as e:
        print(f'Request failed: {e}')
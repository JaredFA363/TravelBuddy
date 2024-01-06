from flask import Blueprint, render_template, request, session, flash
import requests
import json

trip = Blueprint('trip', __name__)

@trip.route('/book', methods = ['GET', 'POST'])
def book():
    if request.method == 'POST':
        action = request.form.get('action')
        date_from = request.form.get('date_from')
        date_to = request.form.get('date_to')
        location_from = request.form.get('location_from')
        location_to = request.form.get('location_to')

        if action == 'book_trip':
            json_id = get_user_id(session.get('username'))
            user_id = json_id.get('ans', '')
            if user_id != -1:
                server_url = f'http://localhost:8080/TravelBuddyServer/webresources/Trip/insertTrip'
                data = {'userId':user_id, 'dateFrom': date_from, 'dateTo':date_to, 'locationFrom':location_from, 'locationTo':location_to}
                json_data = json.dumps(data)
                headers = {'Content-Type': 'application/json'}
                response = requests.post(server_url, data=json_data, headers=headers)
                flash('Trip created', 'Success')
            else:
                flash('No user found', 'error')
        elif action == 'check_weather':
            weather_data = get_weather_data(location_to, date_from, date_to) 
            return render_template('booktrip.html', weather_data=weather_data)

    return render_template("booktrip.html")

@trip.route('/yourTrips', methods=['GET','POST'])
def yourTrips():
    if request.method == 'POST':
        server_url = 'http://localhost:8080/TravelBuddyServer/webresources/Trip/getyourTrips'
        json_id = get_user_id(session.get('username'))
        userId = int(json_id.get('ans', ''))
        params = {'userId' : userId}
        json_data = json.dumps(params)
        print(json_data)
        headers = {'Content-Type': 'application/json'}
        response = requests.post(server_url, data=json_data, headers=headers)

        if response.status_code == 200:
            # Parse the JSON response
            trips_data = json.loads(response.text)
            print(trips_data)
            return render_template('yourtrips.html', trips_data=trips_data)
        else:
            return render_template('yourtrips.html')
    else:
        return render_template('yourtrips.html')

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

def get_weather_data(location_to, date_from, date_to):
    server_url = 'http://localhost:8080/TravelBuddyServer/webresources/Trip/CheckWeather'
    params = {'location_to': location_to, 'date_from': date_from, 'date_to':date_to}
    json_data = json.dumps(params)
    try:
        response = requests.post(server_url, data=json_data)
        if response.status_code == 200:
            return response.json() 
        else:
            print(f'Error: {response.status_code}')
    except Exception as e:
        print(f'Request failed: {e}')
from flask import Blueprint, render_template, request
import requests
import json

auth = Blueprint('auth', __name__)

@auth.route('/login')
def login():
    return render_template("login.html")

@auth.route('/logout')
def logout():
    return "<h1>Loout<h1>"

@auth.route('/register', methods = ['GET', 'POST'])
def register():
    if request.method == 'POST':
        username = request.form.get('username')
        name = request.form.get('name')
        password = request.form.get('password')

        #server_url = f'http://localhost:8080/TravelBuddyServer/webresources/User/queryUser?username={username}&password={password}'
        #response = requests.get(server_url)

        server_url = f'http://localhost:8080/TravelBuddyServer/webresources/User/insertUser'
        data = {'username': username, 'name': name, 'password': password}
        json_data = json.dumps(data)
        headers = {'Content-Type': 'application/json'}
        response = requests.post(server_url, data=json_data, headers=headers)
        print(response.text)

    return render_template("register.html")
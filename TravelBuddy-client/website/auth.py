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

        user_data = {
            "username": username,
            "name": name,
            "password": password
        }

        json_data = json.dumps(user_data)
        orchestrator_url = "http://localhost:8080/TravelBuddy/webresources/Orchestrator/saveUserData"
        headers = {"Content-Type": "application/json"}
        response = requests.post(orchestrator_url, data=json_data, headers=headers)
        print(response.text)

    return render_template("register.html")
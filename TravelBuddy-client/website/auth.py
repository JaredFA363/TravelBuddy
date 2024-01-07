from flask import Blueprint, render_template, request, session, redirect, url_for, flash
import requests
import json

auth = Blueprint('auth', __name__)

@auth.route('/login', methods = ['GET', 'POST'])
def login():
    if request.method == 'POST':
        username = request.form.get('username')
        password = request.form.get('password')

        server_url = f'http://localhost:8080/TravelBuddyServer/webresources/User/queryUser'
        data = {'username': username, 'password': password}
        json_data = json.dumps(data)
        headers = {'Content-Type': 'application/json'}
        response = requests.post(server_url, data=json_data, headers=headers)

        if response.status_code == 200:
            if response.json().get('ans', ''):
                success_message = response.json().get('ans', '')
                if success_message == 'Success':
                    session['username'] = username
                    return redirect(url_for('views.home'))
                else:
                    flash('Login failed. Please try again.', 'error')
                    return redirect(url_for('auth.login'))
            else:
                flash('Login failed. Please try again.', 'error')
                return redirect(url_for('auth.login'))


    return render_template("login.html")

@auth.route('/logout')
def logout():
    session.pop('username', None)
    flash('Logout successful', 'Success')
    return redirect(url_for('auth.login'))

@auth.route('/register', methods = ['GET', 'POST'])
def register():
    if request.method == 'POST':
        username = request.form.get('username')
        name = request.form.get('name')
        password = request.form.get('password')

        server_url = f'http://localhost:8080/TravelBuddyServer/webresources/User/insertUser'
        data = {'username': username, 'name': name, 'password': password}
        json_data = json.dumps(data)
        headers = {'Content-Type': 'application/json'}
        response = requests.post(server_url, data=json_data, headers=headers)

        if response.status_code == 200:
            if response.json().get('ans', ''):
                success_message = response.json().get('ans', '')
                if success_message == 'Success':
                    return redirect(url_for('auth.login'))
                else:
                    flash('Registration failed. Please try again.', 'error')
                    return redirect(url_for('auth.register'))
            else:
                flash('Registration failed. Please try again.', 'error')
                return redirect(url_for('auth.register'))

    return render_template("register.html")
from flask import Flask

def create_app():
    app = Flask(__name__)
    app.secret_key = "dvffbgbghbhgnh"
    
    from .views import views
    from .auth import auth
    from .trip import trip

    app.register_blueprint(views, url_prefix= '/')
    app.register_blueprint(auth, url_prefix= '/')
    app.register_blueprint(trip, url_prefix= '/')

    return app
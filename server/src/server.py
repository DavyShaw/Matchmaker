from flask import Flask
from flask_restful import Api

from user_resource import UserResource
from event_resource import EventResource


if __name__ == '__main__':
    app = Flask(__name__)
    api = Api(app)

    # The Resources tell the api what to do with requests to the endpoint
    api.add_resource(UserResource, '/user')
    api.add_resource(EventResource, '/event')

    app.run(debug=True)

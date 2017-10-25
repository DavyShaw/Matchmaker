import random

from flask_restful import Resource
from flask import request, jsonify

from data_io import IO
from utils import find
from config import USERS_FILE


USER_IO = IO(USERS_FILE)


class UserResource(Resource):

    def put(self):
        try:
            user = {'name': request.json['name']}

        # If the request doesn't contain JSON or there is no 'name' field,
        # one of these exceptions should be raised
        except (KeyError, AttributeError) as e:
            return {'Error':
                    'Request body must be JSON and have a `name` attribute'
                    }, 422
        try:
            # Select a random integer up to a big number for the unique user ID.
            # Not ideal, but will suffice for now.
            user['id'] = random.randint(0, 100000000000)
            USER_IO.save(user)

        # In case there is for some reason an error saving the user, still return
        # a response letting the client know.
        except Exception as e:
            print(e)
            return {'Error': 'Server Error: cannot save user'}, 500

        # All went well, let the client know what went down!
        return {
            'msg': 'OK',
            'saved user': user
        }

    # READ an existing user
    # Traditionally you'd use a GET request for this type of thing, but
    # by using POST we can use a json request body, so might make it easier to
    # perform more complex queries down the line?
    def post(self):
        # check to see if the request contains JSON and has an 'id' field
        try:
            user_id = int(request.json['id'])
        except (KeyError, AttributeError, ValueError):
            return {'Error':
                    'Request body must be JSON and have an `id` attribute which is intger-y'
                    }, 422

        # Read all users from file
        try:
            users = USER_IO.read()

        # Hopefully this will never happen...
        except Exception as e:
            print(e)
            return {'Error': 'Server Error: Could not load users'}, 500

        # find the user that matches the user id
        user = find(lambda user: user['id'] == user_id, users)
        if user:
            return {'msg': 'OK', 'user': user}

        return {'Error': 'user_not_found'}, 422

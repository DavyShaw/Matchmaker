import random

from flask_restful import Resource
from flask import request, jsonify

from data_io import IO
from utils import find
from config import EVENTS_FILE

# Used for reading and writing events data
EVENT_IO = IO(EVENTS_FILE)


class EventResource(Resource):

    def put(self):
        try:
            event = {
                'title': request.json['title'],
                'category': request.json['category'],
                'datetime': request.json['datetime'],
                'location': request.json['location'],
                'organiser': request.json['organiser']
            }
        # If the request doesn't contain JSON or there is no 'name' field,
        # one of these exceptions should be raised
        except (KeyError, AttributeError) as e:
            return {'Error':
                    'Request body must be JSON and have at least `title`, `category`, `datetime`, `location` and `organiser` attributes'
                    }, 422
        try:
            # Select a random integer up to a big number for the unique user ID.
            # Not ideal, but will suffice for now.
            event['id'] = random.randint(0, 100000000000)
            EVENT_IO.save(event)

        # In case there is for some reason an error saving the user, still return
        # a response letting the client know.
        except Exception as e:
            print(e)
            return {'Error': 'Server Error: cannot save event'}, 500

        # All went well, let the client know what went down!
        return {
            'msg': 'OK',
            'saved event': event
        }

    # READ an existing user
    # Traditionally you'd use a GET request for this type of thing, but
    # by using POST we can use a json request body, so might make it easier to
    # perform more complex queries down the line?
    def post(self):
        # check to see if the request contains JSON and has an 'id' field
        try:
            event_id = int(request.json['id'])
        except (KeyError, AttributeError, ValueError):
            return {'Error':
                    'Request body must be JSON and have an `id` attribute which is intger-y'
                    }, 422

        # Read all users from file
        try:
            events = EVENT_IO.read()

        # Hopefully this will never happen...
        except Exception as e:
            print(e)
            return {'Error': 'Server Error: Could not load events'}, 500

        # find the user that matches the user id
        event = find(lambda event: event['id'] == event_id, events)

        if event:
            return {'msg': 'OK', 'event': event}

        return {'Error': 'event_not_found'}, 422

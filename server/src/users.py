import os
import json

from config import USERS_FILE


class Users:
    """Provides static methods for reading and saving users"""

    @staticmethod
    def read(fname=USERS_FILE):
        """read the users file. Returns a list of dictionaries.
        If the file does not exist, returns an empty list."""
        try:
            with open(fname, 'rt') as f:
                return json.load(f)
        except FileNotFoundError:
            return []

    @staticmethod
    def save(user, fname=USERS_FILE):
        """Saves the jsonifyable object to the specified file. 
        If the containing directory exists, create it. 
        Overwrites the file if exists."""

        # if the containing directory does not exist, create it
        if not os.path.exists(os.path.dirname(fname)):
            os.mkdir(os.path.dirname(fname))

        # Read the existing users, and then re-write (not sure about if / how you
        # can append to a json file easily?)
        # TODO: Improve
        users = Users.read()
        users.append(user)

        # save the data
        with open(fname, 'w') as f:
            json.dump(users, f)

import os
import json


class IO:
    """Provides  methods for reading and saving Json"""

    def __init__(self, fname):
        self.__fname = fname

    def read(self):
        """read the users file. Returns a list of dictionaries.
        If the file does not exist, returns an empty list."""
        try:
            with open(self.__fname, 'rt') as f:
                return json.load(f)
        except FileNotFoundError:
            return []

    def save(self, dic):
        """Saves the jsonifyable object to the specified file. 
        If the containing directory exists, create it. 
        Overwrites the file if exists."""

        # if the containing directory does not exist, create it
        if not os.path.exists(os.path.dirname(self.__fname)):
            os.mkdir(os.path.dirname(self.__fname))

        data = self.read()
        data.append(dic)

        # save the data
        with open(self.__fname, 'w') as f:
            json.dump(data, f)

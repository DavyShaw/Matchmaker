# MatchMaker Server

Provides a simple API for the MatchMaker android app.


### Services implimented so far:

## User
### Creating a user

Send a PUT request to `$SERVER_URL/user`

The body of the request should be JSON, and contain a "name" field:
```
{
    "name": "User Name"
}
```

If all goes well, the server will send a JSON response in the format:
```
    "msg": "OK",
    "user" : {
        "name" : "<user's name>",
        "id" : "<unique_user_id>"
    }
```
The name is the same as the value sent to the server. The id is a psudo-unique integer.


### Retrieving a user

Send a POST request to `$SERVER_URL/user`

The body of the request should be JSON, and contain an "id" field:
```
{
    "id": "<unique_user_id>"
}
```

If all goes well, the server will send a JSON response in the format:
```
{
    "msg": "OK",
    "user": {
        "name": "<user_name>",
        "id": "<unique_id>"
    }
}
```

If the user is not found, a response is sent with the status code `422` and body:
```
{
    "Error": "user_not_found"
}
```

## Event

### Creating an event

Send a PUT request to `$SERVER_URL/event`

The body of the request should be JSON, and contain at least the folloing fields:

**Note:** _Currently no validation on the correctness of these values, server only checks that they exist_

```
{
        "title": "<str>",
        "category": "<str>",
        "datetime": <linux_timestamp?>,
        "location": {
            "lat": "latitude",
            "lng": "longitude"
        },
        "organiser": "<user_id of organiser?>",
}
```

If all goes well, the server will send a JSON response in the format:
```
    "msg": "OK",
    "event" : {
        <... event details ...>
    }
```

Send a POST request to `$SERVER_URL/event`

The body of the request should be JSON, and contain an "id" field:
```
{
    "id": "<unique_event_id>"
}
```

If all goes well, the server will send a JSON response in the format:
```
{
    "msg": "OK",
    "event": {
        <... event details ...>
    }
}
```


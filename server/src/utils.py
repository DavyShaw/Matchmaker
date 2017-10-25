
def find(func, iterable):
    """
    func should be a function that returns a boolean.
    iterable should be something that can be iterated over.

    @returns an item from iterable, or None

    Can't believe python doesn't have this built in!
    """
    for item in iterable:
        if func(item):
            return item
    return None

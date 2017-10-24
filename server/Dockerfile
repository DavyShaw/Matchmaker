FROM tiangolo/uwsgi-nginx-flask:flask-python3.5
COPY ./src /api
COPY requirements.txt /api
WORKDIR /api
RUN pip3 install -r ./requirements.txt
CMD [ "python3", "server.py"]

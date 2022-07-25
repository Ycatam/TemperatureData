# Temperature Data Management


This API is a REST based API, that the main motivation is to provide data to IA analyze or predictions, for the agroindustry. This API is able request data from Stormglass API for historical purpose or let the user to store its own data based in a specific payload template, with we will discuss later in this README.

Some setup is required to test this API:

First of all, go to the Stormglass website and create a account to require a key.

#### https://stormglass.io

Be careful, because, only **ten** requests is allowed in a free plan per day!

After you have your personal key from the Stormglass, edit the application.properties file under main/resources and in the line stormglass.token after the = sign put your own key.

Next step is to run MySQL via Docker command:

#### docker container run -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=bootdb -p 3306:3306 -p 33060:33060 -d mysql:8

The application.properties file is already configured to this URI, if you want to change port address or something, confirm and alter in the application.properties too.

After that, run the application, and you are free to test. I'll show examples using postman.

This API have potential to offer many things, in this case, I've configured some endpoints that we'll discuss.

## Register city

To store information about a city, the city needs to be registered in DB first. To do that send a POST request to the URL:

#### http://localhost:8080/city

With the payload(this is just an example):

{
    "cityName":"sao paulo",
    "latitude": "35.8",
    "longitude": "-10.2"
  
}

To check wich city is already registered send a GET request to the URL:

#### http://localhost:8080/city

The status code of the return is 200(OK) and return the object like below(this is an example):

[
    {
        "id": 1,
        "cityName": "sao paulo",
        "latitude": 35.8,
        "longitude": -10.2
    },
    {
        "id": 2,
        "cityName": "campinas",
        "latitude": 22.0,
        "longitude": 47.0
    },
    {
        "id": 3,
        "cityName": "caieiras",
        "latitude": -23.36,
        "longitude": -46.74
    }
]

## Store a temperature data from a already registered city

To manually insert a temperature by a city name, date and temperature value send a POST request to the URL:

#### http://localhost:8080/temperatures

With the payload (this is an example):

{
    "cityName":"sao paulo",
    "temperatures":
    {
        "2019-02-03": "15.0",
        "2019-02-04": "15.0",
        "2019-02-05": "30.0",
        "2019-02-06": "8.0"

    }
  
}

The same object is returned as an answer and the status code 200(OK).

## Store temperature data from Stormglass

This method store historical temperature data since 2017-01-01 in the DB. I choose to store only the data at 12 O'clock, it can be improved. Because, Stormglass send a report hourly with the temperature, in a range of ten days.

To do that, don't forget to setup your personal key, then send a POST request to the URL:

#### http://localhost:8080/fromstormglass

With the payload (this is an example):

{
    "latitude": "-23.36",
    "longitude": "-46.74",
    "date": "2022-07-11"
}

For diferent dates just change the date value. Remember, the most acient register is 2017-01-01. This request will receive the payload from the Stormglass API and store the value from ten days after the start date that you send in the payload.

## List all registers

To list all the registered temperatures and their respective cities, send a GET request to the URL:

#### http://localhost:8080/temperatures

A payload like the example below with the status code 200(OK) will return:

[
    {
        "id": 1,
        "cityName": "sao paulo",
        "latitude": 35.8,
        "longitude": -10.2,
        "temperatures": {
            "2017-01-10": 16.42,
            "2017-01-09": 16.4,
            "2017-01-08": 16.26,
            "2017-01-07": 16.84,
            "2017-01-06": 17.56,
            "2017-01-05": 17.63,
            "2017-01-04": 18.02,
            "2017-01-03": 18.45,
            "2017-01-02": 18.25,
            "2017-01-01": 16.65
        }
    },
    {
        "id": 2,
        "cityName": "campinas",
        "latitude": 22.0,
        "longitude": 47.0,
        "temperatures": {
            "2017-01-10": 27.24,
            "2017-01-09": 26.07,
            "2017-01-08": 24.07,
            "2017-01-07": 21.63,
            "2017-01-06": 19.69,
            "2017-01-05": 21.18,
            "2017-01-04": 23.09,
            "2017-01-03": 26.25,
            "2017-01-02": 27.01,
            "2017-01-01": 27.66
        }
    },
    {
        "id": 3,
        "cityName": "caieiras",
        "latitude": -23.36,
        "longitude": -46.74,
        "temperatures": {
            "2022-07-15": 19.8,
            "2022-07-14": 16.49,
            "2022-07-13": 14.65,
            "2022-07-12": 20.71,
            "2022-07-11": 20.13,
            "2022-07-20": 16.85,
            "2022-07-19": 18.49,
            "2022-07-18": 15.46,
            "2022-07-17": 21.49,
            "2022-07-16": 20.64
        }
    }
]

## Return the highest temperature by a city name

To return the highest registered temperature, send a GET request to the URL:

#### http://localhost:8080/temperatures/high/sao paulo

A city name is always required, the link above is just an example.

## Return the lowest temperature by a city name

To return the lowest registered temperature, send a GET request to the URL:

#### http://localhost:8080/temperatures/low/sao paulo

A city name is always required, the link above is just an example.

## List all temperatures by a city name

To list all sotred temperatures by a given city, send a GET request to the URL:

#### http://localhost:8080/temperatures/sao paulo

A city name is always required, the link above is just an example.

## Delete all registers from a valid city name

To delete all temperatures and the city from DB, send a DELETE request to the URL:

#### http://localhost:8080/temperatures/campinas

A city name is always required, the link above is just an example.

## Swagger documentation

A swagger doccumentation can be accessed through:

#### http://localhost:8080/swagger-ui/

From there you can see all endpoints.

### Know Issues

If you register a city with a space between the name, and register again without the space, it will create a new register, or return a error if you try to register a temperature. Example:

sao paulo or saopaulo.

To avoid errors to all the requirements with the same city name. If you registered it with space, do everything with the space, or vice and versa.

Some errors that came from the Stormglass isn't mapped in this API. Due to deadline and the limited requests, the errors need to be handled latter. All the error stack will be printed in the console containing the information from the Stormglass API.

Hope you enjoy testing and playing with this API. Any sugestions are welcome!
Thanks in advance!
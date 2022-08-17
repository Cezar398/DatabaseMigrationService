![App Screenshot](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)

![App Screenshot](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)

![App Screenshot](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)

# Movie Migration Service

Movie service offer multiple endpoints for manipulate movies, export database to csv and migrate movies from IMDB.

## API Reference

### Movie Endpoints

#### Get all movies items

```http
  GET /api/v1/movie
```

| Parameter     | Type      | Description                                                           |
|:--------------|:----------|:----------------------------------------------------------------------|
| `title`       | `string`  | Search for title match                                                |
| `voteCount`   | `integer` | Search for movies with vote count greater or equal with voteCount     |
| `voteAverage` | `float`   | Search for movies with vote average greater or equal with voteAverage |
| `overview`    | `string`  | Search for movies with contains overview                              |

#### Get movie by id

```http
  GET /api/v1/movie/{id}
```

| Parameter     | Type      | Description                                                           |
|:--------------|:----------|:----------------------------------------------------------------------|
| `id`          | `integer` | Movie id                                                              |

#### Export movie database

```http
  GET /api/v1/movie/export
```

#### Export movie database

```http
  POST /api/v1/movie
```

| Parameter     | Type      | Description               |
|:--------------|:----------|:--------------------------|
| `title`       | `string`  | Movie title               |
| `overview`    | `string`  | Movie overview            |
| `posterPath`  | `string`  | Movie poster path         |
| `mediaType`   | `string`  | Movie media type          |
| `popularity`  | `string`  | Movie popularity          |
| `releaseDate` | `string`  | Movie release date        |
| `video`       | `bool`    | Movie video true or false |
| `voteAverage` | `float`   | Movie vote average        |
| `voteCount`   | `integer` | Movie vote count          |

#### Start Migration

```http
  POST /api/v1/movie/migrate
```

#### Delete movies

```http
  DELETE /api/v1/movie
```

#### Delete movies by id

```http
  DELETE /api/v1/movie/{id}
```

| Parameter     | Type      | Description               |
|:--------------|:----------|:--------------------------|
| `id`          | `integer` | Movie id                  |

#### Update movie by id

```http
  PUT /api/v1/movie/{id}
```

| Parameter     | Type      | Description               |
|:--------------|:----------|:--------------------------|
| `id`          | `integer` | Movie id                  |
| `title`       | `string`  | Movie title               |
| `overview`    | `string`  | Movie overview            |
| `posterPath`  | `string`  | Movie poster path         |
| `mediaType`   | `string`  | Movie media type          |
| `popularity`  | `string`  | Movie popularity          |
| `releaseDate` | `string`  | Movie release date        |
| `video`       | `bool`    | Movie video true or false |
| `voteAverage` | `float`   | Movie vote average        |
| `voteCount`   | `integer` | Movie vote count          |

## Environment Variables

To run this project, you will need to add the following environment variables to your application.yaml file

| Parameter        | Description                                            |
|:-----------------|:-------------------------------------------------------|
| `${DB_HOST}`     | Your database host(`example: localhost`                |
| `${DB_USERNAME}` | Your database user(`example: root`                     |
| `${DB_PASSWORD}` | Your database password(`example: root`                 |
| `${BASE_URL}`    | Your application base url(`example: http://localhost`) |
| `${IMDB_TOKEN}`        | Your imdb token(`STRING_TOKEN`)                        |
| `${IMDB_USERNAME}`    | Your imdb user(`example: user`)                        |
| `${IMDB_PASSWORD}`    | Your imdb password(`example: password`)                |     

## New Features

* POST Import movies endpoint - new endpoint that will allow importing movies through csv file
* GET movies with pagination and order by a specific field - new endpoint for movies
* Movie Crew - CRUD endpoints:
    * First Name
    * Last Name
    * Birth Date
    * Contracts:
        * Start Date - the start date of the contract
        * End Date - the end date of the contract
        * Movie
* GET crew that was hired in a specific period
* GET crew that was in a specific country
* Schedule movies migration on a configured time
* Keep tracking of the triggered migrations - persist the time when a migration was started, duration, how triggered the migration
* Secure endpoints - secure all endpoints except GET endpoints that can be accessed by anyone



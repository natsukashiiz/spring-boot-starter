## Spring Boot Starter API
- [Default Response](#default-response)
- [Response code](#response-code)
- [Pagination](#pagination)
- [Authentication](#authentication)
- [API](#api)
    - [Auth](#auth)
      - [Sign in](#sign-in)
      - [Sign up](#sign-up)
      - [Refresh Token](#refresh-token)
    - [Users](#users)
      - [Personal Information](#personal-information)
      - [Update](#update)
      - [Singed History](#singed-history)

> java version 1.8
> 
> spring-boot version 2.7.12

## Default Response
```json
{
  "code": 0,
  "text": "SUCCESS",
  "result": {},
  "pagination": {}
}
 ```
| index      | type         | description                             |
|------------|--------------|-----------------------------------------|
| code       | number       | code of [Response code](#response-code) |
| message    | string       | text of [Response code](#response-code) |
| result     | json / array | result                                  |
| pagination | json         | [Pagination](#pagination)               |

### Response Code
| code | text                      | description                  |
|--|---------------------------|------------------------------|
| 0 | SUCCESS                   | success                      |
| 210 | INVALID_REQUEST           | invalid request              |
| 211 | INVALID_EMAIL             | INVALID_EMAIL                | invalid email                |
| 212 | INVALID_USERNAME          | invalid username             |
| 213 | INVALID_PASSWORD          | invalid password             |
| 214 | INVALID_NEW_PASSWORD      | invalid new password         |
| 215 | INVALID_CODE     | invalid code         |
| 216 | INVALID_UID     | invalid user id         |
| 217 | INVALID_USERNAME_PASSWORD | invalid username or password |
| 218 | PASSWORD_NOT_MATCH        | password not match        |
| 310 | EXISTED_EMAIL             | existed email             |
| 311 | EXISTED_USERNAME          | existed username          |
| 410 | NOT_FOUND                 | not found                 |
| 411 | NO_DATA                   | no data                   |
| 510 | TOKEN_EXPIRE              | token expire              |
| 511 | REFRESH_TOKEN_EXPIRE      | refresh token expire      |
| 888 | UNAUTHORIZED              | unauthorized              |
| 999 | UNKNOWN                   | unknown                   |

### Pagination
- request
```http request
GET /v1/example?page=1&limit=11&sortBy=id&sortType=desc
```
| index | type     | default | required | description   |
|-------|----------|----------|----------|---------------|
|page| number   | 1      | false    | page           |
|limit| number   | 10     | false    | limit         |
|sortBy| number   | id     | false    | index of data |
|sortType| number  | asc   | false         | asc / desc |
- response
```json
{
  "code": 0,
  "text": "SUCCESS",
  "result": [],
  "pagination": {
    "current": 1,
    "limit": 10,
    "records": 0,
    "pages": 0,
    "first": true,
    "last": true
  }
}
```
| index | type    | description      |
|-------|---------|------------------|
|current| number  | current page number |
|limit| number  | limit data       |
|records| number  | count data       |
|pages| number  | total page       |
|first| boolean | -                |
|last| boolean | -                |

### Authentication
```http request
GET /v1/users
Authorization: Bearer <token>
```

***
# API

## Auth
### Sign Up
- request
```http request
POST /v1/auth/signup
Content-Type: application/json

{
  "email": "xxxx",
  "username": "xxxx",
  "password": "xxxx",
}
```
| index | type   | required | description |
|-------|--------|---|-------------|
|email| string | true     | -           |
|username| string | true     | -           |
|password| string | true     | -           |
- response
```json
{
  "code": 0,
  "text": "SUCCESS",
  "result": {
    "id": 1,
    "email": "xxxx",
    "username": "xxxx"
  }
}
``` 

## Sign In
- request
```http request
POST /v1/auth/signin
Content-Type: application/json

{
  "username": "xxxx",
  "password": "xxxxxxxx"
}
```
| index | type   | required | description |
|-------|--------|----------|-------------|
|username| string | true     | -           |
|password| string | true     | -           |
- response
```json
{
  "code": 0,
  "text": "SUCCESS",
  "result": {
    "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJuYXRzdWthcfdf2hpaXoiLCJzdWIiOiJ2djk5OSIsImlhdCI6MTY4NjIxODQxMSwiZXhwIjoxNjg2MzA0ODExfQ.n8fJp6_EUltl2GF6Six4Er1KonCIDVBcx33xUdJAIxE",
    "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJkZDVhZDhjYS04NjE5LTRjNTAtOTQdasfs4Yi0zNDNiYjEyNDdiYzQiLCJ1aWQiOjUsInVzZXJuYW1lIjoidnY5OTkiLCJlbWFpbCI6InZ2OTk5QGdtYWlsLmNvbSIsImlzcyI6Im5hdHN1a2FzaGlpeiIsImlhdCI6MTY4NjIxODQxMSwiZXhwIjoxNjg2MjIyMDExfQ.McaPFxSeRTnqo3G8KrCTmUwIfaOUGonV7i543_LdtTs",
    "refreshExpire": 1686304811568,
    "accessExpire": 1686222011541
  }
}
```
### Refresh Token
- request
```http request
POST /v1/auth/refresh
Content-Type: application/json

{
  "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJuYXRzdWthc2hpaXoiLsdafasdfCJzdWIiOiJ2djk5OSIsImlhdCI6MTY4NjIyMDEzMywiZXhwIjoxNjg2MzA2NTMzfQ.C4IQ48D8nCAx0hwvAt7zjqWAgs90P7OlcT_9kpcIWjY"
}
```
| index | type   | required | description                  |
|-------|--------|----------|------------------------------|
|refreshToken| string | true     | Get from [Sign in](#sign-in) |
- response
```json
{
  "code": 0,
  "result": {
    "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOigfdgJuYXRzdWthc2hpaXoiLCJzdWIiOiJ2djk5OSIsImlhdCI6MTY4NjIxODQxMSwiZXhwIjoxNjg2MzA0ODExfQ.n8fJp6_EUltl2GF6Six4Er1KonCIDVBcx33xUdJAIxE",
    "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJkZDVhZDhjYS04NjE5LTRfdsfsdjNTAtOTQ4Yi0zNDNiYjEyNDdiYzQiLCJ1aWQiOjUsInVzZXJuYW1lIjoidnY5OTkiLCJlbWFpbCI6InZ2OTk5QGdtYWlsLmNvbSIsImlzcyI6Im5hdHN1a2FzaGlpeiIsImlhdCI6MTY4NjIxODQxMSwiZXhwIjoxNjg2MjIyMDExfQ.McaPFxSeRTnqo3G8KrCTmUwIfaOUGonV7i543_LdtTs",
    "refreshExpire": 1686304811568,
    "accessExpire": 1686222011541
  }
}
```

## Users
### Personal Information
```http request
GET /v1/users
Authorization: Bearer <token>
```
- response
```json
{
  "code": 0,
  "text": "SUCCESS",
  "result": {
    "id": 1,
    "email": "xxxx",
    "username": "xxxx"
  }
}
```

### Update
- request
```http request
PATCH http://localhost:8080/v1/users
Authorization: Bearer <token>
Content-Type: application/json

{
  "email": "xxxx"
}
```
| index | type   | required | description |
|-------|--------|----------|-------------|
|email| string | true     | -           |
- response
```json
{
  "code": 0,
  "text": "SUCCESS",
  "result": {
    "id": 1,
    "email": "xxxx",
    "username": "xxxx"
  }
}
```


### Singed History
- request
```http request
GET /v1/users/signedHistory
Authorization: Bearer <token>
```
- response
```json
{
  "code": 0,
  "text": "SUCCESS",
  "result": [
    {
      "id": 1,
      "cdt": "2023-06-08T15:04:20.992",
      "udt": "2023-06-08T15:04:20.992",
      "ipv4": "192.168.1.1",
      "device": "iPhone"
    }
  ],
  "pagination": {
    "current": 1,
    "limit": 10,
    "records": 1,
    "pages": 1,
    "first": true,
    "last": true
  }
}
```
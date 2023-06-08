## Spring Boot Starter API
- [Authentication](#authentication)
***
> java version 1.8
> 
> spring-boot version 2.7.12

- response
```json
{
  "code": 0,
  "result": null,
  "pagination": null
}
 ```
| index | type           | description       |
|-------|----------------|-------------------|
|code| number         | response code     |
|result| jsonObject / array | reponse data      |
|pagination| jsonObject     | pagination detail |

### Response code
| code | description                  |
|------|------------------------------|
| 0    | success                      |
| 4010 | invalid request              |
| 4011 | invalid email                |
| 4012 | invalid username             |
| 4013 | invalid password             |
| 4014 | invalid new password         |
| 4021 | invalid username or password |
| 4022 | password not match           |
| 4031 | existed email                |
| 4032 | existed username             |
| 4040 | not found                    |
| 4041 | no data                      |
| 4070 | token expire                 |
| 4071 | refresh token expire         |
| 4100 | unauthorized                 |
| 9999 | unknown                      |

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
### sign up
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
| index | type   | default | required | description |
|-------|--------|------|----------|-------------|
|email| string |    | true     |        |
|username| string |    | true     |     |
|password| string |    | true     |     |
- response
```json
{
  "code": 0,
  "result": {
    "id": 1,
    "email": "xxx@email.com",
    "username": "xxxx"
  },
  "pagination": null
}
``` 

## sign in
- request
```http request
POST /v1/auth/signin
Content-Type: application/json

{
  "username": "xxxx",
  "password": "12345678"
}
```
| index | type   | default | required | description |
|-------|--------|------|----------|-------------|
|username| string |    | true     |     |
|password| string |    | true     |     |
- response
```json
{
  "code": 0,
  "result": {
    "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJuYXRzdWthc2hpaXoiLCJzdWIiOiJ2djk5OSIsImlhdCI6MTY4NjIxODQxMSwiZXhwIjoxNjg2MzA0ODExfQ.n8fJp6_EUltl2GF6Six4Er1KonCIDVBcx33xUdJAIxE",
    "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJkZDVhZDhjYS04NjE5LTRjNTAtOTQ4Yi0zNDNiYjEyNDdiYzQiLCJ1aWQiOjUsInVzZXJuYW1lIjoidnY5OTkiLCJlbWFpbCI6InZ2OTk5QGdtYWlsLmNvbSIsImlzcyI6Im5hdHN1a2FzaGlpeiIsImlhdCI6MTY4NjIxODQxMSwiZXhwIjoxNjg2MjIyMDExfQ.McaPFxSeRTnqo3G8KrCTmUwIfaOUGonV7i543_LdtTs",
    "refreshExpire": 1686304811568,
    "accessExpire": 1686222011541
  },
  "pagination": null
}
```

## Users
### singed history
```http request
GET /v1/users/signedHistory
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJkZDVhZDhjYS04NjE5LTRjNTAtOTQ4Yi0zNDNiYjEyNDdiYzQiLCJ1aWQiOjUsInVzZXJuYW1lIjoidnY5OTkiLCJlbWFpbCI6InZ2OTk5QGdtYWlsLmNvbSIsImlzcyI6Im5hdHN1a2FzaGlpeiIsImlhdCI6MTY4NjIxODQxMSwiZXhwIjoxNjg2MjIyMDExfQ.McaPFxSeRTnqo3G8KrCTmUwIfaOUGonV7i543_LdtTs
```
- response
```json
{
  "code": 0,
  "result": [
    {
      "id": 14,
      "state": false,
      "cdt": "2023-06-08T15:04:20.992",
      "udt": "2023-06-08T15:04:20.992",
      "uid": 5,
      "ipv4": "192.168.1.88",
      "userAgent": "Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1",
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
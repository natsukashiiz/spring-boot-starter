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
```json
{
  "current": 1,
  "limit": 10,
  "records": 0,
  "pages": 0,
  "first": true,
  "last": true
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
```text
Authorization: Bearer <token>
```
- example for use fetch javascript
```js
fetch('/exaple', {
  headers: {
      Authentication: 'Bearer <token>'
  }
})
```

***
# API

## Auth
### sign up
- request
```text
POST /v1/auth/signup
Content-Type: application/json

{
  "email": "xxxx",
  "username": "xxxx",
  "password": "xxxx",
}
```
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
POST http://localhost:8080/v1/auth/signin
Content-Type: application/json

{
  "username": "xxxx",
  "password": "12345678"
}
```
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
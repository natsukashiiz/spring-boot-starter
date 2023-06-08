## Spring Boot Starter API
> java version 1.8
> 
> spring-boot version 2.7.12
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

- response
```json
{
  "code":"xxxx",
  "result":"xxxx",
  "pagination":{
    "current":"xxxx",
    "limit":"xxxx",
    "records":"xxxx",
    "pages":"xxxx",
    "first":"xxxx",
    "last":"xxxx"
  }
}
 ```
| index | type | description       |
|-------|------|-------------------|
|code|number| response code     |
|result|object| reponse data      |
|pagination|pagination| pagination detail |

- pagination

| index | type    | description         |
|-------|---------|---------------------|
|current| number  | current page number |
|limit| number  | limit data          |
|records| number  | count of data       |
|pages| number  | total page          |
|first| boolean  | -                   |
|lastlast| boolean | -                   |
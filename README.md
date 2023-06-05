## Spring Boot Starter API
> java version 1.8
> 
> spring-boot version 2.7.12
### Response code
| code | description |
|--|--|
| 0 | success |
| 4010 | invalid request|
| 4021 | invalid username or password |
| 4040 | not found|
| 4070 | token expire|
| 4100 | unauthorized|
| 9999 | unknown|

### Response example
```json
 {
   "code": xxxx,
   "result": xxxx,
   "records": xxxx
 }
 ```
 | index |type | description |
|--|--|--|
|code|number|response code|
|result|object|reponse data|
|records|number|data count|

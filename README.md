# evaluacion-java

#### Implemetacion de JPA con H2

Se pueden consumir los siguientes endpoint

- [x] POST localhost:8080/api/user

Ex:

```
{
    "name":"tohno",
    "email":"tohno@tohno.com",
    "password":"tohno",
    "phones":[
        {
          "number":"93939393",
          "cityCode":"VIN",
          "countryCode":"CLP"
        },
                {
          "number":"999999",
          "cityCode":"VALPO",
          "countryCode":"CLP"
        }
    ]
}
```

- [x] GET localhost:8080/api/user
- [x] GET localhost:8080/api/user/{id}
- [x] DELETE localhost:8080/api/user/{id}

Para acceder a la consola de H2 ingresar a [link consola H2](http://localhost:8080/h2-console)

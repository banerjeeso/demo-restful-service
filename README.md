# Demo-POC-restful-service
demo restful application

To run the application
mvn clean install

mvn spring-boot:run

To run test cases
mvn clean verify -Pcoverage-ut     # This would run unit tests

curls

Access Swagger UI for Rest API documents information
curl -X GET http://localhost:8080/demo/swagger-ui.html#/

curl -X GET http://localhost:8080/demo/helloWorld

curl -X POST http://localhost:8080/demo/findUniqueWords -H 'Content-Type: application/json' -d '{"paragraphText": "This is a test to find words\nthis words would be paragraph\n test this words frecuency\n"}'

curl -X POST localhost:8080/demo/findFibonacci -H 'Content-Type: application/json' -d '{"nTH": 5 }'

curl -X GET http://localhost:8080/demo/accessExternalCall -H 'Content-Type: application/json'

curl -X GET http://localhost:8080/demo/create/deadlock -H 'Content-Type: application/json' 

curl -X GET http://localhost:8080/demo/check/deadlock -H 'Content-Type: application/json' 

curl -X GET http://localhost:8080/demo/findAllProduct -H 'Content-Type: application/json' 

curl -X GET http://localhost:8080/demo/findProduct/1 -H 'Content-Type: application/json' 

curl -X GET http://localhost:8080/demo/queryProduct/Sony/5 -H 'Content-Type: application/json'

curl -X DELETE http://localhost:8080/demo/removeProduct/2 -H 'Content-Type: application/json'

curl -X POST localhost:8080/demo//addProduct -H 'Content-Type: application/json' -d '{
        "id": 8,
        "productname": "Sony",
        "productype": "music",
        "quantity": 40,
        "promotion": 5
 }'


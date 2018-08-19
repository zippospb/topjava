**MealRestController curl commands:**
curl -i localhost:8080/topjava/rest/meals
curl -i localhost:8080/topjava/rest/meals/filter?startDate=2015-05-31&startTime=11:00:00&endDate=2019-05-31&endTime=23:59:59
curl -i localhost:8080/topjava/rest/meals/100002
curl -i -X POST -H "Content-Type: application/json" -d '{"dateTime":"2015-06-01T18:00","description":"new meal","calories":"300"}' localhost:8080/topjava/rest/meals/
curl -i -X PUT -H "Content-Type: application/json" -d '{"id":"100002","dateTime":"2015-05-30T10:00","description":"test meal","calories":"500"}' localhost:8080/topjava/rest/meals/100002
curl -i -X DELETE localhost:8080/topjava/rest/meals/100002

**ProfileRestController curl commands:**
curl -i http://localhost:8080/topjava/rest/profile
curl -i -X PUT -H "Content-Type: application/json" -d '{"name": "New777","email": "new777@yandex.ru","password": "passwordNew","roles": ["ROLE_USER"]}' http://localhost:8080/topjava/rest/profile
curl -i -X DELETE http://localhost:8080/topjava/rest/profile

**AdminRestController curl commands:**
curl -i http://localhost:8080/topjava/rest/admin/users
curl -i http://localhost:8080/topjava/rest/admin/users/100001
curl -i -X POST -H "Content-Type: application/json" -d '{"name": "New2","email": "new2@yandex.ru","password": "passwordNew","roles": ["ROLE_USER"]}'  http://localhost:8080/topjava/rest/admin/users
curl -i -X PUT -H "Content-Type: application/json" -d '{"name": "UserUpdated","email": "user@yandex.ru","password": "passwordNew","roles": ["ROLE_USER"]}'  http://localhost:8080/topjava/rest/admin/users/100000
curl -i -X DELETE http://localhost:8080/topjava/rest/admin/users/100000
curl -i http://localhost:8080/topjava/rest/admin/users/by?email=admin@gmail.com


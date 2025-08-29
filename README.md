BFHL REST API (Java Spring Boot)

A simple REST API that accepts an array of values and returns:

is_success

user_id

email

roll_number

odd_numbers

even_numbers

alphabets (converted to uppercase; supports multi-letter tokens)

special_characters

sum (sum of numeric tokens, as a string)

concat_string (all alphabetical characters from input, reversed, alternating caps starting Upper)

This project follows the problem statement’s exact logic and formatting requirements.

Features

Endpoint: POST /bfhl, returns HTTP 200 for successful requests

user_id format: full_name_ddmmyyyy (full name lowercased)

Numbers returned as strings

sum returned as a string

Alphabets list contains only alphabetic tokens (A–Z), uppercased

Special characters list contains tokens that are neither pure letters nor pure integers

concat_string: all alphabetical characters from the entire input (including inside mixed tokens), reversed, alternating caps starting with uppercase

Global exception handling with is_success=false and user_id always present on errors

CORS enabled for all origins

Configurable identity via environment variables

Tech Stack

Java 17

Spring Boot 3

Maven

Requirements
# Install JDK 17+
sudo apt update && sudo apt install openjdk-17-jdk -y
java -version

# Install Maven 3.9+
sudo apt install maven -y
mvn -v

# (Optional) Install Docker
sudo apt install docker.io -y
docker -v

Project Structure
├─ pom.xml
├─ Dockerfile
├─ Procfile
├─ render.yaml (or render.yml)
├─ src
│  ├─ main
│  │  ├─ java/com/bfhl
│  │  │  ├─ BfhlApplication.java
│  │  │  ├─ config
│  │  │  │  ├─ AppProperties.java
│  │  │  │  └─ WebConfig.java
│  │  │  ├─ controller/BfhlController.java
│  │  │  ├─ dto
│  │  │  │  ├─ BfhlRequest.java
│  │  │  │  └─ BfhlResponse.java
│  │  │  ├─ exception
│  │  │  │  ├─ GlobalExceptionHandler.java
│  │  │  │  └─ InvalidRequestException.java
│  │  │  └─ service/BfhlService.java
│  │  └─ resources/application.yml
│  └─ test/java/com/bfhl/BfhlApplicationTests.java


##testing

<img width="2360" height="1767" alt="Screenshot 2025-08-29 120256" src="https://github.com/user-attachments/assets/36e67449-80a5-43b4-afe7-d7bf6c180e6c" />
<img width="2330" height="1787" alt="Screenshot 2025-08-29 115810" src="https://github.com/user-attachments/assets/4ab40d3b-365e-42b5-b3d0-1aa23e73e9c6" />
<img width="2328" height="1765" alt="Screenshot 2025-08-29 120137" src="https://github.com/user-attachments/assets/f0b4fd82-e3cf-4706-80e2-45778e447f4c" />


Configuration

Set environment variables (defaults provided):

export APP_FULL_NAME=john_doe
export APP_DOB_DDMMYYYY=17091999
export APP_EMAIL=john@xyz.com
export APP_ROLL_NUMBER=ABCD123


These map to:

user_id: <full_name_lowercased>_<ddmmyyyy>

email: provided email

roll_number: provided roll number

You can also edit src/main/resources/application.yml directly.

Run Locally
Option A: Maven
# Build and package
mvn clean package

# Run Spring Boot app
mvn spring-boot:run


App runs at: http://localhost:8080

Option B: Run main class

Run BfhlApplication from your IDE.

Test the API
Endpoint
POST http://localhost:8080/bfhl
Headers: Content-Type: application/json

Sample request
{
  "data": ["a","1","334","4","R","$"]
}

Sample response
{
  "is_success": true,
  "user_id": "john_doe_17091999",
  "email": "john@xyz.com",
  "roll_number": "ABCD123",
  "odd_numbers": ["1"],
  "even_numbers": ["334","4"],
  "alphabets": ["A","R"],
  "special_characters": ["$"],
  "sum": "339",
  "concat_string": "Ra"
}

Curl

Linux/macOS:

curl -X POST http://localhost:8080/bfhl \
  -H 'Content-Type: application/json' \
  -d '{"data":["a","1","334","4","R","$"]}'


Windows PowerShell:

curl.exe -X POST http://localhost:8080/bfhl -H "Content-Type: application/json" -d '{ "data": ["a","1","334","4","R","$"] }'


Windows CMD:

curl -X POST http://localhost:8080/bfhl -H "Content-Type: application/json" -d "{\"data\":[\"a\",\"1\",\"334\",\"4\",\"R\",\"$\"]}"

Docker
Build
mvn clean package
docker build -t bfhl-api:latest .

Run
docker run -p 8080:8080 \
  -e APP_FULL_NAME=john_doe \
  -e APP_DOB_DDMMYYYY=17091999 \
  -e APP_EMAIL=john@xyz.com \
  -e APP_ROLL_NUMBER=ABCD123 \
  bfhl-api:latest

Test
curl -X POST http://localhost:8080/bfhl \
  -H "Content-Type: application/json" \
  -d '{"data":["A","ABcD","DOE"]}'

Deploy
Render (recommended)
# Push repo to GitHub
git init
git remote add origin https://github.com/<your-username>/bfhl-api.git
git add .
git commit -m "Initial commit"
git push origin main


Go to Render → Create Web Service → Connect repo

Render auto-detects render.yaml and Dockerfile

Public URL: https://your-app.onrender.com/bfhl

Heroku (container stack)
heroku create
heroku stack:set container
git push heroku main

Railway

Create new project → Deploy GitHub repo (Dockerfile auto-detected).

Set env vars if needed.

Error Handling

Bad request (400):

{
  "is_success": false,
  "user_id": "john_doe_17091999",
  "message": "error description"
}


Server error (500): same structure.

Development
Run tests

mvn test

Skip tests (if needed)
mvn clean package -DskipTests

Common Maven goals
mvn spring-boot:run
mvn clean package


License

Add your preferred license here (e.g., MIT).

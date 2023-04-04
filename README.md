# Car Reservation API

An API for managing vehicle reservations/ hires.


## Deployment

### Prerequisites
    - Java 11
    - Maven
    - Postgresql
    - Flyway
    - Docker (optional)
    - Docker-compose (optional)


### Development/local
- Create a `car_reservation_dev` database on postgres.
- Clone the repository `git clone https://github.com/vinkims/car_reservation_api.git`.
- cd into the repository.
- Fetch and checkout the `develop` branch: `git fetch origin develop` & `git checkout develop`.
- Once in the root directory, you can either startup the API directly or build it first.
#### Direct
- Update the `database` and `flyway` credentials as stated in the `src/main/resources/application.properties` do they match your local settings.
- Run the cmd: `mvn spring-boot:run` to start up the API.
- Once the API is running, you can access the logs in the `logs` directory.
#### Java build
- Create a copy of the `src/main/resources/application.properties` file and in it set the database and flyway credentials to match your local ones.
- Build the project using the cmd: `mvn clean package`.
- Once the build is completed, run the resulting jar file as stored in the `target` directory; use the command: `java -jar $jar_path --spring.config.location=$prop_file` where `prop_file` is the path to the application.properties copy you made earlier.

#### Docker build
- Build the project using the cmd: `mvn clean package -DskipTests` where -DskipTests tells maven to skip tsting the application state.
- Once the build is completed, the resulting jar file is stored in the `target` directory.
- Update the `Dockerfile` with the name of your jar file.
- Build the Docker image. In the project directory run `docker compose build`
- To run the multi-container application run `docker compose up - d` where -d runs the containers in detached mode.
- To view the logs run `docker compose logs <container_name>`
- To view the containers run `docker ps`

The API documentation can be accessed at `http://localhost:5030/api/v1/swagger-ui/index.html`

## Usage

### Requests
All requests/endpoints require an authentication token to be passed in the header except for:
    - "/user/auth/login".

Update the default credentials at the `src/main/resources/application.properties` file.

### Pagination
For the paginated endpoints, the following can be specified as URL query parameters

    - pgNum :  The page number to be returned `[default: 0]`
    - pgSize : Number of elements per page `[default: 10]`
    - sortValue : The column/field to be used for sorting/ordering results `[default: createdOn]`
    - sortDirection : Order of results to be returned; can either be *asc* or *desc* `[default: desc]`

    Example:
    `/user?pgNum=2&pgSize=20`

### Filtering/Searching
To filter a list based on resource fields/columns, the below can be used as URL query parameters.
The query parameter should be in the format: `keyOperatorValue`. These can be chained via commas. Operator can either be:

    - EQ - equals
    - GT - greater than or equal to
    - LT - less than or equal to
    - NEQ - not equal to

*Users*

    - id
    - firstName
    - middleName
    - lastName
    - createdOn
    - age
    - lastActiveOn
    - modifiedOn
    - role.id : id of the role
    - status.id : id of the status
    - civilIdentities.civilIdentityValue : value of the civil identity
Example: `/user?q=firstNameEQJohn` -> returns all users with the specified first name

*Vehicles*

    - id
    - bookingAmount
    - createdOn
    - engineCapacity
    - lastActiveOn
    - modifiedOn
    - registrationNumber
    - fuelType.id : id of the fuel type
    - transmissiontype.id : id of the transmission type
    - model.vehicleMake : make of the vehicle
    - model.vehicleModel : model of the vehicle
    - status.id : id of the status
Example: `/vehicle?q=model.vehicleMakeEQSubaru,createdOnGT2022-01-01` -> returns all vehicles with the specified make added after 1st Jan 2022

*Reservations*

    - id
    - createdOn
    - modifiedOn
    - dropoffDate
    - pickupDate
    - dropoffLocation.id : id of the dropoff location
    - pickupLocation.id
    - status.id
    - user.id : id of the user making the reservation
    - vehicle.id : id of the vehicle being reserved
    - vehicle.registrationNumber : registration number of the vehicle being reserved
Example: `/reservation?q=dropoffDateEQ2023-04-01,pickupDateEQ2023-03-01,vehicle.idEQ3` -> returns all reservations of the vehicle with the specified id and which has been reserved from 1st March 2023 to 1st April 2023

*Vehicle reviews* 

    - id
    - createdOn
    - modifiedOn
    - review
    - comment
    - user.id
    - vehicle.id
Example `/vehicle/review?q=reviewEQ2` -> returns all vehicle reviews with 2 stars

*Payments*

    - id
    - amount
    - description
    - externalTransactionId
    - modifiedOn
    - createdOn
    - reference
    - paymentChannel.id
    - transactionType.id
    - status.id
    - user.id
Example: `/payment?q=user.idEQ4,transactionType.idEQ2` -> returns all payments made by the user with the specified id and of the transaction type with the specified id
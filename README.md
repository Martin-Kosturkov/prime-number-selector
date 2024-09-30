## Prime number selector
### Project overview
The project consists of 3 modules:
- `producer` - Generating random numbers in a given range
- `consumer` - Consuming all generated numbers and writing the prime ones in a file
- `common` - Hold utility stuff used by the producer and the consumer

The **numbers** are the domain of the project so both modules consist of a **number** package
where most of the important logic is located.

### Technologies
- Java
- Micronaut
- Kafka
- Redis
- Docker

### Key features
- Both: producer and consumer create required files on startup if they don't exist
- The producer has scheduled task to generate and send numbers
- Numbers are send to Kafka
- On startup, the consumer runs a background job that fills an in-memory cache with all the prime numbers (up to `Long.MAX_VALUE`, check `PrimeNumbersInMemoryCache`).
Note, that the project keep track of the current highest number checked for being prime, and if a bigger number is consumed
it will be processed without using the cache.
- Redis is used for distributed locking to avoid possible issues of multiple threads trying to create the same file or writing to the same file simultaneously.

### Run project
There is a docker compose that starts everything, including 2 producer and 2 consumer containers. Below are the steps to run it.

- Open terminal and navigate to the project directory.
- run `mvnw clean install` (this should generate runnable jar files. **Note that it might require java home to point to java 21**)
- When the build is ready, execute: `docker compose up -d` to start the services on the background.
- The `NUMBER_GENERATOR_MAX_NUMBER` env variable can be anything between 3 and 9223372036854775807 (Long.MAX_VALUE) but is set to 1000 by default to produce more readable and easier to check numbers.

The first time you run docker compose, it will take some time for the images to be downloaded. When everything starts successfully,
A folder called `files` will be created in the project base directory where the generated and prime numbers will be written.
In addition, you can open http://localhost:8090/ui/clusters/local/all-topics to see some kafka related data.



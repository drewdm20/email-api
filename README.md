# email-api
This API sends emails using the sendinblue API library. It also uses thymeleaf to create a custom email template using HTML.

# How to run with doppler
1. Clone this repo to your local machine
2. Ensure required maven dependencies are correctly installed
3. Sign up for doppler
4. Install the doppler cli using the following commands on mac or windows
   - brew install gnupg (mac)
   - brew install dopplerhq/cli/doppler (mac)
   - winget install doppler (windows)
5. cd to the project directory or open project in IDE and run the command in the terminal doppler login
6. authenticate in the web browser and then run doppler setup
7. select the project as well as the environment to fetch the env secrets
8. change the run configuration to run the shell script depending on which system you use i.e. Mac or Windows
9. Test the API using postman or a client of your choice

# How to run without doppler
1. Clone this repo on your local machine
2. Go to run configurations in your IDE and configure the environment variables set in the application.yml file
3. Run the application
4. Test using a client of your choice

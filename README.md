# project1 -- ERS (Employee Reimbursement System)

## Project Description

The ERD program is meant to mock a portion of an actual ERD that may be present in a company. Employees and managers both use the system to interact with 
reimbursement requests. On the employee side, one can log in and view the status on their submitted requests. The status are: pending, denied, or approved. An employee 
can submit new reimbursements. On the manager side, the log-in process is similar. However, managers can approve and deny pending requests, as well as edit the already approved/denied
reimbursements.

## Technologies Used

* Javalin (Servlets run in the background)
* Postgres (Amazon Web Services for hosting, DBeaver for viewing and SQL setup)
* Docker
* JUnit (testing)
* Log4j (logging)

## Features

List of features
* Employees & managers can sign in
* Employees can view their reimbursement requests
* Employees can add new reimbursement requests
* Managers can view all reimbursement requests from all employees or filter by pending, denied, approved, or by user
* Managers can approve/deny and edit the status of all requests
* Managers can create new employees

## Getting Started
   
type `git clone https://github.com/kacperdudz/project1.git` to clone this repo

## Usage

- A database connection needs to be established. Proper credentials must be provided in the `project1\src\main\java\tools\Repo.java` file.

## Contributors

Kacper Dudzinski

## License

This project does not use a license

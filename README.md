# seniorBankingRepo

This project will handle transactions such as deposit and withdrawal for different client’s bank accounts. This will be written in Java and will contain connection to a MySQL database using JDBC. There will be login functionality, and the user can reach either the login success or failure message. Next the user will have three options for deposit, or withdrawal. 
Deposit - If the user selects this option, they can enter how much to deposit. When they enter, that amount is then added and saved to their account in the database. 
Withdrawal - If the user selects this option, they can enter their withdrawal amount. When they enter, the backend code will test this amount against the users account balance and if it overdrafts the user it will not go through and will either show an error message or navigate to an error page. 
Lastly, there will be a logout option which returns to the login menu. 

Steps to run project: 
1) Download the latest branch of the repo
2) Fill in your database login info and path to your schema that will hold the tables
3) Insert rows into tables using the SQL script in the script folder in the repo 
4) Run or debug main method. 

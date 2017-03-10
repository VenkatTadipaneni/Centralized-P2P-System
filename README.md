# Centralized-P2P-System 


# Technologies Used
Java - For socket Programming
Java AWT – For Front end GUI

# Centralized P2P Features - Sever 
A Centralized server maintaining all the client details like client Id, IP Address, Port and Shared File Path and the files available at Client.

# Functionalities:
+ Assigning Client Id using Register Method.
+ Giving Response for Query i.e., Query Hit.
+ Query for a specified file.
+ Query for all files in specified client.
+ Giving Response to Ping i.e. Pong.

# Centralized P2P Features - Client
Client Registers to server to download files from other clients.

# Functionalities:
+ Client started a server socket to make other clients to connect and download files from it.
+ Register to server with shared path and all the files.
+ Query to Server for a file to download.
+ Ping to Server to check whether client is available for download.
+ Download a file from another client.

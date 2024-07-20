# Chat Application

This is a simple chat application built using Java and Swing. It includes both server and client components to facilitate communication between multiple clients. 

## Features
- Multiple clients can connect to the server and send messages to each other in real-time.
- The chat interface displays the client's own messages on the right and other clients' messages on the left.
- Messages are color-coded for easy distinction between different clients.

## How to Run

### Server
1. Run the `chatserver` class to start the server.
2. The server listens on port 2000 for incoming client connections.

### Client
1. Run the `client` class to start the client interface.
2. Enter your name when prompted to join the chat.
3. Type messages in the text field at the bottom of the interface and press Enter to send.

## Project Structure
- `chatserver`: Manages client connections and message broadcasting.
- `ClientHandler`: Handles communication for each connected client.
- `clientchat`: Manages the client's connection to the server and message sending/receiving.
- `client`: Provides the graphical user interface for the chat application.

## Technologies Used
- Java
- Swing for GUI
- Sockets for network communication

## How to Contribute
1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Commit your changes (`git commit -am 'Add new feature'`).
4. Push to the branch (`git push origin feature-branch`).
5. Create a new Pull Request.

## License
This project is licensed under the MIT License.

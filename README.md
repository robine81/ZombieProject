# Final project for Group 1
## Authors:
- Jonathan Rubensson  
- Tamas Tazlo
- Robine Westberg

We have created an adventure game that can be played (either in) the terminal (or on its dedicated webpage). The game service, where all  game logic resides, exposes a number of API endpoints that our Interface interacts with.

Our project is composed of three services:
- GameService
- MySQL
- GameInterface

### Choice of packages
- Springboot
- MYSQL
- H2 DB for testing purposes
- Mockito

### How to run using Github repos, run:
    git clone https://github.com/robine81/ZombieProject.git
    git clone https://github.com/TamasTazlo/ZombieProjectInterface.git

In order to run the project, create a local .env file containing the following variables:

    ${DB_NAME}
    ${DB_PASS}
    ${DB_USERNAME} 
To run both ZombieProject and ZombieProjectInterface containers simultaneously, add the following to your .env file.

    COMPOSE_FILE=compose.yaml:../ZombieProjectInterface/compose.yaml

Do note that this assumes both projects share the same root directory. Adjust the paths to the compose files accordingly.
#### Build and run the project

    docker compose up --build
    docker attach zombie-game-interface

#### Run the game using Docker Hub:
#### Download these images: 
    robinewest/zombie-game:1.0.1
    robinewest/zombie-interface:1.0.1

    docker pull robinewest/zombie-game:1.0.1

#### In order to run the game from image:
    docker compose up
    docker attach zombie-game-interface

#### In game commands

    help: Shows a list of possible commands
    start: Starts the game
    inv: Shows player inventory
    1 - 9: Make a choice given by the scene
    restart: Restarts the game from the beginning
    quit: Quit the game


### API example
    - GET /game
    {
    "description": "You barely manage to jump the high stone wall as the horde behind you clawed at your feet. The moans of the shambling masses can still be heard faintly but it seems like you've completely managed to avoid their stench.\n\nYou're safe. For now.\n\n",
    "name": "A1L1S0-preamble",
    "options": {
    "1": "Look around."
        }
    }

    - GET /{optionId}
    {
    "description": "The high stone walls give you an immediate sense of safety as you look out over the garden.\n\nA chain and padlock secures the hatch to the basement.\n\nA rotund jolly garden gnome smiles knowingly.\n\nIn a far corner of the garden you spot a fancy-looking shed with its door ajar. What treasures might it hold?\n\n",
    "name": "A1L1S1-garden",
    "options": {
    "1": "Smash gnome.",
    "2": "Go to shed."
        }
    }
    
    - GET /inv
    {
    "Basement key": 1
    }


### Class diagram:  
![Class diagram](DesignDocuments/screenshots/ClassDiagram.png)

### Project structure:  
![Project structure](DesignDocuments/screenshots/projectstructure.png)

### Mockup/use case:  
![Mockup](DesignDocuments/screenshots/mockupusecase.png)
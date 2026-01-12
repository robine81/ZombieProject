# Zombie Adventure

## Game Verbs
### MVP
- Move between places
  - Enter/exit building
  - Walk a city block
  - (Cardinal directions)
- Examine features of a scene
  - Open a drawer
  - Investigate a corpse
  - Look to the north
- Die
  - Making a wrong kills the player character leading to a game over
- Use key items to overcome obstacles
  - Break barricades with e.g. a crowbar
  - Shoot zombie with gun

### Nice to have
- Combine items
- Fight (as in dedicated combat loop)
- Sneak (as in dedicated mechanics for noise and or visibility)
- Roaming zombies (moving between nodes)
- Driving
- Faster zombies
- Day / night cycle (mechanics for time keeping)
- Base needs (thirst, hunger, and sleep)
- Getting a view (vantage point nodes that give a rough map (text) of nearby locations)
- Randomized items (chests containing e.g. food or ammo)
- Randomized scenes
- Additional zombie types (leapers, tanks, spitters, screamers)
- Increase security by adding additional User handling entity/microservice between API Gateway and User DB

# Entities needed for MVP
- Node (Scene)
  - Has text description of current scene
  - Has a list of transitions to other nodes that are presented to the player as options
  - Has a list of items that are present in the scene
- Transition
  - Has a short descriptive text that is shown as an option
  - Has an end point node that the player is moved to when the transition is chosen
  - Some transitions require an item to be present in the player's inventory 
- Item
  - Has a description that is added to the scene it is in until it is removed
  - Can be consumable (is removed after being used in a transition)
  - (number of uses)
  - (combination with other items)
- Player / Inventory
  - Has a list of items collected by the player
  - Current node
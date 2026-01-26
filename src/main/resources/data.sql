-- ACT 1
-- LOCATIONS 1-3 GARDEN BASEMENT SHED

INSERT INTO scenes(id, name, description) VALUES
(1, "A1L1S0-preamble", "
   ____________________________________________________
  |____________________________________________________|
  | __     __   ____   ___ ||  ____    ____     _  __  |
  ||  |__ |--|_| || |_|   |||_|**|*|__|+|+||___| ||  | |
  ||==|^^||--| |=||=| |=*=||| |~~|~|  |=|=|| | |~||==| |
  ||  |##||  | | || | |JRO|||-|  | |==|+|+||-|-|~||__| |
  ||__|__||__|_|_||_|_|___|||_|__|_|__|_|_||_|_|_||__|_|
  ||_______________________||__________________________|
  | _____________________  ||      __   __  _  __    _ |
  ||=|=|=|=|=|=|=|=|=|=|=| __..\\/ |  |_|  ||#||==|  / /|
  || | | | | | | | | | | |/\\ \\  \\\\|++|=|  || ||==| / / |
  ||_|_|_|_|_|_|_|_|_|_|_/_/\\_.___\\__|_|__||_||__|/_/__|
  |____________________ /\\~()/()~//\\ __________________|
  | __   __    _  _     \\_  (_ .  _/ _    ___     _____|
  ||~~|_|..|__| || |_ _   \\ //\\\\ /  |=|__|~|~|___| | | |
  ||--|+|^^|==|1||2| | |__/\\ __ /\\__| |==|x|x|+|+|=|=|=|
  ||__|_|__|__|_||_|_| /  \\ \\  / /  \\_|__|_|_|_|_|_|_|_|
  |_________________ _/    \\/\\/\\/    \\_ _______________|
  | _____   _   __  |/      \\../      \\|  __   __   ___|
  ||_____|_| |_|##|_||   |   \\/ __|   ||_|==|_|++|_|-|||
  ||______||=|#|--| |\\   \\   o    /   /| |  |~|  | | |||
  ||______||_|_|__|_|_\\   \\  o   /   /_|_|__|_|__|_|_|||
  |_________ __________\\___\\____/___/___________ ______|
  |__    _  /    ________     ______           /| _ _ _|
  |\\ \\  |=|/   //    /| //   /  /  / |        / ||%|%|%|
  | \\/ |*/  .//____//.//   /__/__/ (_)      /  ||=|=|=|
__|  /|/   /(____|/ //                    /  /||~|~|~|__
  |___\\_/   /________//   ________         /  / ||_|_|_|
  |___ /   (|________/   |\\_______       /  /| |______|
      /                  |________)     /  / | |
You barely manage to jump the high stone wall as the horde behind you clawed at your feet. The moans of the shambling masses can still be heard faintly but it seems like you've completely managed to avoid their stench."),
(2, "A1L1S1-garden", "The high stone walls give you an immediate sense of safety as you look out over the garden."),
(3, "A1L2S1-basement", "You can't see shit."),
(4, "A1L3S1-shed-initial", "The inside of the shed is a mess of expensive looking tools. Someone must've been rummaging frantically for something. The pile looks unstable, as if it might collapse should you take anything."),
(5, "A1L3S2-shed-collapsed", "If the shed was a mess before, the only way to describe it now would be pandemonic. There is no hope of finding anything else here.");

INSERT INTO transitions(id, name, origin_scene_id, target_scene_id, scene_description, choice_description, is_enabled) VALUES
(1, "A1L1S0-A1L1S1-start-to-garden", 1, 2, "You're safe. For now.", "Look around.", true),
(2, "A1L1S0-A1L1S1-locked-basement-hatch", 2, 2, "A chain and padlock secures the hatch to the basement.", "Use basement key.", true),
(3, "A1L1S1-A1L2S1-garden-to-unlit-basement", 2, 3, "The hatch to the basement lies open, its gentle darkness beckoning you.", "Enter basement.", false),
(4, "A1L1S1-A1L1S1-smash-gnome", 2, 2, "A rotund jolly garden gnome smiles knowingly.", "Smash gnome.", true),
(5, "A1L1S1-A1L1S1-take-key", 2, 2, "Glinting in the dust and shards of the former gnome you spot a key.", "Take key.", false),
(6, "A1L1S1-A1L3S1-garden-to-shed", 2, 4, "In a far corner of the garden you spot a fancy-looking shed with its door ajar. What treasures might it hold?", "Go to shed.", true),
(7, "A1L3S1-A1L1S1-shed-to-garden", 4, 2, "The door stands open behind you.", "Return to garden.", true),
(8, "A1L1S1-A1L3S2-garden-to-shed", 2, 5, "In a far corner of the garden you spot a fancy-looking shed with its door ajar. Only chaos remains inside.", "Go to shed.", false),
(9, "A1L3S2-A1L1S1-shed-to-garden", 5, 2, "The door stands open behind you.", "Return to garden.", true),
(10, "A1L3S1-A1L3S2-take-flashlight", 4, 5, "Jutting out from beneath the pile you spot the head of a flashlight.", "Take the flashlight.", true),
(11, "A1L3S1-A1L3S2-take-chainsaw", 4, 5, "Jammed into the middle of the pile you make out the handle of a chainsaw.", "Take the chainsaw.", true);

INSERT INTO items(id, name, description, transition_id) VALUES
(1, "Basement key", "I should be able to open the hatch to the basement with this.", 5),
(2, "Flashlight", "A welcome light in dark times.", 10),
(3, "Chainsaw", "A timeless classic.", 11);

INSERT INTO transition_items(transition_id, item_id) VALUES (2, 1);

INSERT INTO transition_disables_transitions(owner_transition_id, affected_transition_id) VALUES
(2, 2),
(4, 4),
(5, 5),
(10, 6),
(11, 6);

INSERT INTO transition_enables_transitions(owner_transition_id, affected_transition_id) VALUES
(2, 3),
(4, 5),
(10, 8),
(11, 8);
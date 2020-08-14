# My Personal Project - Memorizable

## Proposal
I propose **Memorizable**, a flashcard application that utilizes [spaced repetition][1] to facilitate memorization.  Items to memorize are stored in Memorizable as question-answer pairs. When a pair is due to be reviewed, the question (e.g., a new word) will be shown, and the user will try to recall the answer (in this case, the definition of the word). The user will then manually reveal the answer and tell Memorizable how difficult it was to answer. The application will compute the intervals between each review of an item based on the user’s feedback. Newly introduced and more difficult items will show up more frequently. 

Memorizable can be used in second-language learning or any learning tasks that involve intensive memorization.

Before coming to UBC, I was a journalist in China. I enjoyed reading and writing. I want to develop a sufficient vocabulary in English as I do in Chinese so that I can resume my hobby here. A flashcard application will definitely help me. There are a list of flashcard software out there. But most of them have features that are way too sophisticated than my needs. I want to build a minimalist app that’s tailored  for me and keep the option of future upgrades.
### Algorithm
I use a simplified [SM-2][2]algorithm to compute the interval before next time an item is shown:
- If an item is visited for the 1st time, set `I(1) = 1 * EF` where:
	- `I(1)` is the interval after the 1st repetition, and
	- `EF` (ease factor) reflects the easiness of memorizing. `EF` is 0 for items that are **not** correctly remembered and 2 for items that are correctly remembered.
- If an item is visited for the nth time, 
	- If `I(n-1) != 0`,  set `I(n) = I(n-1) * EF`;
	- If `I(n-1) == 0`, set `I(n) = 1 * EF`\.

## Phase I User Stories
- As a user, I want to be able to create a list of items to Memorizable.
- As a user, I want to be able to add an item (question-answer pair) to the list.
- As a user, I want to be able to view the question and then the answer of the item.
- As a user, I want to mark an item in the list to be “easy" or “difficult”.
- As a user, I want to be able to know when I will revisit an item given my feedback of “easy" or “difficult”.

## Phase II User Stories
- As a user, when I go back to the main menu, I want to save the queue and the flashcards that I create.
- As a user, I want to review existing queues of flashcards.

## Phase III User Stories
- [x] The app should be able to schedule the reviews of flashcards on real-world dates, namely to show cards  due at a specific day when the app is running, and not to show cards that are not due.
- [x] The user should be able to add a new deck of flashcards through the GUI.
- [x] The user should be able to add a new card to an empty deck through the GUI.
- [] The user should be able to review the cards through the GUI. (TODO)

## Instructions for Grader
- You can generate the first required event by **clicking the “Add a new deck” button, naming the deck, then clicking OK**. You can also generate the first required event by **choosing a deck, clicking “Review the deck” button, typing question and answer, then clicking “Add a new card” button **.
- You can generate the second required event by **choosing one deck, clicking “Delete the deck”, the clicking Yes**.
- You can trigger my audio component by **clicking the “Add a new deck” button, then clicking OK without naming the deck**. You can also trigger my audio component by **adding a new deck with the same name of one existing deck**.
- The state of my application is automatically saved.
- You can reload the state of my application by **running it**. Files in the `./data/`folder will be loaded automatically.

## Phase 4: Task 2
- The constructor of `Card` class has a robust design. In the `setup()` method in `TestCard` class, the exception is not expected. In the `testCardConstructor()` method in the same class, the exception is expected.
- The `updateQueue()` method in the `CardQueue` class also has a robust design. The `testUpdateQueue` method in the `TestCardQueue` class has both scenarios of expecting and not expecting an exception.

## Phase 4: Task 3


[1]:	https://en.wikipedia.org/wiki/Spaced_repetition
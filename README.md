# My Personal Project - Memorizable

## Proposal
I propose **Memorizable**, a flashcard application that utilizes [spaced repetition][1] to facilitate memorization.  Items to memorize are stored in Memorizable as question-answer pairs. When a pair is due to be reviewed, the question (e.g., a new word) will be shown, and the user will try to recall the answer (in this case, the definition of the word). The user will then manually reveal the answer and tell Memorizable how difficult it was to answer. The application will compute the intervals between each review of an item based on the user’s feedback. Newly introduced and more difficult items will show up more frequently. 

Memorizable can be used in second-language learning or any learning tasks that involve intensive memorization.

Before coming to UBC, I was a journalist in China. I enjoyed reading and writing. I want to develop a sufficient vocabulary in English as I do in Chinese so that I can resume my hobby here. A flashcard application will definitely help me. There are a list of flashcard software out there. But most of them have features that are way too sophisticated than my needs. I want to build a minimalist app that’s tailored  for me and keep the option of future upgrades.

## User Story
- As a user, I want to be able to add a list of items to Memorizable.
- As a user, I want to be able to add an item (question-answer pair) the the list.
- As a user, I want to be able to view the question and then the answer of the item.
- As a user, I want to mark an item on the list to be “easy" or “difficult”.
- As a user, I want to be able to know when I will revisit the item given my feedback of “easy" or “difficult”.

[1]:	https://en.wikipedia.org/wiki/Spaced_repetition#List_of_spaced_repetition_software_programs
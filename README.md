## Introduction
**Memorizable** is a flashcard application that utilizes [spaced repetition][1] to facilitate memorization.  Items to memorize are stored in Memorizable as question-answer pairs. When a pair is due to be reviewed, the question (e.g., a new word) will be shown, and the user will try to recall the answer (in this case, the definition of the word). The user will then manually reveal the answer and tell Memorizable how difficult it was to answer. The application will compute the intervals between each review of an item based on the user’s feedback. Newly introduced and more difficult items will show up more frequently. 

Memorizable can be used in second-language learning or any learning tasks that involve intensive memorization.

### Algorithm
I use a simplified [SM-2][2] algorithm to compute the interval before next time an item is shown:
- If an item is visited for the 1st time, set `I(1) = 1 * EF` where:
	- `I(1)` is the interval after the 1st repetition, and
	- `EF` (ease factor) reflects the easiness of memorizing. `EF` is 0 for items that are **not** correctly remembered and 2 for items that are correctly remembered.
- If an item is visited for the nth time, 
	- If `I(n-1) != 0`,  set `I(n) = I(n-1) * EF`;
	- If `I(n-1) == 0`, set `I(n) = 1 * EF`\.
## Usage
The JAR file can be found at `out/artifacts/Memorizable_jar/Project-Starter.jar `.


[1]:	https://en.wikipedia.org/wiki/Spaced_repetition
[2]:	https://www.supermemo.com/zh/archives1990-2015/english/ol/sm2
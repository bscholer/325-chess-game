#Chess Game (325 Final Project)
###Executing

IMPORTANT: There is an issue with the Chess API we are using that basically ends the game after a minute or so. We didn't realize this until we had almost completed the project.

More about that issue on the API's (GitHub)[https://github.com/anzemur/chess-api/issues/2]

To execute the code, run `src/Main.java`. Please note that there is one dependency, `lib/json-20201115.jar`.

You'll notice that the game is fairly slow--to speed it up, please change `useTimeAPI` to `false` in src/TimeServer.java.

Doing this will make the program use Java's prebuilt Time/Date classes to get the time for logging, instead of the (slow) time API.

Sometimes the reset game and language dropdown disappear. To fix this, just hover your mouse around that area, and they should reappear.

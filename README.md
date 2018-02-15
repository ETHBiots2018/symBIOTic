# symBIOTic
Project for #BIOTS2018: gamify recycling using the Blockchain & AI.


### Summary
We introduce a gamified recycling experience in our work which incentivises ecological behaviour by a lottery.
For this, we developped an Android App that allows the user to scan, i.e. recycle, items, thus earning receiving a direct return (the usual deposit) and also tokens for a raffle.
Thus, the consumer has a direct reward, but also the chance on a even higher one which natuarally triggers ecological behaviour.

The Blockchain is used as immutable backend for the lottery.
We use `Oraclize` as trusted backend for random choices.
Image recognition is possible thanks to the `Microsoft Cognitive Services`.
Our repository consists of the Android frontend (`./app`), the Ethereum Smart Contract (`./contracts`) and helper libraries (`./java`).


### File Structure
```
symBIOTic
│   README.md                     (file you're looking at)
│   LICENSE                       (open source license)
│
└───js
│   │   config.js                 (stores URL to AMIV API)
│   │   doRender.js               (renders selected events to HTML code)
│   │   eventHandlers.js          (handles clicks on the frontend's UI elements)
│   │   index.js                  (wraps all js in one file for npm)
│   │   prepareJSON.js            (localises requested JSON and adds header information)
│   
└───css
│   │   announce.css              (styles for the actual Announce)
│   │   style.css                 (styles for the frontend)
│   
└───templates
│   │   *.html                    (templates for each part of the Announce)
│   
└───images
│   │   *.png                     (images for the actual Announce)
```

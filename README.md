# symBIOTic
Project for #BIOTS2018: gamify recycling using the Blockchain & AI.


### Summary
We introduce a gamified version of everyday's recycling which incentivises ecological behaviour by a lottery.
For this, we developped an Android App that allows the user to scan, i.e. recycle, items, thus earning a direct return (the usual deposit) and also tokens for a raffle.
Thus, the consumer has a direct reward, but also the chance on a even higher one which naturally triggers ecological behaviour.

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
└───app                           (Android frontend for authentication and image recognition)
│   │   ...
│   
└───contracts                     (Smart Contracts for the lottery backend)
│   │   ...
│   
└───java                          (Java helper libraries + Socket Server - bridge between the app and the blockchain)
│   │   ...

```

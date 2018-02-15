pragma solidity ^0.4.19;

import "oraclizeAPI_0.4.sol";
import "Ownable.sol";

/**
 * @title Lottery
 */
contract Lottery is usingOraclize, Ownable {

  // Possible states of the contract
  enum State {
      Recycling,
      PickingWinner
  }

  State state = State.Recycling;  // Keeps track of the current contract state
  uint jackpot = 5;               // Required potsize to end the lottery and pick a winner

  uint potSize = 0;               // Keeps track of the current pot size
  uint nOfParticipants = 0;       // Keeps track of the number of participant

  address mostRecentWinner = 0x0; // Stores the most recent winner

  mapping (uint => address) participants;
  mapping (address => uint) balances;
  
  event WinnerPicked(address winner);

  modifier inState(State _state) {
    require(state == _state);
    _;
  }

  function Lottery() public {
    oraclize_setProof(proofType_Ledger);
  }

  function recycle(address _sender) public inState(State.Recycling) {
    require(this.balance >= 1 ether);
    require(_sender != 0x0);
    require(potSize < jackpot);
    
    createParticipantIfNeeded(_sender);
    balances[_sender]++;
    potSize++;
    
    if (potSize == jackpot) {
      state = State.PickingWinner;
      pickWinner();
    }
  }

  function createParticipantIfNeeded(address participant) private {
    if (balances[participant] == 0) {
      participants[nOfParticipants++] = participant;
    }
  }

  function pickWinner() private {
    uint n = 7;
    uint delay = 0;
    uint callbackGas = 200000;
    oraclize_newRandomDSQuery(delay, n, callbackGas); // ask the oracle for a random number
  }

  function __callback(bytes32 _queryId, string _result, bytes _proof) public {
    if (msg.sender != oraclize_cbAddress()) {
      throw;
    }
    
    uint winningNumber;
    
    if (oraclize_randomDS_proofVerify__returnCode(_queryId, _result, _proof) != 0) {
      // The proof verification has failed, we have to use a fallback 
      // to pick a winning number
      winningNumber = uint(block.blockhash(block.number - 1)) % potSize;
    } else {
      // The proof verification has passed
      
      uint maxRange = 2**(8 * 7); // do some magic stuff
      uint randomNumber = uint(sha3(_result)) % maxRange;
      
      winningNumber = randomNumber % potSize;
    }
    
    uint sum = 0;
    for (uint i = 0; i < nOfParticipants; i++) {
      sum += balances[participants[i]];
      if (sum >= winningNumber) { // We have a winner
          mostRecentWinner = participants[i];
      }
    }

    resetLottery();
    state = State.Recycling;

    if (mostRecentWinner != 0x0) {
      WinnerPicked(mostRecentWinner);
      mostRecentWinner.transfer(1 ether);
    }
  }

  function didIWin(address _sender) public view returns (bool) {
    return keccak256(_sender) == keccak256(mostRecentWinner);
  }

  function resetLottery() private {
    for (uint i = 0; i < nOfParticipants; i++) {
      balances[participants[i]] = 0;
      participants[i] = 0x0;
    }
    potSize = 0;
    nOfParticipants = 0;
  }

  function transfer(address _to, uint _amount) public { 
    require(balances[msg.sender] >= _amount);  
    balances[msg.sender] -= _amount;
    createParticipantIfNeeded(_to);
    balances[_to] += _amount;
  }

  function getBalance(address _sender) public view returns (uint) {
    return balances[_sender];
  }

  function setJackpot(uint _value) public onlyOwner inState(State.Recycling) {
    jackpot = _value;
  }

  function fundLottery() public payable onlyOwner {
      
  }

  function lotteryBalance() public view onlyOwner returns (uint) {
    return this.balance;
  }
}

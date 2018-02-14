pragma solidity ^0.4.19;

import "github.com/OpenZeppelin/zeppelin-solidity/blob/master/contracts/ownership/Ownable.sol";

contract Lottery is usingOraclize, Ownable {
    
    uint jackpot = 5;
    
    uint potSize = 0;
    uint nOfParticipants = 0;
    
    mapping (uint => address) participants;
    mapping (address => uint) balances;
    
    function recycle() public {
        require(this.balance >= 1 ether);
        require(msg.sender != 0x0);
        require(potSize < jackpot);
        
        if (balances[msg.sender] == 0) { // It is a new participant
            participants[nOfParticipants++] = msg.sender;
        }
        balances[msg.sender]++;
        potSize++;
        
        if (potSize == jackpot) {
            address winner = pickWinner();
            winner.transfer(1 ether);
            resetLottery();
        }
    }
    
    function pickWinner() private view returns (address) {
        uint sum = 0;
        uint winningNumber = uint(block.blockhash(block.number - 1)) % potSize;
        for (uint i = 0; i < nOfParticipants; i++) {
            sum += balances[participants[i]];
            if (sum >= winningNumber) { // We have a winner
                return participants[i];
            }
        }
    }
    
    function resetLottery() private {
        for (uint i = 0; i < nOfParticipants; i++) {
            balances[participants[i]] = 0;
            participants[i] = 0x0;
        }
        potSize = 0;
        nOfParticipants = 0;
    }
    
    function getBalance() public view returns (uint) {
        return balances[msg.sender];
    }
    
    function setJackpot(uint _value) public onlyOwner {
        jackpot = _value;
    }
    
    function fundLottery() public payable onlyOwner {
        
    }
    
    function lotteryBalance() public view onlyOwner returns (uint) {
        return this.balance;
    }
}